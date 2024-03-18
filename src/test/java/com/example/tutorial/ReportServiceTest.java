package com.example.tutorial;

import com.example.tutorial.repository.OrderRepository;
import com.example.tutorial.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceTest {
    @Autowired
    private ReportService reportService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void statisticTest() {
        LocalDate from = LocalDate.now().minusDays(2);
        LocalDate to = LocalDate.now();
    }

    @Test
    public void statisticByDay() {
        LocalDate from = LocalDate.now().minusDays(2);
        LocalDate to = LocalDate.now();


        System.out.println("For testing");
    }

    @Test
    public void statisticBetween() {
        LocalDate from = LocalDate.now().minusDays(2);
        LocalDate to = LocalDate.now();

        Object[][] result = orderRepository.createStatisticFromDateToDate(from, to);

        System.out.println("For test");
    }

    @Test
    public void productStatistic() {
        LocalDate from = LocalDate.now().minusDays(2);
        LocalDate to = LocalDate.now();

        Object result = orderRepository.createProductStatisticFromDateToDate(from, to);

        System.out.println("For testing");
    }
}