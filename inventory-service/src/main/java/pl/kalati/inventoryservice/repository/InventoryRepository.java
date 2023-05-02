package pl.kalati.inventoryservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import pl.kalati.inventoryservice.model.Inventory;

public interface InventoryRepository extends ListCrudRepository<Inventory, Long> {

    boolean existsBySkuCode(String skuCode);
}
