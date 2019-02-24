package com.example.b6015413.usbtourteam6.Model;

public class Tutor {

    public int id;
    public String name,surname,room;

    public Tutor(int id, String name, String surname, String room) {
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
