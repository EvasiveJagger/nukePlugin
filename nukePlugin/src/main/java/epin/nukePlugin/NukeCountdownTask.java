package epin.nukePlugin;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class NukeCountdownTask extends BukkitRunnable {
    private int startTick;

    public NukeCountdownTask(int startTick) {
        this.startTick = startTick;
    }

    @Override
    public void run() {
        int tickDiff = nukePlugin.instance.getServer().getCurrentTick() - startTick;
        if (tickDiff == 1200) {
            //one minute
            nukePlugin.instance.getServer().sendMessage(Component.text("NUKE EXPLOSION IN T-2:00"));
        }
        if (tickDiff == 2400) {
            //one minute
            nukePlugin.instance.getServer().sendMessage(Component.text("NUKE EXPLOSION IN T-1:00"));
        }
        if (tickDiff == 3000) {
            //one minute
            nukePlugin.instance.getServer().sendMessage(Component.text("NUKE EXPLOSION IN T-0:30"));
        }
        if (tickDiff == (3600 - 60)) {
            //one minute
            nukePlugin.instance.getServer().sendMessage(Component.text("3"));
        }
        if (tickDiff == (3600 - 40)) {
            //one minute
            nukePlugin.instance.getServer().sendMessage(Component.text("2"));
        }
        if (tickDiff == (3600 - 20)) {
            //one minute
            nukePlugin.instance.getServer().sendMessage(Component.text("1"));
        }
        if (tickDiff == 3600) {
            this.cancel();
        }
    }
}
