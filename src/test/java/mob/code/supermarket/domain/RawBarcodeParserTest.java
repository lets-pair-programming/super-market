package mob.code.supermarket.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RawBarcodeParserTest {

    @Test
    public void 解析一个原始条码_不包含数量() {
        RawBarcodeParser rawBarcodeParser = new RawBarcodeParser();
        String[] rawBarcodes = {
                "    _  _     _  _  _  _ ",
                "|   _| _||_||_ |_   ||_|",
                "|  |_  _|  | _||_|  ||_|",
                ""};
        List<String> parsedBarcodes = rawBarcodeParser.parse(rawBarcodes);
        assertThat(parsedBarcodes.size()).isEqualTo(1);
        assertThat(parsedBarcodes.get(0)).isEqualTo("12345678");
    }

    @Test
    public void 解析一个原始条码_包含数量() {
        RawBarcodeParser rawBarcodeParser = new RawBarcodeParser();
        String[] rawBarcodes = {
                "    _  _     _  _  _  _ ",
                "|   _| _||_||_ |_   ||_|",
                "|  |_  _|  | _||_|  ||_|",
                "2"};
        List<String> parsedBarcodes = rawBarcodeParser.parse(rawBarcodes);
        assertThat(parsedBarcodes.size()).isEqualTo(1);
        assertThat(parsedBarcodes.get(0)).isEqualTo("12345678-2");
    }

    @Test
    public void 解析多个原始条码() {
        RawBarcodeParser rawBarcodeParser = new RawBarcodeParser();
        String[] rawBarcodes = {
                "    _  _     _  _  _  _ ",
                "|   _| _||_||_ |_   ||_|",
                "|  |_  _|  | _||_|  ||_|",
                "",
                " _  _  _     _  _  _  _ ",
                " _| _| _||_||_ |_   ||_|",
                "|_ |_  _|  | _||_|  ||_|",
                "3.5"
        };
        List<String> parsedBarcodes = rawBarcodeParser.parse(rawBarcodes);
        assertThat(parsedBarcodes.size()).isEqualTo(2);
        assertThat(parsedBarcodes.get(0)).isEqualTo("12345678");
        assertThat(parsedBarcodes.get(1)).isEqualTo("22345678-3.5");
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void 输入条码长度不正确() {
        RawBarcodeParser rawBarcodeParser = new RawBarcodeParser();
        String[] rawBarcodes = {
                "    _  -     _  _  _  _ ",
                "|   _| _||_||_|_   ||_|",
                "|  |_  _|  | _||_|  ||_|"
        };
        exceptionRule.expect(SupermarketException.class);
        exceptionRule.expectMessage("can not recognize barcode:\n" +
                String.join("\n", Arrays.asList(rawBarcodes)));
        rawBarcodeParser.parse(rawBarcodes);
    }

    @Test
    public void 输入条码本身不正确() {
        RawBarcodeParser rawBarcodeParser = new RawBarcodeParser();
        String[] rawBarcodes = {
                "    _  -   WX_  _  _  _ ",
                "|   _| _||_||_|_   ||_|",
                "| _||_|  ||_|",
                ""
        };
        exceptionRule.expect(SupermarketException.class);
        exceptionRule.expectMessage("can not recognize barcode:\n" +
                String.join("\n", Arrays.asList(rawBarcodes)));
        rawBarcodeParser.parse(rawBarcodes);
    }
}
