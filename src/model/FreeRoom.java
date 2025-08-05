package model;

/**
 * Represents the domain of a free room. Models the data object for the hotel reservation application domain.
 */
final public class FreeRoom extends Room {
    /**
     * Creates a free room with the specified number and type.
     *
     * @param number the room number.
     * @param type   the room type.
     */
    public FreeRoom(String number, RoomType type) {
        // TODO?
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
