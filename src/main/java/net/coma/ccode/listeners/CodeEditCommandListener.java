package net.coma.ccode.listeners;

import net.coma.ccode.enums.keys.ConfigKeys;
import net.coma.ccode.events.CodeEditCommandEvent;
import net.coma.ccode.hooks.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

public class CodeEditCommandListener implements Listener {
    @EventHandler
    public void onEditCommand(final CodeEditCommandEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Webhook.sendWebhook(Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_DESCRIPTION.getString(), event),
                ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_COLOR.getString(),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_AUTHOR_NAME.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_AUTHOR_URL.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_AUTHOR_ICON.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_FOOTER_TEXT.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_FOOTER_ICON.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_THUMBNAIL.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_TITLE.getString(), event),
                Webhook.replacePlaceholdersCodeEditCommand(ConfigKeys.WEBHOOK_CODE_EDIT_COMMAND_EMBED_IMAGE.getString(), event));
    }
}