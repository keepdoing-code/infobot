package ru.usefulcity.Controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.usefulcity.Model.Menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created on 04/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class Dialog {
    private Menu currentMenu;


    public Dialog(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }


    public void processItem(String itemId) {
        if (isNumber(itemId)) {
            Menu item = currentMenu.getById(Integer.parseInt(itemId));

            if(item.haveCard()){

            }



        } else {

        }
    }


    public InlineKeyboardMarkup getMenuItems() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Menu m : currentMenu) {
            List<InlineKeyboardButton> columns = new ArrayList<>();
            columns.add(new InlineKeyboardButton().setText(m.getName()).setCallbackData(m.getId()));
            rows.add(columns);
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }


    public boolean isNumber(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException | InputMismatchException e) {
            return false;
        }
    }
}
