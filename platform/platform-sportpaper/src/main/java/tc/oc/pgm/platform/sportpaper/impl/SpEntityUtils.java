package tc.oc.pgm.platform.sportpaper.impl;

import static tc.oc.pgm.util.platform.Supports.Variant.SPORTPAPER;

import org.bukkit.entity.LivingEntity;
import tc.oc.pgm.util.nms.EntityUtils;
import tc.oc.pgm.util.platform.Supports;

@Supports(SPORTPAPER)
public class SpEntityUtils implements EntityUtils {
  @Override
  public void setSilent(LivingEntity entity, boolean silent) {}
}
