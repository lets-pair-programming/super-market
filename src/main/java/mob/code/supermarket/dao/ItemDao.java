package mob.code.supermarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemDao extends JpaRepository<ItemDO, String> {
    Optional<ItemDO> findByBarcode(String barcode);
}
