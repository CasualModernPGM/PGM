package tc.oc.pgm.util.nms;

import org.bukkit.entity.LivingEntity;
import tc.oc.pgm.util.platform.Platform;

public interface EntityUtils {
  EntityUtils ENTITY_UTILS = Platform.get(EntityUtils.class);

  void setSilent(LivingEntity entity, boolean silent);
}
