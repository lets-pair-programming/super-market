package mob.code.supermarket.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityBarcode {
    public static final String SEPARATOR = "-";
    private final String barcode;
    private final double quantity;
    private final List<String> originalBarcodes;

    public List<String> getOriginalBarcodes() {
        return originalBarcodes;
    }

    public QuantityBarcode(String barcode) {
        this.originalBarcodes = Collections.singletonList(barcode);
        if (!barcode.contains(SEPARATOR)) {
            this.barcode = barcode;
            this.quantity = 1;
            return;
        }
        String[] array = barcode.split(SEPARATOR);
        this.barcode = array[0];
        this.quantity = parseQuantity(array[1]);
    }

    public QuantityBarcode(String barcode, List<QuantityBarcode> entryValue) {
        this.barcode = barcode;
        this.quantity = reduceQuantity(entryValue);
        this.originalBarcodes = reduceOriginalBarcodes(entryValue);

    }

    private List<String> reduceOriginalBarcodes(List<QuantityBarcode> entryValue) {
        return entryValue.stream()
                .flatMap(quantityBarcode -> quantityBarcode.getOriginalBarcodes().stream())
                .collect(Collectors.toList());
    }

    public QuantityBarcode(String barcode, double quantity, String originalBarcode) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.originalBarcodes = Collections.singletonList(originalBarcode);
    }

    static Double reduceQuantity(List<QuantityBarcode> entryValue) {
        return entryValue.stream()
                .map(QuantityBarcode::getQuantity)
                .reduce(0.0, Double::sum);
    }

    private double parseQuantity(String quantity) {
        checkQuantityNotZero(quantity);
        checkQuantityTheDecimalLessThan2(quantity);
        return Double.parseDouble(quantity);
    }

    private void checkQuantityNotZero(String quantity) {
        if (quantity.equals("0")) {
            throw new SupermarketException(String.format("wrong quantity of %s", barcode));
        }
    }

    private void checkQuantityTheDecimalLessThan2(String quantity) {
        if (!quantity.contains(".")) {
            return;
        }
        int decimalLength = quantity.substring(quantity.indexOf(".") + 1).length();
        if (decimalLength > 1) {
            throw new SupermarketException(String.format("wrong quantity of %s", barcode));
        }
    }

    public String getCode() {
        return barcode;
    }

    public double getQuantity() {
        return quantity;
    }

    public boolean isOriginalBarcodeNotContainsQuantity() {
        return originalBarcodes.stream().anyMatch(code -> !code.contains("-"));
    }
}
