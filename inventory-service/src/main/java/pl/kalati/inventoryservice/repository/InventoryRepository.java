package pl.kalati.inventoryservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import pl.kalati.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryRepository extends ListCrudRepository<Inventory, Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
