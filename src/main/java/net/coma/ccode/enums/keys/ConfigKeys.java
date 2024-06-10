package net.coma.ccode.enums.keys;

import net.coma.ccode.CCode;
import net.coma.ccode.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public enum ConfigKeys {
    MENU_SIZE("menu.size"),
    BACK_SLOT("menu.back-item.slot"),
    FORWARD_SLOT("menu.forward-item.slot"),
    MENU_TICK("menu.update-tick"),
    LANGUAGE("language"),
    WEBHOOK_ENABLED("webhook.enabled"),
    WEBHOOK_URL("webhook.url"),
    MENU_TITLE("menu.title"),

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
    WEBHOOK_CODE_DELETE_EMBED_IMAGE("webhook.code-delete-embed.image");

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
