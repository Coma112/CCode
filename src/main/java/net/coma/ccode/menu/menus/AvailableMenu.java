package net.coma.ccode.menu.menus;

import net.coma.ccode.CCode;
import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.enums.keys.MessageKeys;
import net.coma.ccode.item.IItemBuilder;
import net.coma.ccode.managers.Code;
import net.coma.ccode.menu.PaginatedMenu;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("deprecation")
public class AvailableMenu extends PaginatedMenu implements Listener {
    public AvailableMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return ConfigKeys.AVAILABLE_MENU_TITLE.getString();
    }

    @Override
    public int getSlots() {
        return ConfigKeys.AVAILABLE_MENU_SIZE.getInt();
    }

    @Override
    public void addMenuBorder() {
        inventory.setItem(ConfigKeys.AVAILABLE_BACK_SLOT.getInt(), IItemBuilder.createItemFromSection("available-menu.back-item"));
        inventory.setItem(ConfigKeys.AVAILABLE_FORWARD_SLOT.getInt(), IItemBuilder.createItemFromSection("available-menu.forward-item"));
    }

    @Override
    public int getMaxItemsPerPage() {
        return ConfigKeys.AVAILABLE_MENU_SIZE.getInt() - 2;
    }

    @Override
    public int getMenuTick() {
        return ConfigKeys.AVAILABLE_MENU_TICK.getInt();
    }

    @Override
    public boolean enableFillerGlass() {
        return ConfigKeys.AVAILABLE_FILLER_GLASS.getBoolean();
    }


    @Override
    public void setMenuItems() {
        List<Code> codes = CCode.getDatabaseManager().getCodes(menuUtils.getOwner());
        inventory.clear();
        addMenuBorder();

        int startIndex = page * getMaxItemsPerPage();
        int endIndex = Math.min(startIndex + getMaxItemsPerPage(), codes.size());

        for (int i = startIndex; i < endIndex; i++) inventory.addItem(createCodeItem(codes.get(i)));
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getInventory().equals(inventory)) return;

        event.setCancelled(true);

        List<Code> codes = CCode.getDatabaseManager().getCodes(player);

        if (event.getSlot() == ConfigKeys.AVAILABLE_FORWARD_SLOT.getInt()) {
            int nextPageIndex = page + 1;
            int totalPages = (int) Math.ceil((double) codes.size() / getMaxItemsPerPage());

            if (nextPageIndex >= totalPages) {
                player.sendMessage(MessageKeys.LAST_PAGE.getMessage());
                return;
            } else {
                page++;
                super.open();
            }
        }

        if (event.getSlot() == ConfigKeys.AVAILABLE_BACK_SLOT.getInt()) {
            if (page == 0) {
                player.sendMessage(MessageKeys.FIRST_PAGE.getMessage());
                return;
            } else {
                page--;
                super.open();
            }
        }

        CCode.getDatabaseManager().redeemCode(codes.get(event.getSlot()).codeName(), player);
        inventory.close();
        player.sendMessage(MessageKeys.REDEEMED.getMessage());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) close();
    }

    private static ItemStack createCodeItem(@NotNull Code code) {
        ItemStack itemStack = IItemBuilder.createItemFromSection("code-item");
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            String displayName = meta.getDisplayName()
                    .replace("{name}", code.codeName());
            meta.setDisplayName(displayName);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}