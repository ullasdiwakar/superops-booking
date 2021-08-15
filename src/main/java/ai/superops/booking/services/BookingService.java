package ai.superops.booking.services;

import ai.superops.booking.exceptions.BookingTimeoutException;
import ai.superops.booking.exceptions.InvalidSeatSelectionException;
import ai.superops.booking.exceptions.PaymentFailedException;
import ai.superops.booking.models.Bookings;
import ai.superops.booking.models.Seat;
import ai.superops.booking.models.SeatBlock;
import ai.superops.booking.models.ShowDetails;
import ai.superops.booking.repositories.BookingsRepository;
import ai.superops.booking.repositories.SeatBlockRepository;
import ai.superops.booking.repositories.SeatRepository;
import ai.superops.booking.repositories.ShowRepository;
import ai.superops.booking.services.tasks.ReleaseBlockedSeats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatBlockRepository seatBlockRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private ScheduledExecutorService executorService;

    public List<String> fetchAvailableSeatsForShow(int showId) {
        return seatRepository.getAvailableSeatNumbersForShow(showId);
    }

    public SeatBlock selectSeats(List<String> seats, int showId) {
        List<String> availableSeats = seatRepository.getAvailableSeatNumbersForShow(showId);
        List<String> invalidSeats = new ArrayList<>();
        for (String seatNumber : seats) {
            if (!availableSeats.contains(seatNumber)) {
                invalidSeats.add(seatNumber);
            }
        }
        if (invalidSeats.size() > 0) {
            throw new InvalidSeatSelectionException(String.join(", ", invalidSeats) + " are invalid seats");
        }

        ShowDetails showDetails = showRepository.findById(showId);

        List<Seat> seatDetails = seatRepository.findByNumberInAndCinemaId(seats, showDetails.getCinema().getId());

        SeatBlock block = new SeatBlock();
        block.setSeats(seatDetails);
        block.setUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        block.setShowDetails(showDetails);

        SeatBlock blockedSeat = seatBlockRepository.saveAndFlush(block);
        executorService.schedule(new ReleaseBlockedSeats(blockedSeat), 2, TimeUnit.MINUTES);

        return blockedSeat;

    }

    public Bookings book(int blockingId) {
        SeatBlock seatBlock = seatBlockRepository.findById(blockingId);
        if (seatBlock == null) throw new BookingTimeoutException("Sorry!! Your selection has timed out");
        if (!paymentGateway()) throw new PaymentFailedException("Unfortunately, the booking cannot be done as your payment failed");

        seatBlockRepository.deleteById(seatBlock.getId());
        Bookings bookings = new Bookings();
        bookings.setUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        bookings.setShowDetails(seatBlock.getShowDetails());
        bookings.setSeats(seatBlock.getSeats());
        bookings.setPaymentStatus(true);

        return bookingsRepository.saveAndFlush(bookings);
    }

    private boolean paymentGateway() {
        return new Random().nextInt() % 2 == 0;
    }
}
