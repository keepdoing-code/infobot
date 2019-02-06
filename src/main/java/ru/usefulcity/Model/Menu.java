package ru.usefulcity.Model;

import java.util.*;

public class Menu implements Iterable<Menu> {
    private static int uniqueID = 0;
    private final String name;
    private Menu parentMenu = null;
    private Card card = null;
    private int id = -1;
    private Map<Integer, Menu> items = new HashMap<>();

    public Menu(String name) {
        this.name = name;
        this.setUniqueId();
    }

    public Menu(Card card) {
        this(card.getName());
        this.setCard(card);
    }

    public void addSubmenu(Menu item) {
        int intID = Integer.valueOf(item.getId());
        item.setParentMenu(this);
        items.put(intID, item);
    }

    public Menu getSubmenu(int menuId) {
        if (items.get(menuId) == null) {
            return this;
        }
        return items.get(menuId);
    }

    public boolean haveParent() {
        return parentMenu != null;
    }

    public Menu getParent() {
        return parentMenu;
    }

    public boolean haveCard() {
        return card != null;
    }

    public Card getCard() {
        return this.card;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return String.valueOf(id);
    }


    private void setParentMenu(Menu upMenu) {
        this.parentMenu = upMenu;
    }

    private void setCard(Card card) {
        this.card = card;
    }

    private void setUniqueId() {
        if (this.id == -1) {
            this.id = uniqueID;
            uniqueID++;
        }
    }

    @Override
    public Iterator<Menu> iterator() {
        return items.values().iterator();
    }
}
