package project.seatsence.src.utilization.service.reservation;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.utilization.domain.reservation.ReservationStatus.APPROVED;
import static project.seatsence.src.utilization.domain.reservation.ReservationStatus.PENDING;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.utilization.dao.reservation.ReservationRepository;
import project.seatsence.src.utilization.domain.reservation.Reservation;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation findByIdAndState(Long id) {
        return reservationRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));
    }

    public Boolean isPossibleTimeToManageReservationStatus(Reservation reservation) {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(reservation.getEndSchedule());
    }

    public Boolean isPossibleReservationStatusToCancel(Reservation reservation) {
        Boolean isPossible = false;
        if((reservation.getReservationStatus().equals(PENDING)) || (reservation.getReservationStatus().equals(APPROVED))) {
            isPossible = true;
        }
        return isPossible;
    }

    public void checkValidationToModifyReservationStatus(Reservation reservation) {
        if (!isPossibleReservationStatusToCancel(reservation)) {
            throw new BaseException(INVALID_RESERVATION_STATUS);
        }

        if (!isPossibleTimeToManageReservationStatus(reservation)) {
            throw new BaseException(INVALID_TIME_TO_MODIFY_RESERVATION_STATUS);
        }
    }
}
