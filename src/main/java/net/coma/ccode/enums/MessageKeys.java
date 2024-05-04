package net.coma.ccode.enums;

import net.coma.ccode.CCode;
import net.coma.ccode.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public enum MessageKeys {
    NO_PERMISSION("messages.no-permission"),
    RELOAD("messages.reload"),
    PLAYER_REQUIRED("messages.player-required"),
    ALREADY_EXISTS("messages.already-created"),
    CANT_BE_NEGATIVE("messages.cant-be-negative"),
    CREATED("messages.created"),
    REDEEMED("messages.redeemed"),
    USES_ZERO("messages.uses-zero"),
    NEED_NUMBER("messages.need-number"),
    NOT_EXISTS("messages.not-exists"),
    DELETED("messages.deleted"),
    EDIT_CMD("messages.edit-cmd"),
    EDIT_USES("messages.edit-uses"),
    EDIT_NAME("messages.edit-name"),
    OFFLINE_PLAYER("messages.offline-player"),
    NOT_AN_OWNER("messages.not-an-owner"),
    ALREADY_AN_OWNER("messages.already-owner"),
    SUCCESSFUL_ADD("messages.successful-add"),
    FIRST_PAGE("messages.first-page"),
    TARGET_GIVE("messages.successful-give-target"),
    PLAYER_GIVE("messages.successful-give-player"),
    LAST_PAGE("messages.last-page");

    private final String path;

    MessageKeys(@NotNull final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return MessageProcessor.process(CCode.getInstance().getLanguage().getString(path));
    }
}
