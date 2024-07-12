package net.coma.ccode.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class CodeEditUseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String name;
    private final int newUses;

    public CodeEditUseEvent(@NotNull final String name, final int newUses) {
        this.name = name;
        this.newUses = newUses;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
