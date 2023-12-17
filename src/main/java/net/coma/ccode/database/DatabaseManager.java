package net.coma.ccode.database;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class DatabaseManager {
    public abstract boolean isConnected();

    public abstract void disconnect();

    public abstract void createCode(@NotNull String name, @NotNull String cmd, int uses);

    public abstract boolean exists(@NotNull String name);

    public abstract void redeemCode(@NotNull String name, @NotNull OfflinePlayer player);

    public abstract boolean isRedeemed(@NotNull String code, @NotNull OfflinePlayer player);

    public abstract boolean isUsesZero(@NotNull String code);

    public abstract void deleteCode(@NotNull String code);

    public abstract void changeName(@NotNull String oldName, @NotNull String newName);

    public abstract void changeCommand(@NotNull String name, @NotNull String newCommand);

    public abstract void changeUses(@NotNull String name, int newUses);

    public abstract void reconnect(@NotNull ConfigurationSection section);
}
