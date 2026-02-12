package tc.oc.pgm.platform.modern.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.api.player.MatchPlayer;

public class QuakeMatchModule implements MatchModule, Listener {

  private final Match match;
  private final boolean enabled;

  private static final double MAX_RANGE = 100.0;
  private static final long COOLDOWN_MS = 1500;
  private static final long BOOST_COOLDOWN_MS = 2000;

  private final Map<UUID, Long> shootCooldowns = new HashMap<>();
  private final Map<UUID, Long> boostCooldowns = new HashMap<>();

  public QuakeMatchModule(Match match, boolean enabled) {
    this.match = match;
    this.enabled = enabled;
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (!enabled) return;

    Player player = event.getPlayer();
    MatchPlayer shooter = match.getPlayer(player);
    if (shooter == null || !shooter.isParticipating()) return;

    ItemStack item = player.getInventory().getItemInMainHand();
    if (item == null || item.getType() != Material.NETHERITE_HOE) return;

    switch (event.getAction()) {
      case RIGHT_CLICK_AIR:
      case RIGHT_CLICK_BLOCK:
        long nowShoot = System.currentTimeMillis();
        long lastShoot = shootCooldowns.getOrDefault(player.getUniqueId(), 0L);
        if (nowShoot - lastShoot < COOLDOWN_MS) return;
        shootCooldowns.put(player.getUniqueId(), nowShoot);

        player.setCooldown(Material.NETHERITE_HOE, (int) (COOLDOWN_MS / 50));

        Vector direction = player.getEyeLocation().getDirection();
        RayTraceResult result = player
            .getWorld()
            .rayTraceEntities(
                player.getEyeLocation(),
                direction,
                MAX_RANGE,
                entity -> entity instanceof LivingEntity && !entity.equals(player));

        DustOptions rayDust = new DustOptions(Color.SILVER, 1.0F);
        for (double d = 0; d < MAX_RANGE; d += 1.0) {
          Vector point =
              player.getEyeLocation().toVector().add(direction.clone().multiply(d));
          player
              .getWorld()
              .spawnParticle(
                  Particle.DUST, point.getX(), point.getY(), point.getZ(), 1, 0, 0, 0, 0, rayDust);
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.8F);

        if (result != null && result.getHitEntity() instanceof LivingEntity target) {
          target.damage(100.0, shooter.getBukkit());
        }
        break;

      case LEFT_CLICK_AIR:
      case LEFT_CLICK_BLOCK:
        long nowBoost = System.currentTimeMillis();
        long lastBoost = boostCooldowns.getOrDefault(player.getUniqueId(), 0L);
        if (nowBoost - lastBoost < BOOST_COOLDOWN_MS) return;
        boostCooldowns.put(player.getUniqueId(), nowBoost);

        Vector boostDir = player.getLocation().getDirection().normalize();
        Vector boost = boostDir.multiply(1.2).setY(0.5);
        player.setVelocity(boost);

        player
            .getWorld()
            .playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0F, 0.8F);
        break;
    }
  }
}
