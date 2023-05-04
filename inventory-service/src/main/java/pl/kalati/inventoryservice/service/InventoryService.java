package pl.kalati.inventoryservice.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kalati.inventoryservice.dto.InventoryResponse;
import pl.kalati.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
@Slf4j
public record InventoryService(
        InventoryRepository inventoryRepository
) {

    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Wait started");
        Thread.sleep(10 * 1000L);
        log.info("Wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() > 0))
                .toList();
    }
}
