package ai.superops.booking.repositories;

import ai.superops.booking.models.ShowDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<ShowDetails, Integer> {

    List<ShowDetails> findAll();

    ShowDetails findById(int showId);
}
