package tc.oc.pgm.platform.modern.modules;

import static tc.oc.pgm.util.platform.Supports.Variant.PAPER;

import tc.oc.pgm.api.Modules;
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
  }
}
