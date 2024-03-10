package com.example.tutorial.controller;

import com.example.tutorial.dto.Order.OrderCreationDTO;
import com.example.tutorial.dto.Order.OrderDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.enumeration.OrderState;
import com.example.tutorial.service.OrderService;
import com.example.tutorial.util.AuthenticationHelper;
import com.example.tutorial.util.SortHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<Page<OrderDTO>> getAll(
            @NotNull Authentication authentication,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "per_page", defaultValue = "20") Integer perPage,
            @RequestParam(name = "sort", defaultValue = "") String sortQuery,
            @RequestParam(name = "state", defaultValue = "") String stateQuery
    ) {
        User user = AuthenticationHelper.getUser(authentication);
        Sort sort = sortQuery.isBlank() ? Sort.unsorted() : SortHelper.getFinalSort(SortableField.class, sortQuery);
        Pageable pageRequest = PageRequest.of(page, perPage).withSort(sort);
        List<OrderState> states = getStateFilter(stateQuery);
        Page<OrderDTO> result = user.isAdmin() ? orderService.adminGetAll(states, pageRequest)
                                                : orderService.userGetAll(user, states, pageRequest);
        return ResponseDTO.of(result);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<OrderDTO> getDetail(Authentication authentication, @PathVariable(name = "orderId") Integer orderId) {
        User user = AuthenticationHelper.getUser(authentication);
        return user.isAdmin() ? ResponseDTO.of(orderService.adminGetDetail(orderId))
                : ResponseDTO.of(orderService.userGetDetail(user, orderId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<OrderDTO> create(Authentication authentication, @RequestBody @Valid OrderCreationDTO orderDTO) {
        User customer = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(orderService.create(customer, orderDTO));
    }

    @PostMapping("/{orderId}/checkout")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<OrderDTO> checkout(@PathVariable(name = "orderId") @NotNull Integer orderId) {
        return ResponseDTO.of(orderService.checkout(orderId));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer orderId) {
        orderService.deleteOrder(orderId);
    }

    private enum SortableField {
        created
    }

    private List<OrderState> getStateFilter(String filterQuery) {
        List<String> acceptedValues = Stream.of(OrderState.class.getEnumConstants()).map(Enum::name).toList();
        List<OrderState> result = new ArrayList<>();
        for (String state : filterQuery.split(",")) {
            if (acceptedValues.contains(state.toUpperCase())) {
                result.add(OrderState.valueOf(state.toUpperCase()));
            }
        }

        if (result.isEmpty()) {
            result.addAll(Arrays.asList(OrderState.class.getEnumConstants()));
        }

        return result;
    }
}
