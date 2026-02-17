package tc.oc.pgm.platform.modern.tracker.resolvers;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Nullable;
import tc.oc.pgm.api.tracker.DamageResolver;
import tc.oc.pgm.api.tracker.info.PhysicalInfo;
import tc.oc.pgm.tracker.info.ExplosionInfo;

public class EndCrystalDamageResolver implements DamageResolver {
  @Override
  public @Nullable ExplosionInfo resolveDamage(
      EntityDamageEvent.DamageCause damageType, Entity victim, @Nullable PhysicalInfo damager) {
    if (damageType == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
        && victim instanceof EnderCrystal
        && damager != null) {
      return new ExplosionInfo(damager);
    }
    return null;
  }
}
