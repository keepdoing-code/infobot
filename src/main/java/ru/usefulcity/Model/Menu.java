package ru.usefulcity.Model;


import java.util.*;

/**
 * Created by yuri on 02.12.18.
 */
public class Menu implements Iterable<Menu> {
    private final String name;
    private Menu rootMenu = null;
    private Card card = null;
    private int itemsCounter = 0;
    private int id;
    private Map<Integer, Menu> items = new HashMap<>();

    public Menu(String name) {
        this.name = name;
    }

    public Menu(Card card) {
        this.setCard(card);
        this.name = card.get(Card.Field.name);
    }

    public int addItem(Menu item) {
        items.put(itemsCounter, item);
        item.setId(itemsCounter);
        return itemsCounter++;
    }

    public void addSubMenu(Menu subMenu) {
        subMenu.setRootMenu(this);
        addItem(subMenu);
    }

    public Menu getById(int menuId) {
        return items.get(menuId);
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public String getName() {
        return name;
    }

    public boolean haveCard() {
        return card != null;
    }

    public Card getCard() {
        return this.card;
    }

    public boolean haveRoot() {
        return rootMenu != null;
    }

    private void setRootMenu(Menu rootMenu) {
        this.rootMenu = rootMenu;
    }

    private void setCard(Card card) {
        this.card = card;
    }

    public String getId() {
        return String.valueOf(id);
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public Iterator<Menu> iterator() {
        return items.values().iterator();
    }
}
