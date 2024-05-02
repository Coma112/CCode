package net.coma.ccode.language;

import net.coma.ccode.CCode;
import net.coma.ccode.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language extends ConfigUtils {
    public Language() {
        super(CCode.getInstance().getDataFolder().getPath() + File.separator + "locales", "messages_en");

        YamlConfiguration yml = getYml();

        yml.addDefault("messages.no-permission", "&b&lCODE &8| &cYou do not have permission to do this!");
        yml.addDefault("messages.reload", "&b&lCODE &8| &aI have successfully reloaded the files!");
        yml.addDefault("messages.player-required", "&b&lCODE &8| &cPlayer is required!");
        yml.addDefault("messages.already-created", "&b&lCODE &8| &cThis code is already exists!");
        yml.addDefault("messages.redeemed", "&b&lCODE &8| &aYou have successfully redeemed this code!");
        yml.addDefault("messages.deleted", "&b&lCODE &8| &aYou have successfully deleted this code!");
        yml.addDefault("messages.need-number", "&b&lCODE &8| &cYou need to type in a number!");
        yml.addDefault("messages.uses-zero", "&b&lCODE &8| &cYou can't redeem this code anymore!");
        yml.addDefault("messages.cant-be-negative", "&b&lCODE &8| &cThe number can't be negative!");
        yml.addDefault("messages.created", "&b&lCODE &8| &aYou have successfully created a new code!");
        yml.addDefault("messages.not-exists", "&b&lCODE &8| &cThere is no such a code in the database!");
        yml.addDefault("messages.edit-cmd", "&b&lCODE &8| &aYou have successfully changed the command of the code!");
        yml.addDefault("messages.edit-uses", "&b&lCODE &8| &aYou have successfully changed the uses of the code!");
        yml.addDefault("messages.edit-name", "&b&lCODE &8| &aYou have successfully changed the name of the code!");
        yml.addDefault("messages.not-an-owner", "&b&lCODE &8| &cYou are not an owner of this code!");
        yml.addDefault("messages.already-owner", "&b&lCODE &8| &cThe player is already an owner!");
        yml.addDefault("messages.successful-add", "&b&lCODE &8| &aYou have successfully gave the player permission to the code!");
        yml.addDefault("messages.first-page", "&b&lCODE &8| &cYou are already on the first page!");
        yml.addDefault("messages.last-page", "&b&lCODE &8| &cYou are already on the last page!");
        yml.addDefault("messages.offline-player", "&b&lCODE &8| &cThe target is offline!");

        yml.options().copyDefaults(true);
        save();
    }
}
