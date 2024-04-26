package net.coma.ccode.config;

import net.coma.ccode.CCode;
import net.coma.ccode.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Config extends ConfigUtils {
    public Config() {
        super(CCode.getInstance().getDataFolder().getPath(), "config");

        YamlConfiguration yml = getYml();

        yml.addDefault("database.type", "mysql");
        yml.addDefault("database.mysql.host", "localhost");
        yml.addDefault("database.mysql.port", 3306);
        yml.addDefault("database.mysql.database", "coma112");
        yml.addDefault("database.mysql.username", "root");
        yml.addDefault("database.mysql.password", "");
        yml.addDefault("database.mysql.ssl", false);
        yml.addDefault("database.mysql.certificateverification", false);
        yml.addDefault("database.mysql.poolsize", 10);
        yml.addDefault("database.mysql.lifetime", 1800000);

        yml.addDefault("code-item.material", "PAPER");
        yml.addDefault("code-item.name", "&b{name}");
        yml.addDefault("code-item.lore", List.of(
                "",
                "&aClick if you want to redeem the code!"
        ));


        yml.options().copyDefaults(true);
        save();
    }
}
