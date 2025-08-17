package model;

/**
 * Represents the interface of a room.
 */
public interface IRoom {
    String getNumber();

    Double getPrice();

    RoomType getType();

    boolean isFree();
}