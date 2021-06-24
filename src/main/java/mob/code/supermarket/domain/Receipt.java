package mob.code.supermarket.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    public static final String HEAD_LINE = "****** SuperMarket receipt ******";
    public static final String SEPARATOR_LINE = "---------------------------------";
    public static final String ENDING_LINE = "*********************************";
    private final List<ReceiptItem> receiptItems;

    public Receipt(List<ReceiptItem> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public List<String> print() {
        List<String> content = new ArrayList<>();
        content.add(HEAD_LINE);
        content.addAll(printItems(receiptItems));
        content.add(SEPARATOR_LINE);
        content.add(String.format("total: %.2f(CNY)", getTotalPrice()));
        content.add(ENDING_LINE);
        return content;
    }

    private double getTotalPrice() {
        return receiptItems.stream()
                .map(ReceiptItem::totalPrice)
                .reduce(0.0, Double::sum);
    }

    private List<String> printItems(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .map(ReceiptItem::formatItem)
                .collect(Collectors.toList());
    }

}
