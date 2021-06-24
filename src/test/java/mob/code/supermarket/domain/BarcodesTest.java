package mob.code.supermarket.domain;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BarcodesTest {

    @Test
    public void 汇总重复扫码的商品() {
        String[] barcodes = {"12345678", "12345678", "12345678"};
        List<QuantityBarcode> quantityBarcodes = Barcodes.of(barcodes);
        assertThat(quantityBarcodes.size()).isEqualTo(1);
    }
}
