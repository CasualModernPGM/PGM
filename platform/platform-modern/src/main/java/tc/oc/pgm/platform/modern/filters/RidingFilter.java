package tc.oc.pgm.platform.modern.filters;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import tc.oc.pgm.api.filter.FilterDefinition;
import tc.oc.pgm.api.filter.query.PlayerQuery;
import tc.oc.pgm.api.player.MatchPlayer;
import tc.oc.pgm.filters.matcher.player.ParticipantFilter;

/* CMP FORK */
public class RidingFilter extends ParticipantFilter {
  public static final FilterDefinition INSTANCE = new RidingFilter();

  @Override
  public Collection<Class<? extends Event>> getRelevantEvents() {
    return ImmutableList.of(
        EntityMountEvent.class,
        EntityDismountEvent.class,
        PlayerTeleportEvent.class,
        PlayerQuitEvent.class);
  }

  @Override
  public boolean matches(PlayerQuery query, MatchPlayer player) {
    Entity vehicle = player.getBukkit().getVehicle();
    return vehicle != null;
  }
}
