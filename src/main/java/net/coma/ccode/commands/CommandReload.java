package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.language.MessageKeys;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "codereload", requiresPlayer = false, permission = "ccode.reload")
public class CommandReload extends PluginCommand {

    public CommandReload() {
        super("codereload");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        CCode.getInstance().getLanguage().reload();
        CCode.getInstance().getConfigFile().reload();
        sender.sendMessage(MessageKeys.RELOAD);
        return true;

    }
}

