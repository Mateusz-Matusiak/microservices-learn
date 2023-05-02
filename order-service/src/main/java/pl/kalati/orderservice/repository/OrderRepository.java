package pl.kalati.orderservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import pl.kalati.orderservice.model.Order;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
}
