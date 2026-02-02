package tc.oc.pgm.modules;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jspecify.annotations.Nullable;
import tc.oc.pgm.api.filter.Filter;
import tc.oc.pgm.api.filter.Filter.QueryResponse;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.api.match.MatchScope;
import tc.oc.pgm.events.ListenerScope;
import tc.oc.pgm.filters.query.EntitySpawnQuery;

@ListenerScope(MatchScope.LOADED)
public class MobsMatchModule implements MatchModule, Listener {
  private final Match match;
  private final @Nullable Filter mobsFilter;

  public MobsMatchModule(Match match, @Nullable Filter mobsFilter) {
    this.match = match;
    this.mobsFilter = mobsFilter;
  }

  @Override
  public void enable() {
    this.match.getWorld().setSpawnFlags(true, true);
  }

  @Override
  public void disable() {
    this.match.getWorld().setSpawnFlags(false, false);
  }

  @EventHandler(ignoreCancelled = true)
  public void checkSpawn(final CreatureSpawnEvent event) {
    // Allow obscure spawn reasons that can only occur if
    // the player has access to certain creative-only items.
    CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
    switch (reason) {
      case CUSTOM:
      case DEFAULT: // Caused by /summon in 1.8
      case SPAWNER:
      case SPAWNER_EGG:
      case DISPENSE_EGG:
      case SILVERFISH_BLOCK:
        return;
    }

    if ("COMMAND".equalsIgnoreCase(reason.name())) {
      return; // Caused by /summon in 1.13+
    }

    // Always allow armor stands since they are not really mobs.
    if (event.getEntityType() == EntityType.ARMOR_STAND) {
      return;
    }

    if (this.mobsFilter == null) {
      event.setCancelled(true);
    } else {
      final QueryResponse response =
          this.mobsFilter.query(new EntitySpawnQuery(event, event.getEntity(), reason));
      event.setCancelled(response.isDenied());
    }
  }
}
