package ru.usefulcity.Model;

import java.util.*;

public class Menu implements Iterable<Menu> {
    private static int uniqueID = 0;
    private int id = -1;
    private Menu parentMenu = null;
    private final String name;
    private List<Card> cards = new ArrayList<>();
    private Map<Integer, Menu> items = new HashMap<>();


    public Menu(String name, int menuId){
        this.name = name;
        this.id = menuId;
    }

    public Menu(String name) {
        this.name = name;
        this.setUniqueId();
    }

    public Menu(String menuName, Card card) {
        this(menuName);
        this.cards.add(card);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public List<Card> getCards() {
        return this.cards;
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

    public void addCard(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public boolean haveCard() {
        return cards.size() != 0;
    }

    @Override
    public Iterator<Menu> iterator() {
        return items.values().iterator();
    }

    @Override
    public String toString() {
        return toString(this,"");
    }



    private void setParentMenu(Menu upMenu) {
        this.parentMenu = upMenu;
    }

    private void setUniqueId() {
        if (this.id == -1) {
            this.id = uniqueID;
            uniqueID++;
        }
    }

    private String toString(Menu menu, String tab) {
        StringBuilder sb = new StringBuilder();

        if (!menu.haveParent()) {
            sb.append(menu.getId()).append(":").append("\t").append(menu.getName()).append("\r\n");
        }

        for (Menu m : menu) {
            sb.append(tab).append(m.getId()).append(":").append("\t").append(m.getName()).append("\r\n");
            if (m.haveCard()) {
                for (Card c : m.getCards()) {
                    sb.append(tab).append("\t").append(c.getName()).append("\r\n");
                }
            } else {
                sb.append(toString(m, tab + '\t'));
            }
        }
        return sb.toString();
    }
}
