package model;

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
        if (roomType.equals("SINGLE")) {
            this.enumeration = RoomType.SINGLE;
        } else if (roomType.equals("DOUBLE")) {
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
}
