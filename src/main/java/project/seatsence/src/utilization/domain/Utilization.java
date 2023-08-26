package project.seatsence.src.utilization.domain;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

/** Utilization(이용) = Reservation(예약) + Walk-In(뱌로사용) */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Utilization extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @OneToOne
    @JoinColumn(name = "walk_in_id")
    private WalkIn walkIn;

    @Nullable
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'CHECK_IN'")
    private UtilizationStatus utilizationStatus;

    @NotNull private LocalDateTime startSchedule;
    @NotNull private LocalDateTime endSchedule;

    public void forceCheckOut(UtilizationStatus utilizationStatus) {
        this.utilizationStatus = utilizationStatus;
        this.endSchedule = LocalDateTime.now();
    }
}
