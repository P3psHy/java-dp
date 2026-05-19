package org.sebsy.grasps.services;

import org.sebsy.grasps.Params;
import org.sebsy.grasps.beans.Client;
import org.sebsy.grasps.beans.Reservation;
import org.sebsy.grasps.beans.TypeReservation;
import org.sebsy.grasps.daos.ClientDao;
import org.sebsy.grasps.daos.TypeReservationDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final ClientDao clientDao;
    private final TypeReservationDao typeReservationDao;

    public ReservationService(ClientDao clientDao,
                              TypeReservationDao typeReservationDao) {
        this.clientDao = clientDao;
        this.typeReservationDao = typeReservationDao;
    }

    public Reservation creerReservation(Params params) {

        LocalDateTime dateReservation =
                LocalDateTime.parse(params.getDateReservation(), FORMATTER);

        Client client =
                clientDao.extraireClient(params.getIdentifiantClient());

        TypeReservation type =
                typeReservationDao.extraireTypeReservation(
                        params.getTypeReservation()
                );

        Reservation reservation = new Reservation(dateReservation);

        reservation.setNbPlaces(params.getNbPlaces());
        reservation.setClient(client);

        double total = calculerTotal(
                type,
                params.getNbPlaces(),
                client.isPremium()
        );

        reservation.setTotal(total);

        client.ajouterReservation(reservation);

        return reservation;
    }

    private double calculerTotal(TypeReservation type,
                                 int nbPlaces,
                                 boolean premium) {

        double total = type.getMontant() * nbPlaces;

        if (premium) {
            return total * (1 - type.getReductionPourcent() / 100.0);
        }

        return total;
    }
}