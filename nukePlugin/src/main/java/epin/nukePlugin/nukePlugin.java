package epin.nukePlugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class nukePlugin extends JavaPlugin {

    public static nukePlugin instance;

    public ArrayList<ItemStack> items;

    public ArrayList<NamespacedKey> keys;

    public ItemStack nuke;

    public NamespacedKey nukeKey;

    public ItemStack gpdrBlock;

    public NamespacedKey gpdrBlkKey;

    public ItemStack unstableCore;

    public NamespacedKey unCoreKey;

    public ItemStack stableCore;

    public NamespacedKey coreKey;

    public NamespacedKey isNuke;

    @Override
    public void onEnable() {
        instance = this;

        ArrayList<Component> genLore = new ArrayList<>();
        genLore.add(Component.text("Part of the Nuke plugin by Ep1n"));

        items = new ArrayList<>();
        keys = new ArrayList<>();

        nuke = new ItemStack(Material.TNT);
        isNuke = new NamespacedKey(this, "isNuke");
        nukeKey = new NamespacedKey(this, "nuke");
        keys.add(nukeKey);
        ItemMeta nukeMeta = nuke.getItemMeta();
        nukeMeta.customName(Component.text(ChatColor.LIGHT_PURPLE + "Nuke"));
        nukeMeta.lore(genLore);
        nuke.setItemMeta(nukeMeta);
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getCommand("gimme").setExecutor(new GiveNukeCommand());
        items.add(nuke);

        gpdrBlkKey = new NamespacedKey(this, "gunpowder_block");
        keys.add(gpdrBlkKey);
        gpdrBlock = new ItemStack(Material.GRAY_CONCRETE_POWDER);
        ItemMeta gpdrMeta = gpdrBlock.getItemMeta();
        gpdrMeta.customName(Component.text("Block of Gunpowder"));
        gpdrMeta.lore(genLore);
        gpdrBlock.setItemMeta(gpdrMeta);
        items.add(gpdrBlock);

        unCoreKey = new NamespacedKey(this, "unstable_core");
        keys.add(unCoreKey);
        unstableCore = new ItemStack(Material.WAXED_COPPER_BULB);
        ItemMeta unstableMeta = unstableCore.getItemMeta();
        unstableMeta.customName(Component.text(ChatColor.AQUA + "Unstable Nuclear Core"));
        ArrayList<Component> unstableLore = new ArrayList<>();
        unstableLore.add(genLore.get(0));
        unstableLore.add(Component.text("Blasting (blast furnace) this is a good idea."));
        unstableLore.add(Component.text("Dropping it, however, is not a good idea."));
        unstableMeta.lore(unstableLore);
        unstableCore.setItemMeta(unstableMeta);
        items.add(unstableCore);

        coreKey = new NamespacedKey(this, "stable_core");
        keys.add(coreKey);
        stableCore = new ItemStack(Material.WAXED_OXIDIZED_COPPER_BULB);
        ItemMeta stableMeta = stableCore.getItemMeta();
        stableMeta.customName(Component.text(ChatColor.AQUA + "Stable Nuclear Core"));
        stableMeta.lore(genLore);
        stableCore.setItemMeta(stableMeta);
        items.add(stableCore);

        Bukkit.getLogger().info("sigma energy");
        // Plugin startup logic
        // Run the method to give all recipes but for each player in the server on reload

        //recipes below

        //gunpowder block
        ShapedRecipe gpwdBlockRecipe = new ShapedRecipe(gpdrBlkKey, gpdrBlock);
        gpwdBlockRecipe.shape("GGG",
                              "GGG",
                              "GGG");
        gpwdBlockRecipe.setIngredient('G', Material.GUNPOWDER);
        getServer().addRecipe(gpwdBlockRecipe, true);

        //gunpowder from block into dust
        NamespacedKey gpwd = new NamespacedKey(this, "gunpowder");
        keys.add(gpwd);
        ShapelessRecipe blockToGpwd = new ShapelessRecipe(gpwd, new ItemStack(Material.GUNPOWDER, 9));
        blockToGpwd.addIngredient(gpdrBlock);
        getServer().addRecipe(blockToGpwd, true);

        //unstable core
        ShapedRecipe unstableCoreRecipe = new ShapedRecipe(unCoreKey, unstableCore);
        unstableCoreRecipe.shape("NGS",
                                 "GTG",
                                 "SGN");
        unstableCoreRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        unstableCoreRecipe.setIngredient('G', Material.GLOWSTONE);
        unstableCoreRecipe.setIngredient('T', Material.TNT);
        unstableCoreRecipe.setIngredient('S', Material.WITHER_SKELETON_SKULL);
        getServer().addRecipe(unstableCoreRecipe, true);

        //stable core
        BlastingRecipe stableCoreRecipe = new BlastingRecipe(coreKey, stableCore, new RecipeChoice.ExactChoice(unstableCore), 2, 7200);
        getServer().addRecipe(stableCoreRecipe, true);
        //a furnace full of blaze rods will be able to smelt 2 total
        //check onSmeltBypass if this doesn't work

        //the big boy
        ShapedRecipe nukeRecipe = new ShapedRecipe(nukeKey, nuke);
        nukeRecipe.shape("GGG",
                         "GTG",
                         "GGG");
        nukeRecipe.setIngredient('G', gpdrBlock);
        nukeRecipe.setIngredient('T', stableCore);
        getServer().addRecipe(nukeRecipe, true);

        for(Player p : getServer().getOnlinePlayers()) {
            unlockRecipes(p);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void unlockRecipes(Player p) {
        for(NamespacedKey key : nukePlugin.instance.keys) {
            if (p.hasDiscoveredRecipe(key)) {
                p.discoverRecipe(key);
            }
        }
    }
}
