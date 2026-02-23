package tc.oc.pgm.api.death;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import tc.oc.pgm.api.player.event.MatchPlayerDeathEvent;

public interface DeathMessageProvider {
  @Nullable
  Component buildMessage(MatchPlayerDeathEvent event);
}
