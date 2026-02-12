package tc.oc.pgm.platform.modern.modules;

import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import tc.oc.pgm.api.map.MapModule;
import tc.oc.pgm.api.map.factory.MapFactory;
import tc.oc.pgm.api.map.factory.MapModuleFactory;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.util.xml.InvalidXMLException;

public class LockMountsModule implements MapModule<LockMountsMatchModule> {

  private final boolean lockMounts;

  public LockMountsModule(boolean lockMounts) {
    this.lockMounts = lockMounts;
  }

  @Override
  public LockMountsMatchModule createMatchModule(Match match) {
    return new LockMountsMatchModule(match, lockMounts);
  }

  public static class Factory implements MapModuleFactory<LockMountsModule> {
    @Override
    public LockMountsModule parse(MapFactory factory, Logger logger, Document doc)
        throws InvalidXMLException {
      Element el = doc.getRootElement().getChild("lock-mounts");
      boolean lockMounts = el != null && !el.getTextNormalize().equalsIgnoreCase("off");
      return new LockMountsModule(lockMounts);
    }
  }
}
