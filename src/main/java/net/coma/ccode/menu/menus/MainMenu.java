package net.coma.ccode.menu.menus;

import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.item.IItemBuilder;
import net.coma.ccode.menu.Menu;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public class MainMenu extends Menu implements Listener {
    public MainMenu(@NotNull MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() { return ConfigKeys.MAIN_MENU_TITLE.getString(); }

    @Override
    public int getSlots() { return ConfigKeys.MAIN_MENU_SIZE.getInt(); }

    @Override
    public boolean enableFillerGlass() {
        return ConfigKeys.MAIN_MENU_FILLER_GLASS.getBoolean();
    }

    @Override
    public int getMenuTick() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void handleMenu(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getInventory().equals(inventory)) return;

        event.setCancelled(true);

        if (event.getSlot() == ConfigKeys.MAIN_AVAILABLE_MENU_SLOT.getInt()) {
            inventory.close();
            new AvailableMenu(MenuUtils.getMenuUtils(player)).open();
        }

        if (event.getSlot() == ConfigKeys.MAIN_ALL_MENU_SLOT.getInt()) {
            if (!player.hasPermission("ccode.all-menu")) {
                inventory.close();
                return;
            }

            inventory.close();
            new CombinedMenu(MenuUtils.getMenuUtils(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(ConfigKeys.MAIN_AVAILABLE_MENU_SLOT.getInt(), IItemBuilder.createItemFromSection("main-menu.available-menu-item"));
        inventory.setItem(ConfigKeys.MAIN_ALL_MENU_SLOT.getInt(), IItemBuilder.createItemFromSection("main-menu.all-menu-item"));
        setFillerGlass();
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) close();
    }
}
