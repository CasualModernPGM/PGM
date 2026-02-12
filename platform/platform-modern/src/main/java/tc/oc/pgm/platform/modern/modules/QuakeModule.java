package tc.oc.pgm.platform.modern.modules;

import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import tc.oc.pgm.api.map.MapModule;
import tc.oc.pgm.api.map.factory.MapFactory;
import tc.oc.pgm.api.map.factory.MapModuleFactory;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.util.xml.InvalidXMLException;

public class QuakeModule implements MapModule<QuakeMatchModule> {

  private final boolean enabled;

  public QuakeModule(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public QuakeMatchModule createMatchModule(Match match) {
    return new QuakeMatchModule(match, enabled);
  }

  public static class Factory implements MapModuleFactory<QuakeModule> {
    @Override
    public QuakeModule parse(MapFactory factory, Logger logger, Document doc)
        throws InvalidXMLException {
      Element el = doc.getRootElement().getChild("quake");
      boolean enabled = el != null;
      return new QuakeModule(enabled);
    }
  }
}
