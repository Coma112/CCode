package net.coma.ccode.utils;

import net.coma.ccode.CCode;
import net.coma.ccode.version.MinecraftVersion;
import net.coma.ccode.version.ServerVersionSupport;
import net.coma.ccode.version.VersionSupport;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartingUtils {
    private static boolean isSupported;

    public static void registerListenersAndCommands() {
        ListenerRegister.registerEvents();
        CommandRegister.registerCommands();
    }


    public static void checkVM() {
        if (getVMVersion() < 11) {
            Bukkit.getPluginManager().disablePlugin(CCode.getInstance());
            return;
        }

        if (!isSupported) {
            CodeLogger.error("This version of CBounty is not supported on this server version.");
            CodeLogger.error("Please consider updating your server version to a newer version.");
            CCode.getInstance().getServer().getPluginManager().disablePlugin(CCode.getInstance());
        }
    }

    public static void checkVersion() {
        VersionSupport support;

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (Exception ignored) {
            isSupported = false;
            return;
        }

        try {
            int midVersion = Integer.parseInt((Bukkit.getServer().getClass().getName().split("\\.")[3]).split("_")[1]);

            if (midVersion <= 12) {
                isSupported = false;
                return;
            }

            CodeLogger.info("Found everything moving onto VersionSupport...");
            support = new VersionSupport(CCode.getInstance(), MinecraftVersion.getCurrentVersion());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            CodeLogger.error(exception.getMessage());
            isSupported = false;
            return;
        }

        ServerVersionSupport nms = support.getVersionSupport();
        isSupported = true;
    }

    static int getVMVersion() {
        String javaVersion = System.getProperty("java.version");
        Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(javaVersion);
        if (!matcher.find()) return -1;
        String version = matcher.group(1);

        try {
            return Integer.parseInt(version);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
