package mob.code.supermarket.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceiptItems {
    private final ItemRepository itemRepository;

    public ReceiptItems(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ReceiptItem> getReceiptItems(List<QuantityBarcode> quantityBarcodes) {
        return quantityBarcodes.stream()
                .map(this::getReceiptItem).collect(Collectors.toList());
    }

    public ReceiptItem getReceiptItem(QuantityBarcode barcode) {
        var item = itemRepository.findByBarcode(barcode.getCode())
                .orElseThrow(() -> new SupermarketException(
                        String.format("item doesn't exist: %s", barcode.getCode())
                ));
        return new ReceiptItem(item, barcode);
    }
}
