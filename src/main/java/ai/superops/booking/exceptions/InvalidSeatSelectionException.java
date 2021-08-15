package ai.superops.booking.exceptions;

public class InvalidSeatSelectionException extends RuntimeException {

    public InvalidSeatSelectionException(String message) {
        super(message);
    }
}
