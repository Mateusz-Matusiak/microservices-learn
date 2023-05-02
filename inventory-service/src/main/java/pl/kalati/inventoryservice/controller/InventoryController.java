package pl.kalati.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kalati.inventoryservice.dto.IsInStockResponse;
import pl.kalati.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public record InventoryController(InventoryService inventoryService) {

    @GetMapping("/{sku-code}")
    public ResponseEntity<IsInStockResponse> isInStock(@PathVariable("sku-code") String skuCode) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
