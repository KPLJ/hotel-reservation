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
            System.out.println();
            System.out.println("Admin Menu");
            System.out.println("----------------------------------------");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Add multiple rooms");
            System.out.println("6. Back to main menu");
            System.out.println("----------------------------------------");

            Scanner in = new Scanner(System.in);
            command = in.nextInt();

            while (true) {
                if (command >= 1 && command <= 6) {
                    break;
                } else {
                    System.out.println("Please input 1~6:");
                    command = in.nextInt();
                }
            }

            switch (command) {
                case 1 -> displayAllCustomers();
                case 2 -> displayAllRooms();
                case 3 -> adminResource.displayAllReservations();
                case 4 -> addARoom();
                case 5 -> addMultipleRooms();
                case 6 -> {
                    return;
                }
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
        System.out.println("Please input room info (roomNumber price Single(s)/Double(d)):");
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
        System.out.println("How many rooms you want to add (1-10): ");
        String numStr = in.next();
        numStr = MainMenu.checkStringNumeric(numStr, in);
        int roomCount = Integer.parseInt(numStr);
        while (true) {
            if (roomCount < 1 || roomCount > 10 ) {
                System.out.println("Please input valid number (1-10): ");
                roomCount = in.nextInt();
            } else {
                break;
            }
        }

        List<IRoom> rooms = new ArrayList<>();
        System.out.println("Please input " + roomCount + " rooms info (roomNumber price Single(s)/Double(d)):");
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
