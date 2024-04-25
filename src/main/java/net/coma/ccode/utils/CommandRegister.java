package net.coma.ccode.utils;

import net.coma.ccode.CCode;
import net.coma.ccode.commands.*;
import net.coma.ccode.subcommand.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommandRegister {
    @SuppressWarnings("deprecation")
    public static void registerCommands() {
        for (Class<? extends net.coma.ccode.subcommand.PluginCommand> clazz : getCommandClasses()) {
            try {
                PluginCommand commandInstance = clazz.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(Bukkit.getCommandMap()).register(CCode.getInstance().getDescription().getName(), commandInstance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
                exception.printStackTrace();
            }
        }
    }

    private static Set<Class<? extends PluginCommand>> getCommandClasses() {
        Set<Class<? extends PluginCommand>> commandClasses = new HashSet<>();
        commandClasses.add(CommandCodeCreate.class);
        commandClasses.add(CommandCodeRedeem.class);
        commandClasses.add(CommandCodeDelete.class);
        commandClasses.add(CommandCodeEdit.class);
        commandClasses.add(CommandReload.class);
        commandClasses.add(CommandCodeGive.class);

        return commandClasses;
    }
}
