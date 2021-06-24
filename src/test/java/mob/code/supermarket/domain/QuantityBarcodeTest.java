package mob.code.supermarket.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuantityBarcodeTest {

    @Test
    public void 解析不带数量的barcode() {
        QuantityBarcode quantityBarcode = new QuantityBarcode("12345678");
        assertThat(quantityBarcode.getQuantity()).isEqualTo(1);
        assertThat(quantityBarcode.getCode()).isEqualTo("12345678");
    }

    @Test
    public void 解析带数量的barcode() {
        QuantityBarcode quantityBarcode = new QuantityBarcode("22345678-3");
        assertThat(quantityBarcode.getQuantity()).isEqualTo(3);
        assertThat(quantityBarcode.getCode()).isEqualTo("22345678");
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void 商品数量不支持1位以上的小数() {
        exceptionRule.expect(SupermarketException.class);
        exceptionRule.expectMessage("wrong quantity of 22345678");
        new QuantityBarcode("22345678-3.11");
    }

    @Test
    public void 带商品数量的code_数量不能为0() {
        exceptionRule.expect(SupermarketException.class);
        exceptionRule.expectMessage("wrong quantity of 22345678");
        new QuantityBarcode("22345678-0");
    }

}
