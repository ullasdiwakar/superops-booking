package ai.superops.booking.repositories;

import ai.superops.booking.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query(value = "select number from seat where id not in (select seats_id from bookings_seats) and id not in (select seats_id from seat_block_seats) and cinema_id in (select cinema_id from show_details where id = ?1)", nativeQuery = true)
    List<String> getAvailableSeatNumbersForShow(int showId);

    List<Seat> findByNumberInAndCinemaId(List<String> selectedNumbers, int cinemaId);
}
