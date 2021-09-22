package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return customer.toString() + " Room " + room.getRoomNumber() + " $" + room.getRoomPrice() + "/night " + parseDate(checkInDate) + "-" + parseDate(checkOutDate);
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Parse a Date object into yyyymmdd format.
     *
     * @param date The Date object to parse.
     * @return A string in format of yyyymmdd.
     */
    public static String parseDate(Date date) {
        return Integer.toString(date.getYear()) + Integer.toString(date.getMonth()) +
                (date.getDate() < 10 ? "0" + Integer.toString(date.getDate()) : Integer.toString(date.getDate()));
    }
}
