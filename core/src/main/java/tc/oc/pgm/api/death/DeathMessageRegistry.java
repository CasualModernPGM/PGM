package tc.oc.pgm.api.death;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import tc.oc.pgm.api.player.event.MatchPlayerDeathEvent;

public final class DeathMessageRegistry {
  private static final List<DeathMessageProvider> providers = new ArrayList<>();

  private DeathMessageRegistry() {}

  public static void register(DeathMessageProvider provider) {
    providers.add(provider);
  }

  public static void unregister(DeathMessageProvider provider) {
    providers.remove(provider);
  }

  public static List<DeathMessageProvider> getProviders() {
    return Collections.unmodifiableList(providers);
  }

  @Nullable
  public static Component getCustomMessage(MatchPlayerDeathEvent event) {
    for (DeathMessageProvider provider : providers) {
      Component message = provider.buildMessage(event);
      if (message != null) {
        return message;
      }
    }
    return null;
  }
}
