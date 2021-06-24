package mob.code.supermarket.domain;

import mob.code.supermarket.legacy.BarcodeReader;

import java.util.Arrays;
import java.util.List;

public class RawBarcodeParser {
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
}
