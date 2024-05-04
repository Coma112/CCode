package net.coma.ccode.menu;

import net.coma.ccode.CCode;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuUpdater extends BukkitRunnable {
    private final Menu menu;
    private boolean running = true;

    public MenuUpdater(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void run() {
        if (!running) {
            cancel();
            return;
        }

        if (menu.getInventory().getViewers().contains(menu.menuUtils.getOwner())) menu.updateMenuItems();
        stop();
    }

    public void start(int intervalTicks) {
        runTaskTimer(CCode.getInstance(), intervalTicks, intervalTicks);
    }

    public void stop() {
        running = false;
    }
}

