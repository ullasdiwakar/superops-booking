package ai.superops.booking.exceptions;

public class OverbookingException extends RuntimeException {

    public OverbookingException(String message) {
        super(message);
    }
}
