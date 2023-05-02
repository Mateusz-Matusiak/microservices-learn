package pl.kalati.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kalati.orderservice.dto.OrderRequest;
import pl.kalati.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/order")
public record OrderController(
        OrderService orderService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
    }
}
