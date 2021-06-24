package mob.code.supermarket.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReceiptItemTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void 对于独立包装商品_带有小数的数量是不允许的() {
        exceptionRule.expect(SupermarketException.class);
        exceptionRule.expectMessage("wrong quantity of 12345678");
        Item pizza = new Item("12345678", "pizza", "", 15.00, "0");

        new ReceiptItem(pizza, new QuantityBarcode("12345678", 1.5, "12345678"));
    }

    @Test
    public void 称重商品条码中必须包含数量_否则返回错误() {
        exceptionRule.expect(SupermarketException.class);
        exceptionRule.expectMessage("wrong quantity of 22345678");
        Item milk = new Item("22345678", "milk", "L", 12.30, "1");
        new ReceiptItem(milk, new QuantityBarcode("22345678", 1, "22345678"));
    }
}
