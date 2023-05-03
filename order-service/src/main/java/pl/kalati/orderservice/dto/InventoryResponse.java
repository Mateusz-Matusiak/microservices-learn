package pl.kalati.orderservice.dto;

public record InventoryResponse(
        String skuCode, boolean isInStock
) {
}
