package mob.code.supermarket.domain;

public class SupermarketException extends RuntimeException {
    public SupermarketException(String message, Throwable exception) {
        super(message, exception);
    }

    public SupermarketException(String message) {
        super(message);
    }

    public SupermarketException(Throwable exception) {
        super(exception);
    }
}
