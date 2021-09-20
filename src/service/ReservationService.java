package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import javax.naming.spi.ResolveResult;
import java.util.*;

public class ReservationService {
    // Map<RoomNumber, IRoom>
    private static Map<String, IRoom> rooms;
    //Map<Reservation.toString(), Reservation>
    private static Map<String, Reservation> reservations; //TODO: not sure if this should be static

    public ReservationService() {
        rooms = new HashMap<>();
        reservations = new HashMap<>();
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.put(newReservation.toString(), newReservation);
        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> foundRooms = new HashSet<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCheckInDate().equals(checkInDate) && reservation.getCheckOutDate().equals(checkOutDate)) {
                foundRooms.add(reservation.getRoom());
            }
        }
        return foundRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> foundReservations = new HashSet<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCustomer().equals(customer)) {
                foundReservations.add(reservation);
            }
        }
        return foundReservations;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservations.values()) {
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Collection<Reservation> getAllReservations() {
        return reservations.values();
    }
}
