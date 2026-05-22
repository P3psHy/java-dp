package org.sebsy.grasps;

import org.junit.Before;
import org.junit.Test;
import org.sebsy.grasps.beans.Reservation;

import static org.junit.Assert.assertEquals;

public class ReservationControllerTest {

    private static final double DELTA = 1e-7;
    private ReservationController controller;

    @Before
    public void setUp() {
        controller = new ReservationController();
    }

    @Test
    public void creerReservation_Theatre_Premium() {
        Params params = new Params();
        params.setDateReservation("20/11/2020 19:55:00");
        params.setNbPlaces(3);
        params.setIdentifiantClient("1");
        params.setTypeReservation("TH");

        Reservation reservation = controller.creerReservation(params);

        assertEquals(382.5, reservation.getTotal(), DELTA);
    }

    @Test
    public void creerReservation_Theatre_NonPremium() {
        Params params = new Params();
        params.setDateReservation("20/11/2020 19:55:00");
        params.setNbPlaces(3);
        params.setIdentifiantClient("3");
        params.setTypeReservation("TH");

        Reservation reservation = controller.creerReservation(params);

        assertEquals(450.0, reservation.getTotal(), DELTA);
    }

    @Test
    public void creerReservation_Cinema_Premium() {
        Params params = new Params();
        params.setDateReservation("21/11/2020 20:30:00");
        params.setNbPlaces(4);
        params.setIdentifiantClient("2");
        params.setTypeReservation("CI");

        Reservation reservation = controller.creerReservation(params);

        assertEquals(43.6, reservation.getTotal(), DELTA);
    }

    @Test
    public void creerReservation_Cinema_NonPremium() {
        Params params = new Params();
        params.setDateReservation("21/11/2020 20:30:00");
        params.setNbPlaces(4);
        params.setIdentifiantClient("3");
        params.setTypeReservation("CI");

        Reservation reservation = controller.creerReservation(params);

        assertEquals(43.6, reservation.getTotal(), DELTA);
    }
}