package net.coma.ccode.utils;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


@Getter
public class MenuUtils {
    private final Player owner;

    public MenuUtils(@NotNull Player player) {
        this.owner = player;
    }
}

