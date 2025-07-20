package model;

/**
 * Represents a free room in the hotel.
 */
public class FreeRoom extends Room {
    /**
     * Creates a free room with the specified number and type.
     *
     * @param number the room number.
     * @param type   the room type.
     */
    public FreeRoom(String number, RoomType type) {
        super(number, 0.0, type);
    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "number='" + getNumber() + '\'' +
                ", price=" + getPrice() +
                ", type=" + getType() +
                '}';
    }
}
