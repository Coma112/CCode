package net.coma.ccode.database;

import net.coma.ccode.managers.Code;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractDatabase {
    public abstract boolean isConnected();

    public abstract void disconnect();

    public abstract void createCode(@NotNull String name, @NotNull String cmd, int uses);

    public abstract boolean exists(@NotNull String name);

    public abstract void redeemCode(@NotNull String name, @NotNull OfflinePlayer player);

    public abstract void giveCode(@NotNull String code, @NotNull OfflinePlayer player);

    public abstract boolean isOwned(@NotNull String code, @NotNull OfflinePlayer player);

    public abstract boolean isUsesZero(@NotNull String code);

    public abstract void takeCode(@NotNull String code, @NotNull String oldOwner, @NotNull String newOwner);

    public abstract void deleteCode(@NotNull String code);

    public abstract void changeName(@NotNull String oldName, @NotNull String newName);

    public abstract void changeCommand(@NotNull String name, @NotNull String newCommand);

    public abstract void changeUses(@NotNull String name, int newUses);

    public abstract List<Code> getCodes(@NotNull OfflinePlayer player);

    public abstract void reconnect();
}
