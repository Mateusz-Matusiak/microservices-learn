package pl.kalati.inventoryservice.service;

import org.springframework.stereotype.Service;
import pl.kalati.inventoryservice.dto.InventoryResponse;
import pl.kalati.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
public record InventoryService(
        InventoryRepository inventoryRepository
) {

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() > 0))
                .toList();
    }
}
