package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static CustomerService customerService;
    private static ReservationService reservationService;

    public HotelResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public  Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public  void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public  IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public  Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public  Collection<Reservation> getCustomerReservations(String customerEmail) {
        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public  Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }
}
