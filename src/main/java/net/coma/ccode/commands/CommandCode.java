package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.database.AbstractDatabase;
import net.coma.ccode.enums.keys.MessageKeys;
import net.coma.ccode.events.*;
import net.coma.ccode.managers.Code;
import net.coma.ccode.menu.menus.AvailableMenu;
import net.coma.ccode.menu.menus.MainMenu;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.Objects;

@Command({"code", "ccode", "voucher"})
public class CommandCode {
    @Subcommand("reload")
    @CommandPermission("ccode.reload")
    public void reload(@NotNull CommandSender sender) {
        CCode.getInstance().getLanguage().reload();
        CCode.getInstance().getConfiguration().reload();
        CCode.getDatabaseManager().reconnect();
        sender.sendMessage(MessageKeys.RELOAD.getMessage());
    }

    @Subcommand("menu")
    @CommandPermission("ccode.menu")
    public void menu(@NotNull Player player) {
        new MainMenu(MenuUtils.getMenuUtils(player)).open();
    }

    @Subcommand("create")
    @CommandPermission("ccode.create")
    public void create(@NotNull CommandSender sender, @NotNull String name, int uses, @NotNull String command) {
        AbstractDatabase database = CCode.getDatabaseManager();

        if (database.exists(name)) {
            sender.sendMessage(MessageKeys.ALREADY_EXISTS.getMessage());
            return;
        }

        if (uses < 0) {
            sender.sendMessage(MessageKeys.CANT_BE_NEGATIVE.getMessage());
            return;
        }

        Code code = new Code(name, (command + " ").trim(), uses);
        database.createCode(code.codeName(), code.command(), code.uses());
        sender.sendMessage(MessageKeys.CREATED.getMessage());
        CCode.getInstance().getServer().getPluginManager().callEvent(new CodeCreateEvent((Player) sender, name, command, uses));
    }

    @Subcommand("delete")
    @CommandPermission("ccode.delete")
    public void delete(@NotNull CommandSender sender, @NotNull String name) {
        AbstractDatabase database = CCode.getDatabaseManager();

        if (!database.exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        database.deleteCode(name);
        sender.sendMessage(MessageKeys.DELETED.getMessage());
        CCode.getInstance().getServer().getPluginManager().callEvent(new CodeDeleteEvent((Player) sender, name));
    }

    @Subcommand("edituse")
    @CommandPermission("ccode.edituse")
    public void edituse(@NotNull CommandSender sender, @NotNull String name, int newUse) {
        AbstractDatabase database = CCode.getDatabaseManager();

        if (!database.exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        if (newUse < 0) {
            sender.sendMessage(MessageKeys.CANT_BE_NEGATIVE.getMessage());
            return;
        }

        CCode.getInstance().getServer().getPluginManager().callEvent(new CodeEditUseEvent(name, newUse));
        database.changeUses(name, newUse);
        sender.sendMessage(MessageKeys.EDIT_USES.getMessage());
    }

    @Subcommand("editname")
    @CommandPermission("ccode.editname")
    public void editname(@NotNull CommandSender sender, @NotNull String name, @NotNull String newName) {
        AbstractDatabase database = CCode.getDatabaseManager();

        if (!database.exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        CCode.getInstance().getServer().getPluginManager().callEvent(new CodeEditNameEvent(name, newName));
        database.changeName(name, newName);
        sender.sendMessage(MessageKeys.EDIT_NAME.getMessage());
    }

    @Subcommand("editcommand")
    @CommandPermission("ccode.editcommand")
    public void editcommand(@NotNull CommandSender sender, @NotNull String name, @NotNull String newCommand) {
        AbstractDatabase database = CCode.getDatabaseManager();

        if (!database.exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        CCode.getInstance().getServer().getPluginManager().callEvent(new CodeEditCommandEvent(name, (newCommand + " ").trim()));
        database.changeCommand(name, (newCommand + " ").trim());
        sender.sendMessage(MessageKeys.EDIT_NAME.getMessage());
    }

    @Subcommand("add")
    @CommandPermission("ccode.add")
    public void add(@NotNull CommandSender sender, @NotNull String name, @NotNull String target) {
        AbstractDatabase database = CCode.getDatabaseManager();
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target);

        if (!database.exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        database.giveCode(name, targetPlayer);
        sender.sendMessage(MessageKeys.SUCCESSFUL_ADD.getMessage());
    }

    @Subcommand("redeem")
    @CommandPermission("ccode.redeem")
    public void redeem(@NotNull Player player, @NotNull String name) {
        AbstractDatabase database = CCode.getDatabaseManager();

        if (!database.exists(name)) {
            player.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        if (database.isUsesZero(name)) {
            player.sendMessage(MessageKeys.USES_ZERO.getMessage());
            return;
        }

        if (!database.isOwned(name, player)) {
            player.sendMessage(MessageKeys.NOT_AN_OWNER.getMessage());
            return;
        }

        database.redeemCode(name, player);
        player.sendMessage(MessageKeys.REDEEMED.getMessage());
    }

    @Subcommand("give")
    @CommandPermission("ccode.give")
    public void give(@NotNull Player player, @NotNull String name, @NotNull String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        AbstractDatabase database = CCode.getDatabaseManager();

        if (!database.exists(name)) {
            player.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        if (!database.isOwned(name, player)) {
            player.sendMessage(MessageKeys.NOT_AN_OWNER.getMessage());
            return;
        }

        if (!Objects.requireNonNull(targetPlayer).isOnline()) {
            player.sendMessage(MessageKeys.OFFLINE_PLAYER.getMessage());
            return;
        }

        database.takeCode(name, player.getName(), targetPlayer.getName());
        player.sendMessage(MessageKeys.PLAYER_GIVE.getMessage());
        targetPlayer.sendMessage(MessageKeys.TARGET_GIVE
                .getMessage()
                .replace("{player}", player.getName())
                .replace("{code}", name));
    }
}