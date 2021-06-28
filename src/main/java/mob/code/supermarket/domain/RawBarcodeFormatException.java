package mob.code.supermarket.domain;

public class RawBarcodeFormatException extends RuntimeException {
    public RawBarcodeFormatException(String message, Throwable exception) {
        super(message, exception);
    }

    public RawBarcodeFormatException(String message) {
        super(message);
    }

    public RawBarcodeFormatException(Throwable exception) {
        super(exception);
    }
}
