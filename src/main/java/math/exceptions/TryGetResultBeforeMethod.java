package math.exceptions;

public class TryGetResultBeforeMethod extends RuntimeException {
    public TryGetResultBeforeMethod(String message) {
        super(message);
        getStackTrace();
    }
}
