package epin.nukePlugin;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UnbanTask extends BukkitRunnable {
    private Player banned;

    public UnbanTask(Player p) {
        banned = p;
    }

    @Override
    public void run() {
        nukePlugin.instance.bannedPlayers.remove(banned);
        banned.getServer().sendMessage(Component.text(banned.getName().toUpperCase() + " HAS BEEN UNLEASHED"));
    }
}
