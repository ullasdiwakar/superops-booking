package ai.superops.booking.repositories;

import ai.superops.booking.models.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

    Bookings saveAndFlush(Bookings bookings);
}
