package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "codedelete", permission = "ccode.delete", requiresPlayer = false)
public class CommandCodeDelete extends PluginCommand {

    public CommandCodeDelete() {
        super("codedelete");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MessageKeys.DELETE_RIGHT_USAGE);
            return true;
        }

        String name = args[0];

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS);
            return true;
        }

        CCode.getDatabaseManager().deleteCode(name);
        sender.sendMessage(MessageKeys.DELETED);
        return true;
    }
}

