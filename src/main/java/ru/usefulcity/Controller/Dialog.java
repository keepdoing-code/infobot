package ru.usefulcity.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.usefulcity.Log;
import ru.usefulcity.Model.Card;
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
    Logger log = LoggerFactory.getLogger(Dialog.class);
    private Menu rootMenu;
    private Menu currentMenu;
    private EditMessageText editMessage = new EditMessageText().setText("");


    public Dialog(Menu rootMenu) {
        this.currentMenu = rootMenu;
        this.rootMenu = rootMenu;
    }

    public void updateRootMenu(Menu updatedMenu){
        this.currentMenu = updatedMenu;
        this.rootMenu = updatedMenu;
    }


    public boolean processUpdate(String itemId) {
        log.info("Input: {}",itemId);

        if (isNumber(itemId)) {
            currentMenu = currentMenu.getSubmenu(Integer.parseInt(itemId));

        } else {
            Log.out("cur id: ",currentMenu.getId(), "cur name: ", currentMenu.getName());
            Log.out("root id: ",rootMenu.getId(), "root name: ", rootMenu.getName());
            Log.out("parent id: ",currentMenu.getParent().getId(), "parent name: ", currentMenu.getParent().getId());


            switch (itemId) {
                case GO_BACK_CMD:
                    if (currentMenu.haveParent()) {
                        currentMenu = currentMenu.getParent();
                    }
                    break;
                case MAIN_MENU_CMD:
                    currentMenu = rootMenu;
                    break;
                default:
                    return false;
            }

            Log.out("cur id: ",currentMenu.getId(), "cur name: ", currentMenu.getName());
            Log.out("root id: ",rootMenu.getId(), "root name: ", rootMenu.getName());
            Log.out("parent id: ",currentMenu.getParent().getId(), "parent name: ", currentMenu.getParent().getId());

        }
        return true;
    }

    public EditMessageText getEditMessage() {
        if (currentMenu.haveCard()) {
            StringBuilder sb = new StringBuilder();
            for(Card c: currentMenu.getCards()) {
                sb.append(c.getName()).append(NEW_LINE)
                        .append(c.getText());
            }
            return editMessage.setText(sb.toString());
        }
        return editMessage.setText(currentMenu.getName());
    }


    public InlineKeyboardMarkup getMenuItems() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Menu m : currentMenu) {
            rows.add(addColumn(m.getName(), m.getId()));
        }

        if (currentMenu.haveCard()) {
            rows.add(addColumn(MAIN_MENU, MAIN_MENU_CMD));
        }

        if (currentMenu.haveParent()) {
            rows.add(addColumn(GO_BACK, GO_BACK_CMD));
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }


    private List<InlineKeyboardButton> addColumn(String name, String id) {
        List<InlineKeyboardButton> columns = new ArrayList<>();
        columns.add(new InlineKeyboardButton().setText(name).setCallbackData(id));
        return columns;
    }


    private boolean isNumber(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException | InputMismatchException e) {
            return false;
        }
    }
}
