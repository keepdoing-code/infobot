package ru.usefulcity.Model;

public class Card {
    private String name;
    private String text = "";

    public Card(String name) {
        this.name = name;
    }

    public Card addText(String str) {
        text = text + str + "\r\n";
        return this;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
