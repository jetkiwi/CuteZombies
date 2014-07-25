package net.ja731j.bukkit.cutezombies;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author ja731j
 */
public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("The CuteZombies plugin has been loaded");
    }

    @Override
    public void onDisable() {
        getLogger().info("The CuteZombies plugin has been unloaded");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.ZOMBIE) {
            World w = e.getEntity().getWorld();
            Location l = e.getEntity().getLocation();

            if (Math.random() > 0.5) {
                LivingEntity le = (LivingEntity) w.spawnEntity(l, EntityType.CREEPER);
                List<PotionEffect> effects = new ArrayList<PotionEffect>();
                effects.add(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1));

                if (Math.random() > 0.5) {
                    effects.add(new PotionEffect(PotionEffectType.SPEED, 1200, 1));
                    le.addPotionEffects(effects);
                }
            } else {
                final Item i = w.dropItem(l, new ItemStack(Material.EXP_BOTTLE));

                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        if (i.isValid()) {
                            i.remove();
                        }
                    }
                }, 45L);
            }
        }
    }
}
