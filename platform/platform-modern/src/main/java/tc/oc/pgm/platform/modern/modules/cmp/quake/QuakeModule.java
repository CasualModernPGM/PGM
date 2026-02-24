package tc.oc.pgm.platform.modern.modules.cmp.quake;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Color;
import org.bukkit.Material;
import org.jdom2.Document;
import org.jdom2.Element;
import tc.oc.pgm.api.map.MapModule;
import tc.oc.pgm.api.map.factory.MapFactory;
import tc.oc.pgm.api.map.factory.MapModuleFactory;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.util.xml.InvalidXMLException;

public class QuakeModule implements MapModule<QuakeMatchModule> {

  private final boolean enabled;
  private final List<RailgunDefinition> railguns;

  public QuakeModule(boolean enabled, List<RailgunDefinition> railguns) {
    this.enabled = enabled;
    this.railguns = List.copyOf(railguns);
  }

  @Override
  public QuakeMatchModule createMatchModule(Match match) {
    return new QuakeMatchModule(match, enabled, railguns);
  }

  public static class Factory implements MapModuleFactory<QuakeModule> {
    @Override
    public QuakeModule parse(MapFactory factory, Logger logger, Document doc)
        throws InvalidXMLException {
      Element quakeEl = doc.getRootElement().getChild("quake");
      if (quakeEl == null) return new QuakeModule(false, List.of());

      List<RailgunDefinition> railguns = new ArrayList<>();
      for (Element railgunEl : quakeEl.getChildren("railgun")) {
        String matName = railgunEl.getAttributeValue("material");
        String beamName = railgunEl.getAttributeValue("beam-color");
        String spiralName = railgunEl.getAttributeValue("spiral-color");

        Material mat = Material.matchMaterial(matName.toUpperCase().replace(" ", "_"));
        if (mat == null) throw new InvalidXMLException("Invalid material: " + matName, railgunEl);

        if (railguns.stream().anyMatch(r -> r.material() == mat)) {
          throw new InvalidXMLException("Duplicate railgun material: " + matName, railgunEl);
        }

        Color beamColor = parseColor(beamName, railgunEl, "beam-color");
        Color spiralColor = parseColor(spiralName, railgunEl, "spiral-color");

        railguns.add(new RailgunDefinition(mat, beamColor, spiralColor));
      }

      return new QuakeModule(true, railguns);
    }

    private Color parseColor(String name, Element el, String attrName) throws InvalidXMLException {
      if (name == null || name.isEmpty()) return Color.BLACK;
      try {
        return (Color) Color.class.getField(name.toUpperCase()).get(null);
      } catch (Exception e) {
        throw new InvalidXMLException("Invalid " + attrName + ": " + name, el);
      }
    }
  }
}
