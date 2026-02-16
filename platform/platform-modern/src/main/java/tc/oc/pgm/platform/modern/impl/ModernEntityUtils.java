package tc.oc.pgm.platform.modern.impl;

import static tc.oc.pgm.util.platform.Supports.Variant.PAPER;

import org.bukkit.entity.LivingEntity;
import tc.oc.pgm.util.nms.EntityUtils;
import tc.oc.pgm.util.platform.Supports;

@Supports(value = PAPER, minVersion = "1.21.11")
public class ModernEntityUtils implements EntityUtils {
  @Override
  public void setSilent(LivingEntity entity, boolean silent) {
    entity.setSilent(silent);
  }
}
