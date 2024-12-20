package net.coma.ccode.listeners;

import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.events.CodeCreateEvent;
import net.coma.ccode.hooks.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

public class CodeCreateListener implements Listener {
    @EventHandler
    public void onCreate(final CodeCreateEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Webhook.sendWebhook(Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_DESCRIPTION.getString(), event),
                ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_COLOR.getString(),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_AUTHOR_NAME.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_AUTHOR_URL.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_AUTHOR_ICON.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_FOOTER_TEXT.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_FOOTER_ICON.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_THUMBNAIL.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_TITLE.getString(), event),
                Webhook.replacePlaceholdersCodeCreate(ConfigKeys.WEBHOOK_CODE_CREATE_EMBED_IMAGE.getString(), event));
    }
}
