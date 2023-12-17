package net.coma.ccode.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.coma.ccode.CCode;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MySQL extends DatabaseManager {
    private final Connection connection;

    public MySQL(@NotNull ConfigurationSection section) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();

        String host = section.getString("host");
        String database = section.getString("database");
        String user = section.getString("username");
        String pass = section.getString("password");
        int port = section.getInt("port");
        boolean ssl = section.getBoolean("ssl");
        boolean certificateVerification = section.getBoolean("certificateverification");
        int poolSize = section.getInt("poolsize");
        int maxLifetime = section.getInt("lifetime");

        hikariConfig.setPoolName("CodePool");
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setMaxLifetime(maxLifetime * 1000L);
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);
        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(ssl));
        if (!certificateVerification)
            hikariConfig.addDataSourceProperty("verifyServerCertificate", String.valueOf(false));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("jdbcCompliantTruncation", "false");
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "275");
        hikariConfig.addDataSourceProperty("useUnicode", "true");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        connection = dataSource.getConnection();
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS code (CODE VARCHAR(255) NOT NULL, CMD VARCHAR(255) NOT NULL, USES INT, PLAYERS VARCHAR(255) NOT NULL, PRIMARY KEY (CODE))";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void createCode(@NotNull String name, @NotNull String cmd, int uses) {
        String query = "INSERT IGNORE INTO code (CODE, CMD, USES) VALUES (?, ?, ?)";

        try {
            if (!exists(name)) {
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, cmd);
                    preparedStatement.setInt(3, uses);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean exists(@NotNull String name) {
        String query = "SELECT * FROM code WHERE CODE = ?";

        try {
            if (!getConnection().isValid(2))
                reconnect(Objects.requireNonNull(CCode.getInstance().getConfigFile().getSection("database")));

            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setString(1, name);

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void redeemCode(@NotNull String name, @NotNull Player player) {
        String selectQuery = "SELECT USES, CMD, PLAYERS FROM code WHERE CODE = ?";
        String updateQuery = "UPDATE code SET USES = USES - 1, PLAYERS = CONCAT(PLAYERS, ?, ', ') WHERE CODE = ?";
        String deleteQuery = "DELETE FROM code WHERE CODE = ?";

        try {
            int currentUses = 0;
            String command = "";
            String currentPlayers = "";

            try (PreparedStatement selectStatement = getConnection().prepareStatement(selectQuery)) {
                selectStatement.setString(1, name);

                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    currentUses = resultSet.getInt("USES");
                    command = resultSet.getString("CMD");
                    currentPlayers = resultSet.getString("PLAYERS");
                }
            }

            if (currentUses <= 0) {
                try (PreparedStatement deleteStatement = getConnection().prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, name);
                    deleteStatement.executeUpdate();
                }
            } else {
                try (PreparedStatement updateStatement = getConnection().prepareStatement(updateQuery)) {
                    updateStatement.setString(1, player.getName());
                    updateStatement.setString(2, name);
                    updateStatement.executeUpdate();
                }

                if (!command.isEmpty()) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean isRedeemed(@NotNull String code, @NotNull Player player) {
        String selectQuery = "SELECT PLAYERS FROM code WHERE CODE = ?";

        try {
            try (PreparedStatement selectStatement = getConnection().prepareStatement(selectQuery)) {
                selectStatement.setString(1, code);

                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    String playersList = resultSet.getString("PLAYERS");
                    if (playersList != null && playersList.contains(player.getName())) return true;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return false;
    }

    @Override
    public boolean isUsesZero(@NotNull String code) {
        String query = "SELECT USES FROM code WHERE CODE = ?";

        try {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setString(1, code);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int uses = resultSet.getInt("USES");
                    return uses == 0;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return false;
    }

    @Override
    public void deleteCode(@NotNull String code) {
        String deleteQuery = "DELETE FROM code WHERE CODE = ?";

        try {
            try (PreparedStatement deleteStatement = getConnection().prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, code);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void changeName(@NotNull String oldName, @NotNull String newName) {
        String updateQuery = "UPDATE code SET CODE = ? WHERE CODE = ?";

        try {
            try (PreparedStatement updateStatement = getConnection().prepareStatement(updateQuery)) {
                updateStatement.setString(1, newName);
                updateStatement.setString(2, oldName);
                updateStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void changeCommand(@NotNull String name, @NotNull String newCommand) {
        String updateQuery = "UPDATE code SET CMD = ? WHERE CODE = ?";

        try {
            try (PreparedStatement updateStatement = getConnection().prepareStatement(updateQuery)) {
                updateStatement.setString(1, newCommand);
                updateStatement.setString(2, name);
                updateStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void changeUses(@NotNull String name, int newUses) {
        String updateQuery = "UPDATE code SET USES = ? WHERE CODE = ?";

        try {
            try (PreparedStatement updateStatement = getConnection().prepareStatement(updateQuery)) {
                updateStatement.setInt(1, newUses);
                updateStatement.setString(2, name);
                updateStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


    @Override
    public void reconnect(@NotNull ConfigurationSection section) {
        try {
            if (getConnection() != null && !getConnection().isClosed()) getConnection().close();
            new MySQL(Objects.requireNonNull(CCode.getInstance().getConfigFile().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to reconnect to the database", exception);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
