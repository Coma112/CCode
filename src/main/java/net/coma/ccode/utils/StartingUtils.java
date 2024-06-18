package net.coma.ccode.utils;

import net.coma.ccode.CCode;
import net.coma.ccode.update.UpdateChecker;
import net.coma.ccode.version.MinecraftVersion;
import net.coma.ccode.version.ServerVersionSupport;
import net.coma.ccode.version.VersionSupport;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class StartingUtils {
    private static boolean isSupported;

    public static void registerListenersAndCommands() {
        RegisterUtils.registerEvents();
        RegisterUtils.registerCommands();
    }


    public static void checkVM() {
        if (getVMVersion() < 11) {
            Bukkit.getPluginManager().disablePlugin(CCode.getInstance());
            return;
        }

        if (!isSupported) {
            CodeLogger.error("This version of CCode is not supported on this server version.");
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
            String[] classParts = Bukkit.getServer().getClass().getName().split("\\.");

            if (classParts.length < 4) {
                CodeLogger.error("Unexpected server class name format: " + Bukkit.getServer().getClass().getName());
                isSupported = false;
                return;
            }

            String[] versionParts = classParts[3].split("_");

            if (versionParts.length < 2) {
                CodeLogger.error("Unexpected version format in class name: " + classParts[3]);
                isSupported = false;
                return;
            }

            int midVersion = Integer.parseInt(versionParts[1]);

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


    public static void checkUpdates() {
        new UpdateChecker(116606).getVersion(version -> {
            CodeLogger.info(CCode.getInstance().getDescription().getVersion().equals(version) ? "Everything is up to date" : "You are using an outdated version! Please download the new version so that your server is always fresh! The newest version: " + version);
        });
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
