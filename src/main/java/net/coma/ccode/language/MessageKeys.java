package net.coma.ccode.language;

import net.coma.ccode.CCode;
import net.coma.ccode.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public class MessageKeys {
    public static String PREFIX = getString("prefix");
    public static String NO_PERMISSION = PREFIX + getString("messages.no-permission");
    public static String RELOAD = PREFIX + getString("messages.reload");
    public static String PLAYER_REQUIRED = PREFIX + getString("messages.player-required");
    public static String CREATE_RIGHT_USAGE = PREFIX + getString("messages.create-right-usage");
    public static String ALREADY_EXISTS = PREFIX + getString("messages.already-created");
    public static String CANT_BE_NEGATIVE = PREFIX + getString("messages.cant-be-negative");
    public static String CREATED = PREFIX + getString("messages.created");
    public static String REDEEMED = PREFIX + getString("messages.redeemed");
    public static String REDEEM_RIGHT_USAGE = PREFIX + getString("messages.usage-redeem");
    public static String USES_ZERO = PREFIX + getString("messages.uses-zero");
    public static String NEED_NUMBER = PREFIX + getString("messages.need-number");
    public static String ALREADY_REDEEMED = PREFIX + getString("messages.already-redeemed");
    public static String NOT_EXISTS = PREFIX + getString("messages.not-exists");
    public static String DELETED = PREFIX + getString("messages.deleted");
    public static String DELETE_RIGHT_USAGE = PREFIX + getString("messages.usage-delete");
    public static String EDIT_CMD = PREFIX + getString("messages.edit-cmd");
    public static String EDIT_USES = PREFIX + getString("messages.edit-uses");
    public static String EDIT_NAME = PREFIX + getString("messages.edit-name");
    public static String EDIT_RIGHT_USAGE = PREFIX + getString("messages.edit-right-usage");

    private static String getString(@NotNull String path) {
        return MessageProcessor.process(CCode.getInstance().getLanguage().getString(path));
    }
}
