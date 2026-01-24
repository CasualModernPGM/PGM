package tc.oc.pgm.action.actions;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.player.MatchPlayer;

public class ExecuteAction extends AbstractAction<Audience> {

  private final String command;

  public ExecuteAction(String command) {
    super(Audience.class);
    this.command = command;
  }

  @Override
  public void trigger(Audience audience) {
    String parsed = command;
    Match match = null;

    if (audience instanceof MatchPlayer) {
      MatchPlayer matchPlayer = (MatchPlayer) audience;
      Player player = matchPlayer.getBukkit();
      match = matchPlayer.getMatch();

      parsed = parsed.replace("{player}", player.getName());

      Location loc = player.getLocation();
      parsed =
          parsed.replace("~ ~ ~", loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
    } else if (audience instanceof Match) {
      match = (Match) audience;
      parsed = parsed.replace("{player}", "@a");
      parsed = parsed.replace("~ ~ ~", "0 0 0");
    }

    if (match != null) {
      String prefix = "execute in minecraft:match-" + match.getId() + " run ";
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), prefix + parsed);
    }
  }
}
