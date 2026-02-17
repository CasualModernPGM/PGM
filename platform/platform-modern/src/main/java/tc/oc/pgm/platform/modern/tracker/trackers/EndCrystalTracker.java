package tc.oc.pgm.platform.modern.tracker.trackers;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.player.ParticipantState;
import tc.oc.pgm.tracker.TrackerMatchModule;
import tc.oc.pgm.tracker.info.PlayerInfo;

public class EndCrystalTracker implements Listener {
  private final Match match;

  public EndCrystalTracker(Match match) {
    this.match = match;
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onDamage(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof EnderCrystal crystal) {
      ParticipantState attacker = match.getParticipantState(event.getDamager());
      if (attacker != null) {
        match
            .getModule(TrackerMatchModule.class)
            .getEntityTracker()
            .trackEntity(crystal, new PlayerInfo(attacker));
      }
    }
  }
}
