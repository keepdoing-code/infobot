package ru.usefulcity.Model;

/**
 * Created on 08/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class MenuLoader {

    public static Menu createMenu() {
        Card card1 = new Card("Yuri").
                add("Coder").
                add("+7(911)500-00-00");

        Card card2 = new Card("Ivan").
                add("Builder").
                add("+7(911)500-00-00").
                add("https://vk.com/stritron");

        Card card3 = new Card("Sergei").
                add("Engineer").
                add("+7(911)500-00-00").
                add("https://vk.com/stritron");

        Card card4 = new Card("Alex").
                add("Economist").
                add("+7(911)500-00-00").
                add("https://vk.com/stritron");

        Menu itemCoder = new Menu(card1);
        Menu itemBuilder = new Menu(card2);
        Menu itemEngin = new Menu(card3);
        Menu itemEcon = new Menu(card4);

        Menu economistsMenu = new Menu("Economists");
        Menu engineerMenu = new Menu("Engineers");
        Menu otherSpec = new Menu("Other");
        Menu rootMenu = new Menu("Specialists");
        Menu utilsMenu = new Menu("Utils");
        Menu sysUtilsMenu = new Menu("Sysutils");

        rootMenu.addSubmenu(economistsMenu);
        rootMenu.addSubmenu(engineerMenu);
        rootMenu.addSubmenu(otherSpec);
        rootMenu.addSubmenu(utilsMenu);

        utilsMenu.addSubmenu(sysUtilsMenu);
        economistsMenu.addSubmenu(itemEcon);
        engineerMenu.addSubmenu(itemEngin);
        otherSpec.addSubmenu(itemCoder);
        otherSpec.addSubmenu(itemBuilder);

        return rootMenu;
    }

    public static String printAllMenu(Menu menu, String tab) {
        StringBuilder sb = new StringBuilder();

        if (!menu.haveParent()) {
            sb
                    .append(menu.getId())
                    .append(":")
                    .append("\t")
                    .append(menu.getName())
                    .append("\r\n");
        }

        for (Menu m : menu) {
            sb
                    .append(tab)
                    .append(m.getId())
                    .append(":")
                    .append("\t")
                    .append(m.getName())
                    .append("\r\n");
            if (m.haveCard()) {
                for(Card c: m.getCard()) {
                    sb
                            .append(tab)
                            .append("\t")
                            .append(c.getName())
                            .append("\r\n");
                }
            } else {
                sb.append(printAllMenu(m, tab + '\t'));
            }
        }
        return sb.toString();
    }
}
