package mob.code.supermarket.legacy;

import mob.code.supermarket.domain.RawBarcodeFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BarcodeReader {

    List<StringBuilder> symbols;
    private static final int NB_CHARS_BY_LINE = 27;
    public static final String SA =
            " _ | ||_|   |  |   _  _||_  _  _| _|   |_|  |" +
                    " _ |_  _| _ |_ |_| _   |  | _ |_||_| _ |_| _|";

    public static BarcodeReader barcodeFactory() {
        BarcodeReader barcode = new BarcodeReader();
        barcode.initSymbols();
        return barcode;
    }

    private void initSymbols() {
        this.symbols = Stream.generate(StringBuilder::new)
                .limit(NB_CHARS_BY_LINE)
                .collect(Collectors.toList());
    }

    private String getNumber(String quantity) {
        StringBuilder n = new StringBuilder();
        symbols.stream().filter(s -> s.length() != 0).forEach(s ->
                n.append(getSymbolFromStringRepresentation(s.toString()))
        );
        return getString(n, quantity);
    }

    private String getString(StringBuilder n, String quantity) {
        if (quantity.isEmpty()) {
            return n.toString();
        }
        return String.format("%s-%s", n, quantity);
    }

    public int getSymbolFromStringRepresentation(final String representation) {
        if (!SA.contains(representation)) {
            throw new RawBarcodeFormatException("");
        }
        return SA.indexOf(representation) / 9;
    }


    public List<String> getBarcode(String[] barcodes) {
        // Read from string
        // read the line by 4-tuple
        List<String> result = new ArrayList<>();
        for (int i = 0; i < barcodes.length; i++) {
            if (((i + 1) % 4) != 0) {
                splitLineAndFillAccounts(barcodes[i]);
            } else {
                result.add(getNumber(barcodes[i]));
                initSymbols();
            }
        }
        return result;
    }

    private void splitLineAndFillAccounts(String stringLine) {

        for (int i = 3, j = 0; i <= stringLine.length(); i += 3, j++) {
            symbols.get(j).append(stringLine.substring(i - 3, i));

        }
    }
}
