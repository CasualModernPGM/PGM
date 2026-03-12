package tc.oc.pgm.tablist;

import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.ffa.FreeForAllMatchModule;
import tc.oc.pgm.ffa.FreeForAllOptions;
import tc.oc.pgm.util.tablist.DynamicTabEntry;
import tc.oc.pgm.util.tablist.TabView;
import tc.oc.pgm.util.text.TextFormatter;

public class FreeForAllTabEntry extends DynamicTabEntry {

  private final Match match;

  public FreeForAllTabEntry(Match match) {
    this.match = match;
  }

  @Override
  public Component getContent(TabView view) {
    FreeForAllOptions options = match.needModule(FreeForAllMatchModule.class).getOptions();

    NamedTextColor labelColor = NamedTextColor.YELLOW;
    if (options.singleColor != null) labelColor = TextFormatter.convert(options.singleColor);

    Component name = options.customName != null
        ? text(options.customName, labelColor, TextDecoration.BOLD)
        : translatable("match.info.players", labelColor, TextDecoration.BOLD);

    return text()
        .append(text(match.getParticipants().size(), NamedTextColor.WHITE))
        .append(text("/", NamedTextColor.DARK_GRAY))
        .append(text(match.getMaxPlayers(), NamedTextColor.GRAY))
        .append(space())
        .append(name)
        .build();
  }
}
