package tc.oc.pgm.platform.modern.modules.cmp.quake;

import org.bukkit.Color;
import org.bukkit.Material;

public final class RailgunDefinition {
  private final Material material;
  private final Color beamColor;
  private final Color spiralColor;

  public RailgunDefinition(Material material, Color beamColor, Color spiralColor) {
    this.material = material;
    this.beamColor = beamColor;
    this.spiralColor = spiralColor;
  }

  public Material material() {
    return material;
  }

  public Color beamColor() {
    return beamColor;
  }

  public Color spiralColor() {
    return spiralColor;
  }
}
