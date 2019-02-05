package ru.usefulcity.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.usefulcity.Model.Menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

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
    private Menu selectedItem;
    private EditMessageText editMessage = new EditMessageText().setText("");


    public Dialog(Menu rootMenu) {
        this.currentMenu = rootMenu;
        this.rootMenu = rootMenu;
    }


    public boolean processItem(String itemId) {
        if (isNumber(itemId)) {
            int i = Integer.parseInt(itemId);
            selectedItem = currentMenu.getSubmenu(i);
            log.info("Input : {}, curr: {} {}", itemId, currentMenu.getName(), currentMenu.getId());

            if (selectedItem != null) {
                log.info(" selected: {} {}", selectedItem.getName(), selectedItem.getId());
                currentMenu = selectedItem; //if not card than set submenu as current
            }
        } else {
            switch (itemId) {
                case GO_BACK_ID:
                    if (currentMenu.haveParent()) {
                        currentMenu = currentMenu.getParent();
                    }
                case MAIN_MENU_ID:
                    currentMenu = rootMenu;
                default:
                    return false;
            }
        }
        return true;
    }

    public EditMessageText getEditMessage() {
        if (currentMenu.haveCard()) {
            editMessage.setText(currentMenu.getCard().getText());
        } else {
            editMessage.setText(currentMenu.getName());
        }

        return editMessage;
    }

    public InlineKeyboardMarkup getMenuItems() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Menu m : currentMenu) {
            rows.add(addColumn(m.getName(), m.getId()));
        }

        if (currentMenu.haveCard()) {
            rows.add(addColumn(MAIN_MENU, MAIN_MENU_ID));
        }

        if (currentMenu.haveParent()) {
            rows.add(addColumn(GO_BACK, GO_BACK_ID));
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
