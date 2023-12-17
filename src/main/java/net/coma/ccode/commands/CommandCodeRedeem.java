package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "redeem", requiresPlayer = false)
public class CommandCodeRedeem extends PluginCommand {

    public CommandCodeRedeem() {
        super("redeem");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(MessageKeys.REDEEM_RIGHT_USAGE);
            return true;
        }

        String name = args[0];

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS);
            return true;
        }

        if (CCode.getDatabaseManager().isRedeemed(name, (Player) sender)) {
            sender.sendMessage(MessageKeys.ALREADY_REDEEMED);
            return true;
        }

        if (CCode.getDatabaseManager().isUsesZero(name)) {
            sender.sendMessage(MessageKeys.USES_ZERO);
            return true;
        }

        CCode.getDatabaseManager().redeemCode(name, (Player) sender);
        sender.sendMessage(MessageKeys.REDEEMED);
        return true;
    }
}
