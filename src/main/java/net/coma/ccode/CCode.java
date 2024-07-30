package net.coma.ccode;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import lombok.Getter;
import net.coma.ccode.config.Config;
import net.coma.ccode.database.AbstractDatabase;
import net.coma.ccode.database.MySQL;
import net.coma.ccode.database.SQLite;
import net.coma.ccode.enums.DatabaseType;
import net.coma.ccode.enums.LanguageType;
import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.language.Language;
import net.coma.ccode.utils.CodeLogger;
import net.coma.ccode.utils.StartingUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

import static net.coma.ccode.utils.StartingUtils.registerListenersAndCommands;

public final class CCode extends JavaPlugin {
    @Getter private static CCode instance;
    @Getter private static AbstractDatabase databaseManager;
    @Getter private Language language;
    @Getter private TaskScheduler scheduler;
    private Config config;

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
        scheduler = UniversalScheduler.getScheduler(this);
        registerListenersAndCommands();
        initializeDatabaseManager();

        StartingUtils.checkUpdates();
        new Metrics(this, 22311);
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) databaseManager.disconnect();
    }


    public Config getConfiguration() {
        return config;
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
            switch (DatabaseType.valueOf(ConfigKeys.DATABASE.getString())) {
                case MYSQL, mysql -> {
                    databaseManager = new MySQL(Objects.requireNonNull(getConfiguration().getSection("database.mysql")));
                    MySQL mySQL = (MySQL) databaseManager;
                    mySQL.createTable();
                }

                case SQLITE, sqlite -> {
                    databaseManager = new SQLite();
                    SQLite sqlite = (SQLite) databaseManager;
                    sqlite.createTable();
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            CodeLogger.error(exception.getMessage());
        }
    }
}
