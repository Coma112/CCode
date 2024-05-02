package net.coma.ccode.menu.menus;

import net.coma.ccode.CCode;
import net.coma.ccode.enums.ConfigKeys;
import net.coma.ccode.enums.MessageKeys;
import net.coma.ccode.item.IItemBuilder;
import net.coma.ccode.managers.Code;
import net.coma.ccode.menu.PaginatedMenu;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("deprecation")
public class CodeMenu extends PaginatedMenu {
    public CodeMenu(MenuUtils menuUtils) {
        super(menuUtils);
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

    @Override
    public String getMenuName() {
        return ConfigKeys.MENU_TITLE.getString();
    }

    @Override
    public int getSlots() {
        return ConfigKeys.MENU_SIZE.getInt();
    }


    @Override
    public void setMenuItems() {
        List<Code> codes = CCode.getDatabaseManager().getCodes(menuUtils.getOwner());
        inventory.clear();
        addMenuBorder();

        int startIndex = page * getMaxItemsPerPage();
        int endIndex = Math.min(startIndex + getMaxItemsPerPage(), codes.size());

        for (int i = startIndex; i < endIndex; i++) {
            Code code = codes.get(i);
            ItemStack item = createCodeItem(code);
            inventory.addItem(item);
        }
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getInventory().equals(inventory)) return;

        event.setCancelled(true);

        List<Code> codes = CCode.getDatabaseManager().getCodes(player);

        switch (event.getSlot()) {
            case 45 -> {
                if (page == 0) {
                    player.sendMessage(MessageKeys.FIRST_PAGE.getMessage());
                    return;
                } else {
                    page--;
                    super.open();
                }
            }

            case 53 -> {
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
        }

        CCode.getDatabaseManager().redeemCode(codes.get(event.getSlot()).codeName(), player);
        inventory.close();
        player.sendMessage(MessageKeys.REDEEMED.getMessage());
    }
}
