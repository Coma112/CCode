package net.coma.ccode.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class CodeCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String name;
    private final String command;
    private final int uses;

    public CodeCreateEvent(@NotNull final Player player, @NotNull final String name, @NotNull final String command, final int uses) {
        this.player = player;
        this.name = name;
        this.command = command;
        this.uses = uses;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
