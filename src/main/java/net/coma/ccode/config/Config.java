package net.coma.ccode.config;

import net.coma.ccode.CCode;
import net.coma.ccode.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Config extends ConfigUtils {
    public Config() {
        super(CCode.getInstance().getDataFolder().getPath(), "config");
        save();
    }
}
