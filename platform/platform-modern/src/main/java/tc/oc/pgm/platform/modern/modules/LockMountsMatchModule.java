package tc.oc.pgm.platform.modern.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.api.player.MatchPlayer;

public class LockMountsMatchModule implements MatchModule, Listener {

  private final Match match;
  private final boolean lockMounts;

  public LockMountsMatchModule(Match match, boolean lockMounts) {
    this.match = match;
    this.lockMounts = lockMounts;
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onSneakWhileMounted(PlayerToggleSneakEvent event) {
    if (!lockMounts || !event.isSneaking()) return;

    Player player = event.getPlayer();
    if (!player.isInsideVehicle()) return;

    MatchPlayer mp = match.getPlayer(player);
    if (mp != null && mp.isParticipating()) {
      event.setCancelled(true);
    }
  }
}
