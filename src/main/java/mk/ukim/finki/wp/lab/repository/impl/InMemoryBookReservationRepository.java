package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.repository.BookReservationRepository;

public class InMemoryBookReservationRepository implements BookReservationRepository {
    @Override
    public BookReservation save(BookReservation reservation) {
        DataHolder.reservations.removeIf(c -> c.getBookTitle().equals(reservation.getBookTitle()));
        DataHolder.reservations.add(reservation);
        return reservation;
    }
}
