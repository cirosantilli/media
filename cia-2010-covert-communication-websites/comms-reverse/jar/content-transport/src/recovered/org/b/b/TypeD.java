/* slides.jar — org/b/b/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.b;

import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

class TypeD {
    TypeD() {
    }

    public boolean a__Z(Properties properties) {
        boolean bl = false;
        if (properties != null) {
            PropertyConfigurator.configure((Properties)properties);
            bl = true;
        }
        if (!bl) {
            BasicConfigurator.configure();
            Logger logger = Logger.getRootLogger();
            logger.removeAllAppenders();
            logger.setLevel(Level.OFF);
        }
        return bl;
    }
}
