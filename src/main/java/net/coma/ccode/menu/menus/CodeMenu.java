package net.coma.ccode.menu.menus;

import net.coma.ccode.CCode;
import net.coma.ccode.config.ConfigKeys;
import net.coma.ccode.item.IItemBuilder;
import net.coma.ccode.item.ItemBuilder;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.managers.Code;
import net.coma.ccode.menu.PaginatedMenu;
import net.coma.ccode.storage.ItemStorage;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CodeMenu extends PaginatedMenu {
    public CodeMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "&bAVAILABLE CODES";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        List<Code> codes = CCode.getDatabaseManager().getCodes(player);

        if (Objects.equals(event.getCurrentItem(), ItemStorage.BACK)) {
            if (page == 0) {
                player.sendMessage(MessageKeys.FIRST_PAGE);
            } else {
                page--;
                super.open();
            }

            return;
        }

        if (Objects.equals(event.getCurrentItem(), ItemStorage.FORWARD)) {
            int nextPageIndex = page + 1;
            int totalPages = (int) Math.ceil((double) codes.size() / getMaxItemsPerPage());

            if (nextPageIndex >= totalPages) {
                player.sendMessage(MessageKeys.LAST_PAGE);
            } else {
                page++;
                super.open();
            }

            return;
        }

        CCode.getDatabaseManager().redeemCode(Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getLocalizedName(), player);
        inventory.close();
        player.sendMessage(MessageKeys.REDEEMED);
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


    private static ItemStack createCodeItem(@NotNull Code code) {
        ItemBuilder itemBuilder = IItemBuilder.create(Material.valueOf(ConfigKeys.CODE_ITEM_MATERIAL))
                .setName(ConfigKeys.CODE_ITEM_NAME
                        .replace("{name}", code.codeName()))
                .setLocalizedName(code.codeName());

        for (String lore : ConfigKeys.CODE_ITEM_LORE) itemBuilder.addLore(lore);

        return itemBuilder.finish();
    }

}
