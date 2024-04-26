package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.menu.menus.CodeMenu;
import net.coma.ccode.subcommand.CommandInfo;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "codes", requiresPlayer = true)
public class CommandCodesMenu extends PluginCommand {
    public CommandCodesMenu() {
        super("codes");
    }

    @Override
    public boolean run(@NotNull Player player, @NotNull String[] args) {
        new CodeMenu(CCode.getInstance().getMenuUtils(player)).open();
        return true;
    }
}
