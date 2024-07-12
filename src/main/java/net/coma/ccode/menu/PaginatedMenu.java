package net.coma.ccode.menu;

import net.coma.ccode.utils.MenuUtils;

public abstract class PaginatedMenu extends Menu {

    public abstract void addMenuBorder();

    protected int page = 0;
    public abstract int getMaxItemsPerPage();

    public PaginatedMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

}

