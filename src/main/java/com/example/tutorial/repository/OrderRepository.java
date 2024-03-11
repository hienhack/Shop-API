package com.example.tutorial.repository;

import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findOrderByIdAndCustomer_Id(Integer orderId, Integer customerId);
    public Page<Order> findByCreatedBetween(LocalDateTime from, LocalDateTime to, Pageable pageRequest);
    public Page<Order> findByStateIsInAndCustomer_Id(List<OrderState> states, Integer customerId, Pageable pageRequest);
    public Page<Order> findByStateIsIn(List<OrderState> states, Pageable pageRequest);
    public int countByCreatedBetween(LocalDateTime from, LocalDateTime to);

    /**
     * Create an overall statistic
     * @param from beginning date
     * @param to end date
     * @return an array including total revenue, number of orders and number of products sold respectively
     */
    @Query(value = "select sum(o.total_cost), count(*), sum(od.quantity) " +
            "from orders o join (select order_id, sum(quantity) quantity from order_detail group by order_id) od on od.order_id = o.id " +
            "where o.created_date between ?1 and ?2", nativeQuery = true)
    public Object[][] createStatisticFromDateToDate(LocalDate from, LocalDate to);

    /**
     * create overall statistic by day
     * @param from beginning date
     * @param to end date
     * @return 2D arrays, each line includes date, total revenue, number of orders and number of products sold respectively
     */
    @Query(value = "select cast(o.created_date as date) as \"date\", sum(o.total_cost), count(*), sum(od.quantity)\n" +
            "from orders o join (select order_id, sum(quantity) quantity from order_detail group by order_id) od on od.order_id = o.id\n" +
            "where o.created_date between ?1 and ?2 and o.state <> 'CANCELLED'\n" +
            "group by cast(o.created_date as date)\n" +
            "order by \"date\" asc", nativeQuery = true)
    public Object[][] createStatisticByDayFromDateToDate(LocalDate from, LocalDate to);

    /**
     * Create product sold statistic
     * @param from beginning date
     * @param to end date
     * @return 2D array, each line includes product id, product name, thumbnail, total sold
     */
    @Query(value = "select p.id, p.name, p.thumbnail, p.price, sum(od.quantity) " +
            "from orders o, order_detail od, product p " +
            "where o.id = od.order_id and od.product_id = p.id and o.state <> 'CANCELLED' and o.created_date between ?1 and ?2 " +
            "group by p.id, p.name, p.thumbnail, p.price " +
            "order by sum(od.quantity) desc", nativeQuery = true)
    public Object[][] createProductStatisticFromDateToDate(LocalDate from, LocalDate to);


}
