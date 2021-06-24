package mob.code.supermarket.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mob.code.supermarket.domain.Item;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ItemDO {
    @Id
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private String type;

    public static ItemDO fromEntity(Item entity) {
        return ItemDO.builder()
                .price(entity.getPrice())
                .name(entity.getName())
                .type(entity.getType())
                .unit(entity.getUnit())
                .barcode(entity.getBarcode())
                .build();
    }

    public Item toEntity() {
        return new Item(barcode, name, unit, price, type);
    }
}
