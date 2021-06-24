package mob.code.supermarket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReceiptItem {
    private final double quantity;
    private final Item item;

    public ReceiptItem(Item item, QuantityBarcode barcode) {
        this.quantity = barcode.getQuantity();
        this.item = item;
        checkItemLegal(barcode);
    }

    private void checkItemLegal(QuantityBarcode barcode) {
        checkIndividualPackageItem();
        checkWeightingItem(barcode);
    }

    private void checkWeightingItem(QuantityBarcode barcode) {
        if (item.isWeightingItem() && barcode.isOriginalBarcodeNotContainsQuantity()) {
            throw new SupermarketException(String.format("wrong quantity of %s", barcode.getCode()));
        }
    }

    private void checkIndividualPackageItem() {
        if (item.isIndividualPackage() && !isIntegerForDouble(quantity)) {
            throw new SupermarketException(String.format("wrong quantity of %s", item.getBarcode()));
        }
    }

    public double totalPrice() {
        return BigDecimal.valueOf(quantity * item.getPrice())
                .setScale(2, RoundingMode.DOWN).doubleValue();
    }

    public static boolean isIntegerForDouble(double obj) {
        // 精度范围
        double eps = 1e-10;
        return obj - Math.floor(obj) < eps;
    }

    public String getName() {
        return item.getName();
    }

    public double getPrice() {
        return item.getPrice();
    }

    public String getUnit() {
        return item.getUnit();
    }

    String formatItem() {
        return String.format(
                "%s: %s%s x %.2f --- %.2f",
                getName(),
                formatQuantity(),
                formatUnit(getUnit()),
                getPrice(),
                totalPrice());
    }

    private String formatQuantity() {
        if (isIntegerForDouble(quantity)) {
            return String.valueOf((int) quantity);
        }
        return String.format("%.1f", quantity);
    }

    private String formatUnit(String unit) {
        if (unit.isEmpty()) {
            return "";
        }
        return "(" + unit + ")";
    }
}
