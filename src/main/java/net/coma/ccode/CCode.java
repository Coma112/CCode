package net.coma.ccode;

import lombok.Getter;
import net.coma.ccode.config.Config;
import net.coma.ccode.database.AbstractDatabase;
import net.coma.ccode.database.MySQL;
import net.coma.ccode.enums.LanguageType;
import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.language.Language;
import net.coma.ccode.utils.CommandRegister;
import net.coma.ccode.utils.ListenerRegister;
import net.coma.ccode.utils.StartingUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

import static net.coma.ccode.utils.StartingUtils.registerListenersAndCommands;

public final class CCode extends JavaPlugin {
    @Getter
    private static CCode instance;
    @Getter
    private static AbstractDatabase databaseManager;
    private static Config config;
    private static Language language;

    @Override
    public void onLoad() {
        instance = this;

        StartingUtils.checkVersion();
    }

    @Override
    public void onEnable() {
        StartingUtils.checkVM();
        saveDefaultConfig();

        initializeComponents();
        registerListenersAndCommands();
        initializeDatabaseManager();
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) databaseManager.disconnect();
    }


    public Config getConfiguration() {
        return config;
    }

    public Language getLanguage() {
        return language;
    }

    private void initializeComponents() {
        config = new Config();

        saveResource("locales/messages_en.yml", false);
        saveResource("locales/messages_hu.yml", false);
        saveResource("locales/messages_de.yml", false);

        language = new Language("messages_" + LanguageType.valueOf(ConfigKeys.LANGUAGE.getString()));
    }

    private void initializeDatabaseManager() {
        try {
            databaseManager = new MySQL(Objects.requireNonNull(getConfiguration().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
