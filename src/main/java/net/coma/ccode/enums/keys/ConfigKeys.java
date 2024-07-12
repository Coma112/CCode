package net.coma.ccode.enums.keys;

import net.coma.ccode.CCode;
import net.coma.ccode.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public enum ConfigKeys {
    DATABASE("database.type"),
    LANGUAGE("language"),
    USES_MUST_BE_BIGGER_THAN_ONE("uses-must-be-bigger-than-one"),
    WEBHOOK_ENABLED("webhook.enabled"),
    WEBHOOK_URL("webhook.url"),

    WEBHOOK_CODE_CREATE_EMBED_TITLE("webhook.code-create-embed.title"),
    WEBHOOK_CODE_CREATE_EMBED_DESCRIPTION("webhook.code-create-embed.description"),
    WEBHOOK_CODE_CREATE_EMBED_COLOR("webhook.code-create-embed.color"),
    WEBHOOK_CODE_CREATE_EMBED_AUTHOR_NAME("webhook.code-create-embed.author-name"),
    WEBHOOK_CODE_CREATE_EMBED_AUTHOR_URL("webhook.code-create-embed.author-url"),
    WEBHOOK_CODE_CREATE_EMBED_AUTHOR_ICON("webhook.code-create-embed.author-icon"),
    WEBHOOK_CODE_CREATE_EMBED_FOOTER_TEXT("webhook.code-create-embed.footer-text"),
    WEBHOOK_CODE_CREATE_EMBED_FOOTER_ICON("webhook.code-create-embed.footer-icon"),
    WEBHOOK_CODE_CREATE_EMBED_THUMBNAIL("webhook.code-create-embed.thumbnail"),
    WEBHOOK_CODE_CREATE_EMBED_IMAGE("webhook.code-create-embed.image"),

    WEBHOOK_CODE_DELETE_EMBED_TITLE("webhook.code-delete-embed.title"),
    WEBHOOK_CODE_DELETE_EMBED_DESCRIPTION("webhook.code-delete-embed.description"),
    WEBHOOK_CODE_DELETE_EMBED_COLOR("webhook.code-delete-embed.color"),
    WEBHOOK_CODE_DELETE_EMBED_AUTHOR_NAME("webhook.code-delete-embed.author-name"),
    WEBHOOK_CODE_DELETE_EMBED_AUTHOR_URL("webhook.code-delete-embed.author-url"),
    WEBHOOK_CODE_DELETE_EMBED_AUTHOR_ICON("webhook.code-delete-embed.author-icon"),
    WEBHOOK_CODE_DELETE_EMBED_FOOTER_TEXT("webhook.code-delete-embed.footer-text"),
    WEBHOOK_CODE_DELETE_EMBED_FOOTER_ICON("webhook.code-delete-embed.footer-icon"),
    WEBHOOK_CODE_DELETE_EMBED_THUMBNAIL("webhook.code-delete-embed.thumbnail"),
    WEBHOOK_CODE_DELETE_EMBED_IMAGE("webhook.code-delete-embed.image"),

    WEBHOOK_CODE_EDIT_USE_EMBED_TITLE("webhook.code-edituse-embed.title"),
    WEBHOOK_CODE_EDIT_USE_EMBED_DESCRIPTION("webhook.code-edituse-embed.description"),
    WEBHOOK_CODE_EDIT_USE_EMBED_COLOR("webhook.code-edituse-embed.color"),
    WEBHOOK_CODE_EDIT_USE_EMBED_AUTHOR_NAME("webhook.code-edituse-embed.author-name"),
    WEBHOOK_CODE_EDIT_USE_EMBED_AUTHOR_URL("webhook.code-edituse-embed.author-url"),
    WEBHOOK_CODE_EDIT_USE_EMBED_AUTHOR_ICON("webhook.code-edituse-embed.author-icon"),
    WEBHOOK_CODE_EDIT_USE_EMBED_FOOTER_TEXT("webhook.code-edituse-embed.footer-text"),
    WEBHOOK_CODE_EDIT_USE_EMBED_FOOTER_ICON("webhook.code-edituse-embed.footer-icon"),
    WEBHOOK_CODE_EDIT_USE_EMBED_THUMBNAIL("webhook.code-edituse-embed.thumbnail"),
    WEBHOOK_CODE_EDIT_USE_EMBED_IMAGE("webhook.code-edituse-embed.image"),

    WEBHOOK_CODE_EDIT_COMMAND_EMBED_TITLE("webhook.code-editcommand-embed.title"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_DESCRIPTION("webhook.code-editcommand-embed.description"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_COLOR("webhook.code-editcommand-embed.color"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_AUTHOR_NAME("webhook.code-editcommand-embed.author-name"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_AUTHOR_URL("webhook.code-editcommand-embed.author-url"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_AUTHOR_ICON("webhook.code-editcommand-embed.author-icon"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_FOOTER_TEXT("webhook.code-editcommand-embed.footer-text"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_FOOTER_ICON("webhook.code-editcommand-embed.footer-icon"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_THUMBNAIL("webhook.code-editcommand-embed.thumbnail"),
    WEBHOOK_CODE_EDIT_COMMAND_EMBED_IMAGE("webhook.code-editcommand-embed.image"),

    WEBHOOK_CODE_EDIT_NAME_EMBED_TITLE("webhook.code-editname-embed.title"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_DESCRIPTION("webhook.code-editname-embed.description"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_COLOR("webhook.code-editname-embed.color"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_AUTHOR_NAME("webhook.code-editname-embed.author-name"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_AUTHOR_URL("webhook.code-editname-embed.author-url"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_AUTHOR_ICON("webhook.code-editname-embed.author-icon"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_FOOTER_TEXT("webhook.code-editname-embed.footer-text"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_FOOTER_ICON("webhook.code-editname-embed.footer-icon"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_THUMBNAIL("webhook.code-editname-embed.thumbnail"),
    WEBHOOK_CODE_EDIT_NAME_EMBED_IMAGE("webhook.code-editname-embed.image"),

    AVAILABLE_MENU_TITLE("available-menu.title"),
    AVAILABLE_MENU_TICK("available-menu.update-tick"),
    AVAILABLE_MENU_SIZE("available-menu.size"),
    AVAILABLE_BACK_SLOT("available-menu.back-item.slot"),
    AVAILABLE_FORWARD_SLOT("available-menu.forward-item.slot"),
    AVAILABLE_FILLER_GLASS("available-menu.filler-glass"),

    ALL_MENU_TITLE("all-menu.title"),
    ALL_MENU_TICK("all-menu.update-tick"),
    ALL_MENU_SIZE("all-menu.size"),
    ALL_BACK_SLOT("all-menu.back-item.slot"),
    ALL_FILLER_GLASS("all-menu.filler-glass"),
    ALL_FORWARD_SLOT("all-menu.forward-item.slot"),

    MAIN_MENU_TITLE("main-menu.title"),
    MAIN_AVAILABLE_MENU_SLOT("main-menu.available-menu-item.slot"),
    MAIN_ALL_MENU_SLOT("main-menu.all-menu-item.slot"),
    MAIN_MENU_SIZE("main-menu.size"),
    MAIN_MENU_FILLER_GLASS("main-menu.filler-glass");

    private final String path;

    ConfigKeys(@NotNull final String path) {
        this.path = path;
    }

    public String getString() {
        return MessageProcessor.process(CCode.getInstance().getConfiguration().getString(path));
    }

    public boolean getBoolean() {
        return CCode.getInstance().getConfiguration().getBoolean(path);
    }

    public int getInt() {
        return CCode.getInstance().getConfiguration().getInt(path);
    }
}
