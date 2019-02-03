package ru.usefulcity.TextMenu;


import java.util.*;

/**
 * Created by yuri on 02.12.18.
 */
public class Menu implements Iterable<Menu> {
    private final String name;
    private Menu rootMenu = null;
    private Card cardInfo = null;
    private int itemsCounter = 0;
    private int id;
    private Map<Integer, Menu> items = new HashMap<>();

    public Menu(String name) {
        this.name = name;
    }

    public Menu(Card card){
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

    public Menu getById(int menuId){
        return items.get(menuId);
    }

    public Menu getRootMenu(){ return rootMenu; }

    public Card getCard() { return this.cardInfo; }

    public String getName(){ return name; }

    public boolean haveRoot(){return rootMenu != null;}

    public boolean isItem() { return itemsCounter == 0; }

    public boolean haveCard() { return cardInfo != null; } //TODO redundant method, deprecated

    public int getId(){ return id;}

    private Menu setCard(Card card) {
        this.cardInfo = card;
        return this;
    } //TODO deprecated because it is repeated class constructor

    private void setId(int id) { this.id = id; }

    private void setRootMenu(Menu rootMenu) { this.rootMenu = rootMenu; }

    @Override
    public Iterator<Menu> iterator() {
        return items.values().iterator();
    }
}
