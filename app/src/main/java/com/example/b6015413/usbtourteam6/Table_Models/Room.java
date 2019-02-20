package com.example.b6015413.usbtourteam6.Table_Models;

public class Room {

    private String name;
    private int level;
    private String prevRoom;
    private String coords;
    private String description;

    public Room(String name, int level, String prevRoom, String coords, String description) {
        this.name = name;
        this.level = level;
        this.prevRoom = prevRoom;
        this.coords = coords;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPrevRoom() {
        return prevRoom;
    }

    public void setPrevRoom(String prevRoom) {
        this.prevRoom = prevRoom;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (level != room.level) return false;
        if (name != null ? !name.equals(room.name) : room.name != null) return false;
        if (prevRoom != null ? !prevRoom.equals(room.prevRoom) : room.prevRoom != null)
            return false;
        if (coords != null ? !coords.equals(room.coords) : room.coords != null) return false;
        return description != null ? description.equals(room.description) : room.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + level;
        result = 31 * result + (prevRoom != null ? prevRoom.hashCode() : 0);
        result = 31 * result + (coords != null ? coords.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
