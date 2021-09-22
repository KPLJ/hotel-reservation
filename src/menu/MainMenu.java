package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    private final HotelResource hotelResource;
    private final String emailRegex = "^(.+)+@(.+)+.com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public MainMenu() {
        hotelResource = new HotelResource();

        System.out.println("Welcome to hotel reservation application!");

        int command;
        while (true) {
            System.out.println();
            System.out.println("Main Menu");
            System.out.println("----------------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("----------------------------------------");

            Scanner in = new Scanner(System.in);
            System.out.println("Please select a number for menu option: ");
            command = in.nextInt();

            while (true) {
                if (command >= 1 && command <= 5) {
                    break;
                } else {
                    System.out.println("Please input 1~5:");
                    command = in.nextInt();
                }
            }

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
            }
        }
    }

    private void findAndReserve() {
        System.out.println("Please input check-in date and check out date (e.g. yyyymmdd-yyyymmdd): ");
        Scanner in = new Scanner(System.in);
        String dataString = in.next();

        while(true) {
            if (dataString.length() != "yyyymmdd-yyyymmdd".length()) {
                System.out.println("Please input valid date format (yyyymmdd-yyyymmdd): ");
                dataString = in.next();
            } else {
                break;
            }
        }

        while (true) {
            if (isInteger(dataString.substring(0, 8)) && isInteger(dataString.substring(9, 17))) {
                break;
            } else {
                System.out.println("Please input dates in correct format (yyyymmdd-yyyymmdd): ");
                dataString = in.next();
            }
        }

        int inYear = Integer.parseInt(dataString.substring(0, 4));
        int inMonth = Integer.parseInt(dataString.substring(4, 6));
        int inDate = Integer.parseInt(dataString.substring(6, 8));
        int outYear = Integer.parseInt(dataString.substring(9, 13));
        int outMonth = Integer.parseInt(dataString.substring(13, 15));
        int outDate = Integer.parseInt(dataString.substring(15, 17));
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
        while (true) {
            if (hotelResource.getRoom(roomNumber) != null) {
                break;
            } else {
                System.out.println("Please input the correct room number: ");
                roomNumber = in.next();
            }
        }

        System.out.println("Please input your email (user@domain.com): ");
        String email = in.next();
        email = checkEmailValidity(email, in, hotelResource, pattern);
        if (email == null) {
            return;
        }

        if (hotelResource.getCustomer(email) == null) {
            System.out.println("Booking failed, you need to create an account first.");
        } else {
            Reservation newReservation = hotelResource.bookARoom(email, hotelResource.getRoom(roomNumber), checkInDate, checkOutDate);
            System.out.println("Successfully reserved Room " + roomNumber + " $" + newReservation.getRoom().getRoomPrice() + "/night " + dataString);
        }
    }

    private void displayReservations() {
        String email;
        System.out.println("Please input your email (user@domain.com): ");
        Scanner in = new Scanner(System.in);
        email = in.next();
        email = checkEmailValidity(email, in, hotelResource, pattern);
        if (email == null) {
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
        System.out.print("Please input your email (user@domain.com): ");
        email = in.next();
        email = checkEmailFormat(email, in, pattern);

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created");
    }

    /**
     * Check if a String can be converted to Integer.
     *
     * @param s The String to be checked.
     * @return True if the String can be converted to int, otherwise false.
     */
    public static boolean isInteger(String s) {
        if (s == null) {
            return false;
        }

        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        if (s == null) {
            return false;
        }

        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String checkStringNumeric(String numStr, Scanner in) {
        while(true) {
            if (isInteger(numStr)) {
                break;
            } else {
                System.out.println("Please input valid number: ");
                numStr = in.next();
            }
        }
        return numStr;
    }

    public static String checkEmailFormat(String email, Scanner in, Pattern pattern) {
        while (true) {
            if (!pattern.matcher(email).matches()) {
                System.out.println("Please input email in the correct format (user@domain.com): ");
                email = in.next();
            } else {
                break;
            }
        }
        return email;
    }

    /**
     * Check if an email is in database and valid in format
     *
     * @param email         The email to be checked.
     * @param in            The Scanner object.
     * @param hotelResource The hotelResource static object.
     * @param pattern       The email pattern.
     * @return The valid email; null if email is not in database.
     */
    public static String checkEmailValidity(String email, Scanner in, HotelResource hotelResource, Pattern pattern) {
        while (true) {
            if (!pattern.matcher(email).matches()) {
                System.out.println("Please input email in the correct format (user@domain.com): ");
                email = in.next();
            } else if (hotelResource.getCustomer(email) == null) {
                System.out.println("Account does not exist.");
                return null;
            } else {
                break;
            }
        }
        return email;
    }
}
