package model;

import java.util.Objects;

/**
 * Represents a room in the hotel. Provides methods for getting properties.
 */
public class Room implements IRoom {
    private final String number;
    private final Double price;
    private final RoomType type;

    /**
     * Creates a room with the specified number, price and type.
     *
     * @param number the room number.
     * @param price  the room price.
     * @param type   the room type.
     */
    public Room(String number, Double price, RoomType type) {
        this.number = number;
        this.price = price;
        this.type = type;
    }

    @Override
    final public String getNumber() {
        return number;
    }

    @Override
    final public Double getPrice() {
        return price;
    }

    @Override
    final public RoomType getType() {
        return type;
    }

    @Override
    final public boolean isFree() {
        return true; // TODO?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(number, room.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        String roomType = type.equals(RoomType.SINGLE) ? "Single" : "Double";
        return "Room number: " + number + "; " + roomType + " bed room; Price: $" + price + ".";
    }
}