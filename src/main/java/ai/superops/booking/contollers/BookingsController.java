package ai.superops.booking.contollers;

import ai.superops.booking.exceptions.OverbookingException;
import ai.superops.booking.models.Bookings;
import ai.superops.booking.models.ShowDetails;
import ai.superops.booking.repositories.ShowRepository;
import ai.superops.booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingsController {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/shows")
    @ResponseBody
    public List<ShowDetails> getShowDetails() {
        return showRepository.findAll();
    }

    @GetMapping("/seats-for-shows")
    @ResponseBody
    public List<String> getSeatsForShow(@RequestParam(name = "showId") int showId) {
        return bookingService.fetchAvailableSeatsForShow(showId);
    }

    @PostMapping("/select-seats")
    @ResponseBody
    public int selectSeats(@RequestBody List<String> seatNumbers, @RequestParam(name = "showId") int showId) throws OverbookingException {
        if (seatNumbers.size() > 6) throw new OverbookingException("Cannot select more than 6 seats");
        return bookingService.selectSeats(seatNumbers, showId);
    }

    @PostMapping("/book")
    @ResponseBody
    public Bookings book(@RequestParam(name = "blockingId") int blockingId) {
        return bookingService.book(blockingId);
    }

}
