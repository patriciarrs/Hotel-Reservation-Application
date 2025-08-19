package service;

import model.Customer;
import model.Dates;
import model.IRoom;
import model.Reservation;
import model.Room;
import model.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Communicates with the resources to build the business logic necessary to provide feedback to the UI.
 * <p>
 * Stateful service (remembers things for the project) that uses Collections to manage information. As such, there's
 * only one of each service (Singleton).
 */
final public class ReservationService {
    private static ReservationService instance;

    final private Map<String, List<Reservation>> roomNumberToReservations;
    final private Map<String, IRoom> roomNumberToRoom;

    private ReservationService() {
        roomNumberToReservations = new HashMap<>();
        roomNumberToRoom = new HashMap<>();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }

    /**
     * Get a room from the roomNumberToRoom map.
     *
     * @param roomNumber the room number
     * @return the room.
     */
    public IRoom getRoom(String roomNumber) {
        return roomNumberToRoom.get(roomNumber);
    }

    /**
     * Find the available rooms for the desired dates.
     *
     * @param dates          the check-in and check-out dates for this reservation.
     * @param roomSearchType the room search type - A (all rooms), P (only paid room) or F (only free rooms).
     * @return the available rooms for the desired dates.
     */
    public List<IRoom> findAvailableRooms(Dates dates, String roomSearchType) {
        List<String> searchTypeRoomNumbers = getSearchTypeRoomNumbers(roomSearchType);
        List<IRoom> availableRooms = new ArrayList<>();

        for (String searchTypeRoomNumber : searchTypeRoomNumbers) {
            List<Reservation> roomReservations = roomNumberToReservations.get(searchTypeRoomNumber);

            if (roomReservations == null) {
                availableRooms.add(roomNumberToRoom.get(searchTypeRoomNumber));
            } else {
                for (Reservation reservation : roomReservations) {
                    boolean isBooked =
                            checkIsBooked(reservation.getCheckIn(), reservation.getCheckOut(), dates.checkIn(),
                                    dates.checkOut());

                    if (!isBooked) {
                        availableRooms.add(reservation.getRoom());
                    }
                }
            }
        }

        return availableRooms;
    }

    /**
     * Get the room numbers based on the search type.
     *
     * @param roomSearchType the room search type - A (all rooms), P (only paid room) or F (only free rooms).
     * @return the room numbers.
     */
    private List<String> getSearchTypeRoomNumbers(String roomSearchType) {
        Collection<IRoom> allHotelRooms = roomNumberToRoom.values();

        Collection<IRoom> searchTypeRooms = switch (roomSearchType) {
            case "P" -> allHotelRooms.stream().filter(room -> !room.isFree()).toList();
            case "F" -> allHotelRooms.stream().filter(IRoom::isFree).toList();
            default -> allHotelRooms;
        };

        return searchTypeRooms.stream().map(IRoom::getNumber).toList();
    }

    /**
     * Check if the room is already booked for the desired dates.
     *
     * @param existingCheckIn  the existing reservation check-in date.
     * @param existingCheckOut the existing reservation check-out date.
     * @param desiredCheckIn   the desired reservation check-in date.
     * @param desiredCheckOut  the desired reservation check-out date.
     * @return true if the room is already booked for the desired dates.
     */
    private boolean checkIsBooked(LocalDate existingCheckIn, LocalDate existingCheckOut, LocalDate desiredCheckIn,
                                  LocalDate desiredCheckOut) {
        return existingCheckIn.isBefore(desiredCheckOut) && desiredCheckIn.isBefore(existingCheckOut);
    }

    /**
     * Get the reservations for a customer.
     *
     * @param customer the customer.
     * @return the customer reservations.
     */
    public List<Reservation> getCustomerReservations(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();

        for (List<Reservation> roomReservation : roomNumberToReservations.values()) {
            for (Reservation reservation : roomReservation) {
                if (reservation.getCustomer().equals(customer)) {
                    customerReservations.add(reservation);
                }
            }
        }

        return customerReservations;
    }

    /**
     * Print all reservations.
     */
    public void printAllReservations() {
        System.out.println(roomNumberToReservations);
    }

    /**
     * Get all rooms.
     *
     * @return all rooms.
     */
    public List<IRoom> getAllRooms() {
        return roomNumberToRoom.values().stream().sorted(Comparator.comparing(IRoom::getNumber)).toList();
    }

    /**
     * Add rooms and reservations test data.
     */
    public void addTestData(Customer[] customers) {
        addRoom(new Room("100", 120.0, RoomType.SINGLE));
        addRoom(new Room("101", 130.0, RoomType.DOUBLE));
        addRoom(new Room("102", 125.0, RoomType.SINGLE));
        addRoom(new Room("103", 100.0, RoomType.DOUBLE));
        addRoom(new Room("104", 145.0, RoomType.DOUBLE));
        addRoom(new Room("105", 100.0, RoomType.SINGLE));
        addRoom(new Room("106", 250.0, RoomType.DOUBLE));
        addRoom(new Room("107", 0.0, RoomType.SINGLE));

        System.out.println("Rooms: " + roomNumberToRoom);

        reserveRoom(customers[0], roomNumberToRoom.get("100"),
                new Dates(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 15)));
        reserveRoom(customers[1], roomNumberToRoom.get("101"),
                new Dates(LocalDate.of(2026, 1, 16), LocalDate.of(2026, 1, 31)));

        System.out.println("Reservations: " + roomNumberToReservations);
    }

    /**
     * Add a room to the roomNumberToRoom map.
     *
     * @param room the room.
     */
    public void addRoom(IRoom room) {
        roomNumberToRoom.put(room.getNumber(), room);
    }

    /**
     * Reserve a room.
     *
     * @param customer the customer that is reserving the room.
     * @param room     the room that is being reserved.
     * @param dates    the check-in and check-out dates for this reservation.
     */
    public void reserveRoom(Customer customer, IRoom room, Dates dates) {
        List<Reservation> previousReservations = roomNumberToReservations.get(room.getNumber());

        List<Reservation> reservations =
                previousReservations == null ? new ArrayList<>() : new ArrayList<>(previousReservations);

        reservations.add(new Reservation(customer, room, dates.checkIn(), dates.checkOut()));

        roomNumberToReservations.put(room.getNumber(), reservations);
    }
}
