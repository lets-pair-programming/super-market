package mob.code.supermarket.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ReceiptTest {

    @Test
    public void 没有收据项的时候打印收据() {
        Receipt receipt = new Receipt(Collections.emptyList());
        List<String> content = expected(new String[]{}, 0.0);
        assertThat(receipt.print()).isEqualTo(content);
    }

    @Test
    public void 有一个收据项的时候打印收据() {
        Item item = new Item("12345678", "pizza", "", 15.00, "0");
        ReceiptItem pizza = new ReceiptItem(item, new QuantityBarcode("12345678", 1, "12345678"));
        Receipt receipt = new Receipt(Arrays.asList(pizza));
        List<String> content = expected(new String[]{"pizza: 1 x 15.00 --- 15.00"}, 15.00);
        assertThat(receipt.print()).isEqualTo(content);
    }

    @Test
    public void 包含一个有单位商品的收据项的时候打印收据() {
        Item pizza = new Item("12345678", "pizza", "", 15.00, "0");
        Item milk = new Item("22345678", "milk", "L", 12.30, "1");
        Receipt receipt = new Receipt(Arrays.asList(
                new ReceiptItem(pizza, new QuantityBarcode("12345678", 1, "12345678")),
                new ReceiptItem(milk, new QuantityBarcode("22345678", 3, "22345678-3"))
        ));
        List<String> content = expected(new String[]{"pizza: 1 x 15.00 --- 15.00", "milk: 3(L) x 12.30 --- 36.90"}, 51.90);
        assertThat(receipt.print()).isEqualTo(content);
    }

    @Test
    public void 价格直接去掉尾数() {
        Item rice = new Item("82203002", "rice", "KG", 7.09, "1");
        Item orange = new Item("82203003", "orange", "KG", 13.07, "1");
        Receipt receipt = new Receipt(Arrays.asList(
                new ReceiptItem(rice, new QuantityBarcode("82203002", 1.5, "82203002-1.5")),
                new ReceiptItem(orange, new QuantityBarcode("82203003", 0.5, "82203003-0.5"))
        ));
        List<String> content = expected(new String[]{"rice: 1.5(KG) x 7.09 --- 10.63",
                "orange: 0.5(KG) x 13.07 --- 6.53",
        }, 17.16);
        assertThat(receipt.print()).isEqualTo(content);
    }


    private List<String> expected(String[] expectedItems, double totalPrice) {
        List<String> content = new ArrayList<>();
        content.add(Receipt.HEAD_LINE);
        content.addAll(Arrays.stream(expectedItems).collect(Collectors.toList()));
        content.add(Receipt.SEPARATOR_LINE);
        content.add(String.format("total: %.2f(CNY)", totalPrice));
        content.add(Receipt.ENDING_LINE);
        return content;
    }
}
