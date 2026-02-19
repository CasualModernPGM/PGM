package tc.oc.pgm.platform.modern.modules.cmp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.api.player.MatchPlayer;

@ListenerScope(MatchScope.RUNNING)
public class QuakeMatchModule implements MatchModule, Listener {

  private final Match match;
  private final boolean enabled;

  private static final double MAX_RANGE = 100.0;
  private static final long COOLDOWN_MS = 1000;
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

        RayTraceResult entityResult = player
            .getWorld()
            .rayTraceEntities(
                player.getEyeLocation(),
                direction,
                MAX_RANGE,
                0.25,
                entity -> entity instanceof LivingEntity && !entity.equals(player));

        RayTraceResult blockResult = player
            .getWorld()
            .rayTraceBlocks(
                player.getEyeLocation(), direction, MAX_RANGE, FluidCollisionMode.NEVER);

        double maxDistance = MAX_RANGE;
        if (blockResult != null && blockResult.getHitBlock() != null) {
          maxDistance =
              blockResult.getHitPosition().distance(player.getEyeLocation().toVector());
        }

        DustOptions blackDust = new DustOptions(Color.BLACK, 1.0F);
        DustOptions whiteDust = new DustOptions(Color.WHITE, 1.0F);

        for (double d = 0; d < maxDistance; d += 1.0) {
          Vector basePoint =
              player.getEyeLocation().toVector().add(direction.clone().multiply(d));

          player
              .getWorld()
              .spawnParticle(
                  Particle.DUST,
                  basePoint.getX(),
                  basePoint.getY(),
                  basePoint.getZ(),
                  1,
                  0,
                  0,
                  0,
                  0,
                  blackDust);

          double radius = 0.2;
          double angle = d * 0.8;

          Vector perp1 = direction.clone().normalize().crossProduct(new Vector(0, 1, 0));
          if (perp1.lengthSquared() == 0) {
            perp1 = direction.clone().normalize().crossProduct(new Vector(1, 0, 0));
          }
          Vector perp2 = direction.clone().normalize().crossProduct(perp1).normalize();

          Vector offset = perp1
              .normalize()
              .multiply(Math.cos(angle) * radius)
              .add(perp2.multiply(Math.sin(angle) * radius));

          Vector spiralPoint = basePoint.clone().add(offset);

          player
              .getWorld()
              .spawnParticle(
                  Particle.DUST,
                  spiralPoint.getX(),
                  spiralPoint.getY(),
                  spiralPoint.getZ(),
                  1,
                  0,
                  0,
                  0,
                  0,
                  whiteDust);
        }

        if (blockResult != null && blockResult.getHitBlock() != null) {
          Vector hitPos = blockResult.getHitPosition();
          player
              .getWorld()
              .spawnParticle(
                  Particle.LARGE_SMOKE,
                  hitPos.getX(),
                  hitPos.getY(),
                  hitPos.getZ(),
                  10,
                  0.2,
                  0.2,
                  0.2,
                  0.01);
          player
              .getWorld()
              .playSound(hitPos.toLocation(player.getWorld()), Sound.BLOCK_STONE_HIT, 1.0F, 1.0F);
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.8F);

        if (entityResult != null && entityResult.getHitEntity() instanceof LivingEntity target) {
          double entityDistance =
              entityResult.getHitPosition().distance(player.getEyeLocation().toVector());
          if (entityDistance <= maxDistance) {
            target.damage(100.0, shooter.getBukkit());
          }
        }
        break;

      case LEFT_CLICK_AIR:
      case LEFT_CLICK_BLOCK:
        long nowBoost = System.currentTimeMillis();
        long lastBoost = boostCooldowns.getOrDefault(player.getUniqueId(), 0L);
        if (nowBoost - lastBoost < BOOST_COOLDOWN_MS) return;
        boostCooldowns.put(player.getUniqueId(), nowBoost);

        Vector boostDir = player.getLocation().getDirection().normalize();
        Vector boost = boostDir.multiply(1.8).setY(0.4);
        player.setVelocity(boost);

        player
            .getWorld()
            .playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0F, 0.8F);
        break;
    }
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    UUID id = event.getPlayer().getUniqueId();
    shootCooldowns.remove(id);
    boostCooldowns.remove(id);
  }
}
