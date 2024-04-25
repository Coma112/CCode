package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "codegive", requiresPlayer = false, permission = "ccode.give")
public class CommandCodeGive extends PluginCommand {
    public CommandCodeGive() {
        super("codegive");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length != 2) {
            sender.sendMessage(MessageKeys.GIVE_RIGHT_USAGE);
            return true;
        }

        String name = args[0];
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS);
            return true;
        }

        if (CCode.getDatabaseManager().isOwned(name, targetPlayer)) {
            sender.sendMessage(MessageKeys.ALREADY_AN_OWNER);
            return true;
        }

        CCode.getDatabaseManager().giveCode(name, targetPlayer);
        sender.sendMessage(MessageKeys.SUCCESSFUL_GIVE);
        return true;
    }
}
