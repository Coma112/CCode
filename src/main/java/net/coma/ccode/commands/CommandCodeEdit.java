package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "codeedit", permission = "ccode.edit", requiresPlayer = false)
public class CommandCodeEdit extends PluginCommand {

    public CommandCodeEdit() {
        super("codeedit");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        StringBuilder commandBuilder = new StringBuilder();
        int newUses;

        if (args.length <= 2) {
            sender.sendMessage(MessageKeys.EDIT_RIGHT_USAGE);
            return true;
        }

        String name = args[0];

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS);
            return true;
        }

        switch (args[1]) {
            case "name" -> {
                String newName = args[2];

                CCode.getDatabaseManager().changeName(name, newName);
                sender.sendMessage(MessageKeys.EDIT_NAME);
                return true;
            }

            case "uses" -> {
                try {
                    newUses = Integer.parseInt(args[2]);
                } catch (NumberFormatException exception) {
                    sender.sendMessage(MessageKeys.NEED_NUMBER);
                    return true;
                }

                if (newUses < 0) {
                    sender.sendMessage(MessageKeys.CANT_BE_NEGATIVE);
                    return true;
                }

                CCode.getDatabaseManager().changeUses(name, newUses);
                sender.sendMessage(MessageKeys.EDIT_USES);
                return true;
            }

            case "command" -> {
                for (int i = 2; i < args.length; i++) {
                    commandBuilder
                            .append(args[i])
                            .append(" ");
                }

                String command = commandBuilder
                        .toString()
                        .trim();

                CCode.getDatabaseManager().changeCommand(name, command);
                sender.sendMessage(MessageKeys.EDIT_CMD);
                return true;
            }
        }

        return true;
    }
}
