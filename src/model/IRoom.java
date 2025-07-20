package model;

/**
 * A room interface.
 * Implementations define getters.
 */
public interface IRoom {
    /**
     * Get the number of a room.
     *
     * @return the room number.
     */
    String getNumber();

    /**
     * Get the price of a room.
     *
     * @return the room price.
     */
    Double getPrice();

    /**
     * Get the type of room.
     *
     * @return the room type.
     */
    RoomType getType();

    /**
     * Check if the room is free.
     *
     * @return true if the room is free.
     */
    boolean isFree();
}
