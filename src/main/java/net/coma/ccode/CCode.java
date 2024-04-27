package net.coma.ccode;

import lombok.Getter;
import net.coma.ccode.config.Config;
import net.coma.ccode.database.DatabaseManager;
import net.coma.ccode.database.MySQL;
import net.coma.ccode.language.Language;
import net.coma.ccode.utils.CommandRegister;
import net.coma.ccode.utils.ListenerRegister;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public final class CCode extends JavaPlugin {
    @Getter
    private static CCode instance;
    @Getter
    private static DatabaseManager databaseManager;
    private static Config config;
    private static Language language;
    private static final HashMap<Player, MenuUtils> menuMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        initializeComponents();
        registerListenersAndCommands();
        initializeDatabaseManager();

        MySQL mysql = (MySQL) databaseManager;
        mysql.createTable();
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) databaseManager.disconnect();
    }

    private void initializeComponents() {
        language = new Language();
        config = new Config();
    }

    private void registerListenersAndCommands() {
        ListenerRegister.registerEvents();
        CommandRegister.registerCommands();
    }

    private void initializeDatabaseManager() {
        try {
            databaseManager = new MySQL(Objects.requireNonNull(getConfigFile().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public MenuUtils getMenuUtils(@NotNull Player player) {
        MenuUtils menuUtils;

        if (!(menuMap.containsKey(player))) {

            menuUtils = new MenuUtils(player);
            menuMap.put(player, menuUtils);

            return menuUtils;
        }

        return menuMap.get(player);
    }

    public Config getConfigFile() {
        return config;
    }

    public Language getLanguage() {
        return language;
    }

}
