package ru.usefulcity.Controller;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.usefulcity.Model.Menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static ru.usefulcity.Controller.Constants.*;

/**
 * Created on 04/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class Dialog {
    private Menu currentMenu;
    private EditMessageText editMessage = new EditMessageText().setText("");


    public Dialog(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }


    public boolean processItem(String itemId) {
        if (isNumber(itemId)) {
            Menu item = currentMenu.getById(Integer.parseInt(itemId));

            if (item == null) return false; //if something gone wrong return false to show Error message

            if (item.haveCard()) {
                StringBuilder sb = new StringBuilder();
                for (String s : item.getCard()) {
                    sb.append(s).append(NEW_LINE);
                }
                editMessage.setText(sb.toString());
                return true;
            }

            currentMenu = item; //if not card than set submenu as current

        } else {
            //if command do some work
            switch (itemId) {
                case "b": //go back to root menu
                    if (currentMenu.haveRoot()) {
                        currentMenu = currentMenu.getRootMenu();
                    }
                default:
                    return false;
            }
        }
        return true;
    }


    public EditMessageText getEditMessage() {
        return editMessage;
    }


    public InlineKeyboardMarkup getMenuItems() {
        editMessage.setText(currentMenu.getName());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Menu m : currentMenu) {
            List<InlineKeyboardButton> columns = new ArrayList<>();
            columns.add(new InlineKeyboardButton().setText(m.getName()).setCallbackData(m.getId()));
            rows.add(columns);
        }

        if(currentMenu.haveRoot()){
            List<InlineKeyboardButton> columns = new ArrayList<>();
            columns.add(new InlineKeyboardButton().setText(GO_BACK).setCallbackData(GO_BACK_ID));
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
