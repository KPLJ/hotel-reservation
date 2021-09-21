package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final AdminResource adminResource;

    public AdminMenu() {
        adminResource = new AdminResource();

        int command;
        while (true) {
            System.out.println("Admin Menu");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Add multiple rooms");
            System.out.println("6. Back to main menu");

            Scanner in = new Scanner(System.in);

            command = in.nextInt();

            switch (command) {
                case 1:
                    displayAllCustomers();
                    break;
                case 2:
                    displayAllRooms();
                    break;
                case 3:
                    adminResource.displayAllReservations();
                    break;
                case 4:
                    addARoom();
                    break;
                case 5:
                    addMultipleRooms();
                    break;
                case 6:
                    return;
                default:
                    throw new IllegalArgumentException("Invalid input");
            }
        }
    }

    private void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomer();
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("No customer in database now");
        }
    }

    private void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (!rooms.isEmpty()) {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("No room in database now");
        }
    }

    private void addARoom() {
        Scanner in = new Scanner(System.in);
        List<IRoom> rooms = new ArrayList<>();
        System.out.println("Please input room info (roomNumber price roomType(SINGLE/DOUBLE)):");
        String roomNumber = in.next();
        Double price = in.nextDouble();
        String roomType = in.next();
        IRoom room = new Room(roomNumber, price, roomType);
        rooms.add(room);
        adminResource.addRoom(rooms);
        System.out.println("Room added");
    }

    private void addMultipleRooms() {
        Scanner in = new Scanner(System.in);
        System.out.println("How many rooms you want to add (must be a positive number): ");
        int roomCount = in.nextInt();
        if (roomCount <= 0) {
            throw new NumberFormatException("Number of room to add is not positive");
        } else {
            List<IRoom> rooms = new ArrayList<>();
            System.out.println("Please input " + roomCount + " rooms (roomNumber price roomType(SINGLE/DOUBLE)):");
            for (int i = 0; i < roomCount; i++) {
                String roomNumber = in.next();
                Double price = in.nextDouble();
                String roomType = in.next();
                IRoom room = new Room(roomNumber, price, roomType);
                rooms.add(room);
            }
            adminResource.addRoom(rooms);
            System.out.println("Room(s) added");
        }
    }
}
