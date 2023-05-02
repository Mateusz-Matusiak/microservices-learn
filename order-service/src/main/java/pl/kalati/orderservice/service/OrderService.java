package pl.kalati.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kalati.orderservice.dto.OrderLineItemDto;
import pl.kalati.orderservice.dto.OrderRequest;
import pl.kalati.orderservice.model.Order;
import pl.kalati.orderservice.model.OrderLineItem;
import pl.kalati.orderservice.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToEntity).toList();
        order.setOrderLineItemList(orderLineItems);
        orderRepository.save(order);

    }

    private OrderLineItem mapToEntity(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItem;
    }
}
