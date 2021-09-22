package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private final HotelResource hotelResource;

    public MainMenu() {
        hotelResource = new HotelResource();

        int command;
        while (true) {
            System.out.println("Main Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            try {
                Scanner in = new Scanner(System.in);
                command = in.nextInt();

                switch (command) {
                    case 1 -> findAndReserve();
                    case 2 -> displayReservations();
                    case 3 -> createAccount();
                    case 4 -> {
                        new AdminMenu();
                        System.out.println("Returned to main menu");
                    }
                    case 5 -> {
                        System.out.println("Exit successfully");
                        return;
                    }
                    default -> throw new IllegalArgumentException("Invalid input");
                }
            } catch (InputMismatchException e) {
                throw new InputMismatchException("Invalid input format.");
            }
        }
    }

    private void findAndReserve() {
        System.out.println("Please input check-in date and check out date (e.g. yyyymmdd-yyyymmdd): ");
        Scanner in = new Scanner(System.in);
        String dateString = in.next();
        int inYear = Integer.parseInt(dateString.substring(0, 4));
        int inMonth = Integer.parseInt(dateString.substring(4, 6));
        int inDate = Integer.parseInt(dateString.substring(6, 8));
        int outYear = Integer.parseInt(dateString.substring(9, 13));
        int outMonth = Integer.parseInt(dateString.substring(13, 15));
        int outDate = Integer.parseInt(dateString.substring(15, 17));
        Date checkInDate = new Date(inYear, inMonth, inDate);
        Date checkOutDate = new Date(outYear, outMonth, outDate);

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (!availableRooms.isEmpty()) {
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("No available room found.");
            return;
        }

        System.out.println("Please input the room number of the room you want to book: ");
        String roomNumber = in.next();
        System.out.println("Please input your email: ");
        String email = in.next();

        if (hotelResource.getCustomer(email) == null) {
            System.out.println("Booking failed, you need to create an account first.");
        } else {
            hotelResource.bookARoom(email, hotelResource.getRoom(roomNumber), checkInDate, checkOutDate);
            System.out.println("Successfully reserved Room " + roomNumber + " " + dateString);
        }
    }

    private void displayReservations() {
        String email;
        System.out.println("Please input your email: ");
        Scanner in = new Scanner(System.in);
        email = in.next();

        if (hotelResource.getCustomer(email) == null) {
            System.out.println("This account does not exist.");
            return;
        }

        Collection<Reservation> reservations = hotelResource.getCustomerReservations(email);

        if (!reservations.isEmpty()) {
            System.out.println("Your reservations: ");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("No reservation found");
        }
    }

    private void createAccount() {
        String firstName, lastName, email;
        Scanner in = new Scanner(System.in);
        System.out.print("Please input your first name: ");
        firstName = in.next();
        System.out.print("Please input your last name: ");
        lastName = in.next();
        System.out.print("Please input your email (e.g. email@domain.com): ");
        email = in.next();

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created");
    }
}
