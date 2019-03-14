package com.example.b6015413.usbtourteam6.Table_Models;

import java.util.Objects;

public class Tutor {

    public int id;
    public String surname, firstname, room;

    /**
     * @param id
     * @param surname
     * @param firstName
     * @param room
     */
    public Tutor(int id, String surname, String firstName, String room) {
        this.id = id;
        this.surname = surname;
        this.firstname = firstName;
        this.room = room;
    }

    public Tutor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return id == tutor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, firstname, room);
    }
}
