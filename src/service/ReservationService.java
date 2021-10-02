package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService = null;
    // Map<RoomNumber, IRoom>
    private static Map<String, IRoom> rooms;
    //Map<Reservation.toString(), Reservation>
    private static Map<String, Reservation> reservations;

    ReservationService() {
        rooms = new HashMap<>();
        reservations = new HashMap<>();
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
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

    /**
     * Find all available rooms in given period.
     * @param checkInDate The check in date.
     * @param checkOutDate The check out date.
     * @return Rooms that are available from checkInDate to checkOutDate.
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> foundRooms = new HashSet<>();
        for (IRoom room : rooms.values()) {
            if (!roomIsBooked(room, checkInDate, checkOutDate)) {
                foundRooms.add(room);
            }
        }
        return foundRooms;
    }

    /**
     * Check if a room has been booked.
     * @param room The room to check.
     * @param checkInDate The check-in date.
     * @param checkOutDate The check-out date.
     * @return True if the room has not been booked, otherwise false.
     */
    public boolean roomIsBooked(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations.values()) {
            if (reservation.getRoom().equals(room) &&
                    reservation.getCheckInDate().equals(checkInDate) &&
                    reservation.getCheckOutDate().equals(checkOutDate)) {
                return true;
            }
        }
        return false;
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
        if (reservations.isEmpty()) {
            System.out.println("No reservation in database now.");
            return;
        }

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
