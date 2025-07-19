package api;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;

/**
 * For public usage.
 * Used for defining the API.
 * Has little to no behavior.
 * Makes use of the Service classes to implement its methods.
 */
public class HotelResource {
    private static final HotelResource instance = new HotelResource();

    private HotelResource() {

    }

    public HotelResource getInstance() {
        return instance;
    }

    public Customer getCustomer(String email) {
        return null;
    }

    public void createACustomer(String email, String firstName, String lastName) {
    }

    public IRoom getRoom(String roomNumber) {
        return null;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return null;
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return null;
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return null;
    }
}
