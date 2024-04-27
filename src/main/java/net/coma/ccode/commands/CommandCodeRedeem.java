package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "redeem", requiresPlayer = true)
public class CommandCodeRedeem extends PluginCommand {

    public CommandCodeRedeem() {
        super("redeem");
    }

    @Override
    public boolean run(@NotNull Player player, @NotNull String[] args) {

        if (args.length == 0) {
            player.sendMessage(MessageKeys.REDEEM_RIGHT_USAGE);
            return true;
        }

        String name = args[0];

        if (!CCode.getDatabaseManager().exists(name)) {
            player.sendMessage(MessageKeys.NOT_EXISTS);
            return true;
        }

        if (CCode.getDatabaseManager().isUsesZero(name)) {
            player.sendMessage(MessageKeys.USES_ZERO);
            return true;
        }

        if (!CCode.getDatabaseManager().isOwned(name, player)) {
            player.sendMessage(MessageKeys.NOT_AN_OWNER);
            return true;
        }

        CCode.getDatabaseManager().redeemCode(name, player);
        player.sendMessage(MessageKeys.REDEEMED);
        return true;
    }
}
