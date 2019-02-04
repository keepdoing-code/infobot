package ru.usefulcity.Deprecated;

import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import java.util.InputMismatchException;

/**
 * Created by yuri on 05.12.18.
 */
public class MenuWrapper {
    private static final String ASK_STRING = ">";
    private static final String UNKNOWN_COMMAND = "Unknown command";
    private static final String WRONG_ITEM = "Wrong item";
    private static final String GO_BACK = "b: go back";
    private static final String MENU_BRACKET = " <> ";
    private static final char NEW_LINE = '\n';
    private final long chatId;
    private Menu menuPointer;

    public MenuWrapper(final Menu startMenu, final long chatId){
        this.menuPointer = startMenu;
        this.chatId = chatId;
    }


    public String dialog(final String inString){
        return process(this, inString);
    }

    public static String printMenu(Menu menu) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(MENU_BRACKET)
                .append(menu.getName())
                .append(MENU_BRACKET)
                .append(NEW_LINE);

        for (Menu m : menu) {
            sb.append(String.format("%d: %s%c", m.getId(), m.getName(), NEW_LINE));
        }
        if (menu.haveRoot()) {
            sb.append(GO_BACK).append(NEW_LINE);
        }
        return sb.toString();
    }

    public static String printCard(final Card card) {
        StringBuilder sb = new StringBuilder();

        for (String s : card) {
            sb.append(s).append(NEW_LINE);
        }

        return sb.toString();
    }

    public Menu getMenuPointer(){
        return menuPointer;
    }

    public Menu goRootMenu(){
        while (menuPointer.haveRoot()){
            setCurrentMenu(menuPointer.getRootMenu());
        }
        return menuPointer;
    }

    public void setCurrentMenu(Menu menu){
        this.menuPointer = menu;
    }

    public static String process(MenuWrapper wrapper, final String inString) {
        if (isNumber(inString))
            return processNumber(Integer.parseInt(inString), wrapper);

        return processCommand(inString, wrapper);
    }

    public static String processNumber(final int number, MenuWrapper wrapper){

        StringBuilder sb = new StringBuilder();
        Menu selectedMenu = wrapper.getMenuPointer().getById(number);

        if (selectedMenu == null) {
            sb
                    .append(WRONG_ITEM)
                    .append(NEW_LINE)
                    .append(NEW_LINE)
                    .append(printMenu(wrapper.getMenuPointer()));
            return sb.toString();
        }

        if (selectedMenu.haveCard()) {
            sb
                    .append(printCard(selectedMenu.getCard()))
                    .append(NEW_LINE)
                    .append(NEW_LINE)
                    .append(printMenu(wrapper.getMenuPointer()));
            return sb.toString();
        }

        sb.append(printMenu(selectedMenu));
        wrapper.setCurrentMenu(selectedMenu);
        return sb.toString();
    }

    public static String processCommand(final String inString, MenuWrapper wrapper){
        switch (inString) {
            case "b":
            case "B":
                if (wrapper.getMenuPointer().haveRoot()) {
                    wrapper.setCurrentMenu(wrapper.getMenuPointer().getRootMenu());
                }
                return printMenu(wrapper.getMenuPointer());
            default:
                return UNKNOWN_COMMAND + ASK_STRING;
        }
    }

    public static boolean isNumber(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException | InputMismatchException e) {
            return false;
        }
    }


}
