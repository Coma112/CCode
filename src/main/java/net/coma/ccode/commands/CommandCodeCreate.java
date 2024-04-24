package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.managers.Code;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "codecreate", permission = "ccode.create", requiresPlayer = false)
public class CommandCodeCreate extends PluginCommand {

    public CommandCodeCreate() {
        super("codecreate");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        StringBuilder commandBuilder = new StringBuilder();
        int uses;

        if (args.length <= 2) {
            sender.sendMessage(MessageKeys.CREATE_RIGHT_USAGE);
            return true;
        }

        String name = args[0];

        if (CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.ALREADY_EXISTS);
            return true;
        }

        try {
            uses = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            sender.sendMessage(MessageKeys.NEED_NUMBER);
            return true;
        }

        if (uses < 0) {
            sender.sendMessage(MessageKeys.CANT_BE_NEGATIVE);
            return true;
        }

        for (int i = 2; i < args.length; i++) {
            commandBuilder
                    .append(args[i])
                    .append(" ");
        }

        String command = commandBuilder
                .toString()
                .trim();
        Code code = new Code(name, command, uses);
        CCode.getDatabaseManager().createCode(code.codeName(), code.command(), code.uses());
        sender.sendMessage(MessageKeys.CREATED);
        return true;
    }
}
