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

import static net.coma.ccode.version.MinecraftVersion.determineVersion;

@SuppressWarnings("deprecation")
public class StartingUtils {
    private static boolean isSupported;

    public static void registerListenersAndCommands() {
        RegisterUtils.registerEvents();
        RegisterUtils.registerCommands();
    }


    public static void checkVM() {
        int vmVersion = getVMVersion();
        if (vmVersion < 11) {
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
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (Exception ignored) {
            isSupported = false;
            return;
        }

        try {
            String bukkitVersion = Bukkit.getVersion();

            Pattern pattern = Pattern.compile("\\(MC: (\\d+)\\.(\\d+)(?:\\.(\\d+))?\\)");
            Matcher matcher = pattern.matcher(bukkitVersion);

            if (matcher.find()) {
                int majorVersion = Integer.parseInt(matcher.group(1));
                int minorVersion = Integer.parseInt(matcher.group(2));
                int patchVersion = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;

                MinecraftVersion version = determineVersion(majorVersion, minorVersion, patchVersion);
                if (version == MinecraftVersion.UNKNOWN) {
                    isSupported = false;
                    return;
                }

                VersionSupport support = new VersionSupport(CCode.getInstance(), version);
                ServerVersionSupport nms = support.getVersionSupport();
                isSupported = nms != null;

            } else {
                isSupported = false;
            }
        } catch (Exception exception) {
            isSupported = false;
        }
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
