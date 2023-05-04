package pl.kalati.inventoryservice.dto;

public record InventoryResponse(
        String skuCode, boolean isInStock
) {
}
