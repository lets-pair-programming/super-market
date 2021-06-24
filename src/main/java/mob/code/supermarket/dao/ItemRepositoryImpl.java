package mob.code.supermarket.dao;

import mob.code.supermarket.domain.Item;
import mob.code.supermarket.domain.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemDao itemDao;

    public ItemRepositoryImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Optional<Item> findByBarcode(String barcode) {
        return itemDao.findById(barcode).map(ItemDO::toEntity);
    }

    @Override
    public List<Item> findAll() {
        List<ItemDO> all = itemDao.findAll();
        return all.stream().map(ItemDO::toEntity).collect(Collectors.toList());
    }

    @Override
    public void save(Item item) {
        itemDao.save(ItemDO.fromEntity(item));
    }
}
