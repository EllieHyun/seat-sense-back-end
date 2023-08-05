package project.seatsence.src.reservation.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.domain.ReservationStatus;
import project.seatsence.src.user.domain.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation save(Reservation reservation);

    Slice<Reservation>
            findAllByUserAndReservationStatusAndStateOrderByReservationStartDateAndTimeDesc(
                    User user, ReservationStatus reservationStatus, State state, Pageable pageable);
}
