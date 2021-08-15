package ai.superops.booking.repositories;

import ai.superops.booking.models.SeatBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatBlockRepository extends JpaRepository<SeatBlock, Integer> {

    SeatBlock saveAndFlush(SeatBlock block);

    SeatBlock findById(int id);

    void deleteById(int id);
}
