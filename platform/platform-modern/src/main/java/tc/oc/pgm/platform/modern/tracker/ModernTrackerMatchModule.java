package tc.oc.pgm.platform.modern.tracker;

import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.api.match.MatchScope;
import tc.oc.pgm.platform.modern.tracker.resolvers.EndCrystalDamageResolver;
import tc.oc.pgm.platform.modern.tracker.trackers.EndCrystalTracker;
import tc.oc.pgm.tracker.TrackerMatchModule;

public class ModernTrackerMatchModule implements MatchModule {
  private final Match match;

  public ModernTrackerMatchModule(Match match) {
    this.match = match;
  }

  @Override
  public void load() {
    match.addListener(new EndCrystalTracker(match), MatchScope.RUNNING);
    match
        .getModule(TrackerMatchModule.class)
        .registerDamageResolverEarly(new EndCrystalDamageResolver());
  }
}
