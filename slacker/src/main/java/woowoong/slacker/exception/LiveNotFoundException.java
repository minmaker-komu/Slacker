package woowoong.slacker.exception;


public class LiveNotFoundException extends RuntimeException {
    public LiveNotFoundException(String message) {
        super(message);
    }
}