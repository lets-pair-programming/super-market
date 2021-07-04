package mob.code.supermarket.domain;

import mob.code.supermarket.legacy.BarcodeReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RawBarcodeParser {

    private final String itemTitle = "****** SuperMarket receipt ******";
    private final String billTitle = "---------------------------------";
    private final String billEnd = "*********************************";
    @Autowired
    private ItemRepository itemRepository;

    public RawBarcodeParser() {
    }

    public List<String> parse(String[] barcodes) {
        try {
            checkBarcodesLength(barcodes);
            return BarcodeReader.barcodeFactory().getBarcode(barcodes);
        } catch (RawBarcodeFormatException e) {
            throw new SupermarketException("can not recognize barcode:\n" +
                    String.join("\n", Arrays.asList(barcodes)));
        }
    }

    private void checkBarcodesLength(String[] barcodes) {
        if (barcodes.length % 4 != 0) {
            throw new RawBarcodeFormatException("输入条码长度不正确");
        }

    }

    public List<String> print(String[] barcodes) {
        List<Double> totalPrice = new ArrayList<>();
        List<String> result = new ArrayList<>();
        result.add(itemTitle);
        Arrays.stream(barcodes).forEach(barCode -> {
            String originCode;
            String count = "1";
            if (barCode.contains("-")) {
                String[] splitArray = barCode.split("-");
                originCode = splitArray[0];
                count = splitArray[1];
            } else {
                originCode = barCode;
            }
            Optional<Item> item = itemRepository.findByBarcode(originCode);
            if (item.isPresent()) {
                Item itemCode = item.get();
                Double itemTotalPrice = itemCode.getPrice() * Double.parseDouble(count);
                totalPrice.add(itemTotalPrice);
                String strItem = String.format("%s: %s(%s) x %s --- %s"
                        , itemCode.getName(), count, itemCode.getUnit()
                        , itemCode.getType(), itemTotalPrice);
                result.add(strItem);
            } else {
                throw new SupermarketException(String.format("item doesn't exist: %s", originCode));
            }
        });
        result.add(billTitle);
        result.add(String.format("total: %s(CNY)",
                totalPrice.stream().mapToDouble(Double::doubleValue).sum()));
        result.add(billEnd);
        return result;
    }
}
