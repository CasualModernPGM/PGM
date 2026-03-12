package tc.oc.pgm.ffa;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.NameTagVisibility;
import org.jetbrains.annotations.Nullable;

public class FreeForAllOptions {
  public final int minPlayers;
  public final int maxPlayers;
  public final int maxOverfill;
  public final NameTagVisibility nameTagVisibility;
  public final boolean colors;
  public final @Nullable ChatColor singleColor;
  public final @Nullable String customName;

  public FreeForAllOptions(
      int minPlayers,
      int maxPlayers,
      int maxOverfill,
      NameTagVisibility nameTagVisibility,
      boolean colors,
      @Nullable ChatColor singleColor,
      @Nullable String customName) {
    this.minPlayers = minPlayers;
    this.maxPlayers = maxPlayers;
    this.maxOverfill = maxOverfill;
    this.nameTagVisibility = nameTagVisibility;
    this.colors = colors;
    this.singleColor = singleColor;
    this.customName = customName;
  }
}
