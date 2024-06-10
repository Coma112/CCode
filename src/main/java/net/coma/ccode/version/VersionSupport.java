package net.coma.ccode.version;

import lombok.Getter;
import net.coma.ccode.utils.CodeLogger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@Getter
public class VersionSupport {
    private final ServerVersionSupport versionSupport;

    public VersionSupport(@NotNull Plugin plugin, @NotNull MinecraftVersion version) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (version == MinecraftVersion.UNKNOWN) throw new IllegalArgumentException("VERSION NOT FOUND!!! ");


        Class<?> clazz = Class.forName("net.coma112.ccode.version.nms." + version.name() + ".Version");
        versionSupport = (ServerVersionSupport) clazz.getConstructor(Plugin.class).newInstance(plugin);

        if (!versionSupport.isSupported()) {
            CodeLogger.warn("---   VERSION IS SUPPORTED BUT,   ---");
            CodeLogger.warn("The version you are using is badly");
            CodeLogger.warn("implemented. Many features won't work.");
            CodeLogger.warn("Please consider updating your server ");
            CodeLogger.warn("version to a newer version. (like 1.19_R2)");
            CodeLogger.warn("---   PLEASE READ THIS MESSAGE!   ---");
        }

        CodeLogger.info("Version support for {} loaded!", version);
    }
}
