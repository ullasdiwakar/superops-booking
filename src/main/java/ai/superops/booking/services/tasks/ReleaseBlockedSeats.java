package ai.superops.booking.services.tasks;

import ai.superops.booking.configuration.ApplicationContextUtils;
import ai.superops.booking.models.SeatBlock;
import ai.superops.booking.repositories.SeatBlockRepository;
import org.springframework.context.ApplicationContext;

public class ReleaseBlockedSeats implements Runnable {

    private SeatBlock block;

    public ReleaseBlockedSeats(SeatBlock block) {
        this.block = block;
    }

    @Override
    public void run() {
        SeatBlockRepository seatBlockRepository = ApplicationContextUtils.getApplicationContext().getBean(SeatBlockRepository.class);
        seatBlockRepository.deleteById(block.getId());
    }
}
