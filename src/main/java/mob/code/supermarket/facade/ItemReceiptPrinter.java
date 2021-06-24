package mob.code.supermarket.facade;

import mob.code.supermarket.domain.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemReceiptPrinter {
    private final ReceiptItems receiptItems;

    public ItemReceiptPrinter(ReceiptItems receiptItems) {
        this.receiptItems = receiptItems;
    }

    public List<String> print(String[] barcodes) {
        String[] parsedBarcodes = parsedBarcodes(barcodes);
        List<QuantityBarcode> quantityBarcodes = Barcodes.of(parsedBarcodes);
        List<ReceiptItem> receiptItems = this.receiptItems.getReceiptItems(quantityBarcodes);
        var receipt = new Receipt(receiptItems);
        return receipt.print();
    }

    private String[] parsedBarcodes(String[] barcodes) {
        if (isOriginalBarcodes(barcodes)) {
            return new RawBarcodeParser().parse(barcodes).toArray(String[]::new);
        }
        return barcodes;
    }

    private boolean isOriginalBarcodes(String[] barcodes) {
        String originalBarcodesStr = Arrays.stream(barcodes).collect(Collectors.joining());
        return originalBarcodesStr.contains("_") || originalBarcodesStr.contains("\\|");
    }

}
