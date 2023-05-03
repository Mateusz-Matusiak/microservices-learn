package pl.kalati.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.kalati.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
