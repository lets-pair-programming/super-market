package mob.code.supermarket.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Barcodes {
    public Barcodes() {
    }

    public static List<QuantityBarcode> of(String[] barcodes) {
        return Arrays.stream(barcodes)
                .map(QuantityBarcode::new)
                .collect(groupingBy(QuantityBarcode::getCode))
                .entrySet().stream().map(entry ->
                        new QuantityBarcode(entry.getKey(),
                                entry.getValue()))
                .collect(Collectors.toList());
    }

}
