package net.coma.ccode.managers;


import org.jetbrains.annotations.NotNull;

public record Code(@NotNull String codeName, @NotNull String command, int uses) {}
