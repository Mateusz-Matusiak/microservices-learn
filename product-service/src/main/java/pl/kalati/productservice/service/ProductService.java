package pl.kalati.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kalati.productservice.dto.ProductRequest;
import pl.kalati.productservice.dto.ProductResponse;
import pl.kalati.productservice.model.Product;
import pl.kalati.productservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getName());
    }

    public List<ProductResponse> fetchAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(),
                        product.getDescription(), product.getPrice()))
                .toList();
    }
}
