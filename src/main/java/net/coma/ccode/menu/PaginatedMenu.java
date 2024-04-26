package net.coma.ccode.menu;

import lombok.Getter;
import net.coma.ccode.storage.ItemStorage;
import net.coma.ccode.utils.MenuUtils;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    @Getter
    protected int maxItemsPerPage = 52;
    protected int index = 0;

    public PaginatedMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    public void addMenuBorder(){
        inventory.setItem(45, ItemStorage.BACK);
        inventory.setItem(53, ItemStorage.FORWARD);
    }
}

