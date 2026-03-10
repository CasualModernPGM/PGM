package tc.oc.pgm.platform.modern.modules;

import static tc.oc.pgm.util.platform.Supports.Variant.PAPER;

import tc.oc.pgm.api.Modules;
import tc.oc.pgm.platform.modern.modules.cmp.LockMountsMatchModule;
import tc.oc.pgm.platform.modern.modules.cmp.LockMountsModule;
import tc.oc.pgm.platform.modern.modules.cmp.quake.QuakeMatchModule;
import tc.oc.pgm.platform.modern.modules.cmp.quake.QuakeModule;
import tc.oc.pgm.platform.modern.modules.kits.ModernKitMatchModule;
import tc.oc.pgm.platform.modern.modules.trim.TrimMatchModule;
import tc.oc.pgm.platform.modern.modules.trim.TrimModule;
import tc.oc.pgm.platform.modern.modules.waypoint.WaypointMatchModule;
import tc.oc.pgm.platform.modern.tracker.ModernTrackerMatchModule;
import tc.oc.pgm.util.platform.Supports;

@Supports(PAPER)
public class ModernModuleRegistrar implements Modules.ModuleRegistrar {
  @Override
  public void registerModules(Modules modules) {
    modules.register(ModernEventFilterMatchModule.class, ModernEventFilterMatchModule::new);
    modules.register(
        LockMountsModule.class, LockMountsMatchModule.class, new LockMountsModule.Factory());
    modules.register(WaypointMatchModule.class, WaypointMatchModule::new);
    modules.register(QuakeModule.class, QuakeMatchModule.class, new QuakeModule.Factory());
    modules.register(ModernTrackerMatchModule.class, ModernTrackerMatchModule::new);
    modules.register(ModernKitMatchModule.class, new ModernKitMatchModule.Factory());
    modules.register(TrimModule.class, TrimMatchModule.class, new TrimModule.Factory());
  }
}
