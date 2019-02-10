package ru.usefulcity.Model;

public class Card {
    private int id = -1;
    private String name;
    private String text = "";

    public Card(String name) {
        this.name = name;
    }

    public Card addText(String str) {
        text = text + str + "\r\n";
        return this;
    }

    public Card setId(String id) {
        this.id = Integer.valueOf(id);
        return this;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

