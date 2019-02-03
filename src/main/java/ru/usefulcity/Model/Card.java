package ru.usefulcity.Model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Card implements Iterable<String>{

    private Map<Field, String> card = new HashMap<>(4);

    public Card(){}

    public Card add(Field field, String param){
        card.put(field, param);
        return this;
    }

    public String get(Field field){
        return card.get(field);
    }

    @Override
    public Iterator<String> iterator() {
        return card.values().iterator();
    }

    public enum Field {
        name, desc, phone, link
    }
}
