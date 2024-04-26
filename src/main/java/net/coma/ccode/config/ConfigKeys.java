package net.coma.ccode.config;

import net.coma.ccode.CCode;
import net.coma.ccode.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigKeys {
    public static String CODE_ITEM_MATERIAL = getString("code-item.material");
    public static String CODE_ITEM_NAME = getString("code-item.name");
    public static List<String> CODE_ITEM_LORE = getLoreList();

    private static String getString(@NotNull String path) {
        return MessageProcessor.process(CCode.getInstance().getConfigFile().getString(path));
    }

    private static List<String> getLoreList() {
        List<String> originalList = CCode.getInstance().getConfigFile().getLoreList("code-item.lore");
        List<String> processedList = new ArrayList<>();

        originalList.forEach(line -> processedList.add(MessageProcessor.process(line)));

        return processedList;
    }

}
