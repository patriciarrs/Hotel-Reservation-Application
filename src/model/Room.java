package model;

/**
 * Represents a room in the hotel.
 * Provides methods for getting properties.
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
    public String getNumber() {
        return number;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public RoomType getType() {
        return type;
    }

    @Override
    public boolean isFree() {
        return false; // TODO
    }

    @Override
    public String toString() {
        return "model.Room{" +
                "number='" + number + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
