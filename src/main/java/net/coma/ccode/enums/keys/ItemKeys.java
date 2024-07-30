package net.coma.ccode.enums.keys;

import net.coma.ccode.item.IItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum ItemKeys {
    CLAIMED_BACK_ITEM("claimed-menu.back-item"),
    ALL_BACK_ITEM("all-menu.back-item"),
    ALL_FORWARD_ITEM("all-menu.forward-item"),
    AVAILABLE_FORWARD_ITEM("available-menu.forward-item"),
    AVAILABLE_BACK_ITEM("available-menu.back-item"),
    MAIN_ALL_MENU_ITEM("main-menu.all-menu-item"),
    MAIN_AVAILABLE_MENU_ITEM("main-menu.available-menu-item"),
    CODE_ITEM("code-item"),
    FILLER_GLASS_ITEM("filler-glass-item"),
    CLAIMED_FORWARD_ITEM("claimed-menu.forward-item");

    private final String path;

    ItemKeys(@NotNull final String path) {
        this.path = path;
    }

    public ItemStack getItem() {
        return IItemBuilder.createItemFromString(path);
    };
}
