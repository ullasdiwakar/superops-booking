package ai.superops.booking.contollers;

import ai.superops.booking.exceptions.BookingTimeoutException;
import ai.superops.booking.exceptions.InvalidSeatSelectionException;
import ai.superops.booking.exceptions.OverbookingException;
import ai.superops.booking.exceptions.PaymentFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookingExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {OverbookingException.class, InvalidSeatSelectionException.class, BookingTimeoutException.class, PaymentFailedException.class})
    protected ResponseEntity<Object> handleOverBooking(RuntimeException ex, WebRequest request) {
        String body = ex.getMessage();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
