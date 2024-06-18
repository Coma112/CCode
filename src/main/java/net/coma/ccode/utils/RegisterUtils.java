package net.coma.ccode.utils;

import net.coma.ccode.CCode;
import net.coma.ccode.commands.CommandCode;
import net.coma.ccode.listeners.CodeCreateListener;
import net.coma.ccode.listeners.CodeDeleteListener;
import net.coma.ccode.menu.MenuListener;
import net.coma.ccode.menu.menus.CodeMenu;
import org.bukkit.event.Listener;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class RegisterUtils {
    public static void registerEvents() {
        Set<Class<? extends Listener>> listenerClasses = getListenerClasses();

        for (Class<? extends Listener> clazz : listenerClasses) {
            try {
                CCode.getInstance().getServer().getPluginManager().registerEvents(clazz.newInstance(), CCode.getInstance());
            } catch (InstantiationException | IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public static void registerCommands() {
        BukkitCommandHandler handler = BukkitCommandHandler.create(CCode.getInstance());
        handler.register(new CommandCode());
    }

    private static Set<Class<? extends Listener>> getListenerClasses() {
        Set<Class<? extends Listener>> listenerClasses = new HashSet<>();
        listenerClasses.add(MenuListener.class);
        listenerClasses.add(CodeCreateListener.class);
        listenerClasses.add(CodeDeleteListener.class);
        return listenerClasses;
    }
}
