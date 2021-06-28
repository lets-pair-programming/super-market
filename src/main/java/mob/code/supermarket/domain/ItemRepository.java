package mob.code.supermarket.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository {
    Optional<Item> findByBarcode(String barcode);

    List<Item> findAll();

    void save(Item item);
}
