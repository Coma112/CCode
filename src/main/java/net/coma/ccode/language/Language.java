package net.coma.ccode.language;

import net.coma.ccode.CCode;
import net.coma.ccode.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Language extends ConfigUtils {
    public Language(@NotNull String name) {
        super(CCode.getInstance().getDataFolder().getPath() + File.separator + "locales", name);
        save();
    }
}
