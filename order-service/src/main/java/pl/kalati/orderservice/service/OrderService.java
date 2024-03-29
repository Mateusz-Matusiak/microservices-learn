package pl.kalati.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kalati.orderservice.dto.InventoryResponse;
import pl.kalati.orderservice.dto.OrderLineItemDto;
import pl.kalati.orderservice.dto.OrderRequest;
import pl.kalati.orderservice.event.OrderPlacedEvent;
import pl.kalati.orderservice.model.Order;
import pl.kalati.orderservice.model.OrderLineItem;
import pl.kalati.orderservice.repository.OrderRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        List<String> skuCodes = orderRequest.getOrderLineItemsList().stream()
                .map(OrderLineItemDto::getSkuCode)
                .toList();
        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(UUID.randomUUID().toString()));

        InventoryResponse[] result = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(result)
                .allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new IllegalArgumentException("Not all products are available, sorry");
        }
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToEntity).toList();
        order.setOrderLineItemList(orderLineItems);
        orderRepository.save(order);
        return "success";
    }

    private OrderLineItem mapToEntity(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItem;
    }
}
