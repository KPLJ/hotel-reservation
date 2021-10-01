package model;

import java.util.Locale;
import java.util.Objects;

public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public Room(String roomNumber, Double price, String roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        roomType = roomType.toLowerCase(Locale.ROOT);
        if (roomType.equals("single") || roomType.equals("s")) {
            this.enumeration = RoomType.SINGLE;
        } else if (roomType.equals("double") || roomType.equals("d")) {
            this.enumeration = RoomType.DOUBLE;
        } else {
            throw new IllegalArgumentException("Invalid roomType: " + roomType);
        }
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return price == 0;
    }

    @Override
    public String toString() {
        // "Room 101 SINGLE $100"
        return "Room " + roomNumber + " " + enumeration.toString() + " $" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
