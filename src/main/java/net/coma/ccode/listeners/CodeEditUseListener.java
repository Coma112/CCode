package net.coma.ccode.listeners;

import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.events.CodeEditUseEvent;
import net.coma.ccode.hooks.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

public class CodeEditUseListener implements Listener {
    @EventHandler
    public void onEditUse(final CodeEditUseEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Webhook.sendWebhook(Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_DESCRIPTION.getString(), event),
                ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_COLOR.getString(),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_AUTHOR_NAME.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_AUTHOR_URL.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_AUTHOR_ICON.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_FOOTER_TEXT.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_FOOTER_ICON.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_THUMBNAIL.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_TITLE.getString(), event),
                Webhook.replacePlaceholdersCodeEditUse(ConfigKeys.WEBHOOK_CODE_EDIT_USE_EMBED_IMAGE.getString(), event));
    }
}
