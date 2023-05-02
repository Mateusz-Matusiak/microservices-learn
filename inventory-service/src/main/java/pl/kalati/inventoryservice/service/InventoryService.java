package pl.kalati.inventoryservice.service;

import org.springframework.stereotype.Service;
import pl.kalati.inventoryservice.dto.IsInStockResponse;
import pl.kalati.inventoryservice.repository.InventoryRepository;

@Service
public record InventoryService(
        InventoryRepository inventoryRepository
) {

    public IsInStockResponse isInStock(String skuCode) {
        return new IsInStockResponse(inventoryRepository.existsBySkuCode(skuCode));
    }
}
