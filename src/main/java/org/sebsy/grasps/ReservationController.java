package org.sebsy.grasps;

import org.sebsy.grasps.beans.Reservation;
import org.sebsy.grasps.daos.ClientDao;
import org.sebsy.grasps.daos.TypeReservationDao;

import org.sebsy.grasps.services.ReservationService;

public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Constructeur par défaut qui initialise le service avec les DAO par défaut.
     */
    public ReservationController() {
        this(new ReservationService(new ClientDao(), new TypeReservationDao()));
    }

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Reservation creerReservation(Params params) {
        return reservationService.creerReservation(params);
    }
}