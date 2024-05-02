package net.coma.ccode.utils;

import net.coma.ccode.CCode;
import net.coma.ccode.commands.CommandCode;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class CommandRegister {
    public static void registerCommands() {
        BukkitCommandHandler handler = BukkitCommandHandler.create(CCode.getInstance());
        handler.register(new CommandCode());
    }
}
