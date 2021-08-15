package ai.superops.booking.exceptions;

public class BookingTimeoutException extends RuntimeException {

    public BookingTimeoutException(String msg) {
        super(msg);
    }
}
