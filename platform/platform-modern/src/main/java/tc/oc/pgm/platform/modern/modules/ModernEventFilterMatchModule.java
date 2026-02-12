package tc.oc.pgm.platform.modern.modules;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.modules.EventFilterMatchModule;

public class ModernEventFilterMatchModule extends EventFilterMatchModule
    implements MatchModule, Listener {

  private final Match match;

  public ModernEventFilterMatchModule(Match match) {
    super(match);
    this.match = match;
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onEntityDismount(EntityDismountEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    Block feetBlock = player.getLocation().getBlock();
    Block headBlock = player.getEyeLocation().getBlock();

    if (feetBlock.getType() == Material.WATER || headBlock.getType() == Material.WATER) {
      event.setCancelled(true);
    }
  }
}
