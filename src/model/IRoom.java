package model;

/**
 * A room interface. Implementations define getters.
 */
public interface IRoom {
    String getNumber();

    Double getPrice();

    RoomType getType();

    boolean isFree();
}
