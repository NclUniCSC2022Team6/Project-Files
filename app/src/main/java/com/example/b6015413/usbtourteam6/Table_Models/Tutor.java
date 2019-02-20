package com.example.b6015413.usbtourteam6.Table_Models;

public class Tutor {

    private int id;
    private String room;
    private String name;

    public Tutor(int id, String room, String name) {
        this.id = id;
        this.room = room;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutor tutor = (Tutor) o;

        if (id != tutor.id) return false;
        if (room != null ? !room.equals(tutor.room) : tutor.room != null) return false;
        return name != null ? name.equals(tutor.name) : tutor.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
