package tc.oc.pgm.action.actions;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import tc.oc.pgm.action.replacements.Replacement;
import tc.oc.pgm.api.PGM;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.player.MatchPlayer;
import tc.oc.pgm.filters.Filterable;

public class ExecuteAction extends AbstractAction<Audience> {
  private static final Pattern PATTERN = Pattern.compile("\\{(.+?)}");
  private static final PlainTextComponentSerializer SERIALIZER =
      PlainTextComponentSerializer.plainText();

  private final Component command;
  private final Map<String, Replacement> replacements;
  private final boolean playerContext;

  public ExecuteAction(
      Component command, Map<String, Replacement> replacements, boolean playerContext) {
    super(Audience.class);
    this.command = command;
    this.replacements = replacements;
    this.playerContext = playerContext;
  }

  @Override
  public void trigger(Audience audience) {
    Match match = null;
    Filterable<?> ctx = null;
    String playerName = "@a";
    String coords = "0 0 0";
    Player player = null;

    if (audience instanceof MatchPlayer) {
      MatchPlayer matchPlayer = (MatchPlayer) audience;
      player = matchPlayer.getBukkit();
      match = matchPlayer.getMatch();
      ctx = matchPlayer;
      playerName = player.getName();
      Location loc = player.getLocation();
      coords = loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    } else if (audience instanceof Match) {
      match = (Match) audience;
      ctx = match;
    }

    if (match == null || ctx == null) {
      return;
    }

    String parsed = replace(command, ctx);
    parsed = parsed.replace("#player#", playerName);
    parsed = parsed.replace("~ ~ ~", coords);

    for (String blocked : PGM.get().getConfiguration().getBlockedCommands()) {
      Pattern p = Pattern.compile("\\b" + Pattern.quote(blocked.toLowerCase()) + "\\b");
      if (p.matcher(parsed.toLowerCase()).find()) {
        PGM.get()
            .getGameLogger()
            .log(Level.SEVERE, "Blocked command in match " + match.getId() + ": " + parsed);
        return;
      }
    }

    try {
      if (playerContext && player != null) {
        Bukkit.dispatchCommand(player, parsed);
      } else {
        String prefix = "execute in minecraft:match-" + match.getId() + " run ";
        String fullCommand = prefix + parsed;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), fullCommand);
      }
    } catch (CommandException e) {
      PGM.get()
          .getGameLogger()
          .log(
              Level.SEVERE,
              "Failed to execute command in match " + match.getId() + ": " + parsed,
              e);
    }
  }

  private String replace(Component component, Filterable<?> scope) {
    if (component == null || replacements == null) {
      return SERIALIZER.serialize(component);
    }

    BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacer =
        (MatchResult mr, TextComponent.Builder original) -> {
          Replacement r = replacements.get(mr.group(1));
          return r != null ? r.get(scope) : original;
        };

    Component replaced = component.replaceText(b -> b.match(PATTERN).replacement(replacer));
    return SERIALIZER.serialize(replaced);
  }
}
