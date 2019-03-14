package com.example.b6015413.usbtourteam6.Table_Models;

public class Tutor {

    public int id;
    public String surname,firstname, room;

    /**
     *
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
}
