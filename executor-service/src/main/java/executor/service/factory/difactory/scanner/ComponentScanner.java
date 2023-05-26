package executor.service.factory.difactory.scanner;

import java.util.List;
/**
 * An interface for scanning components within a package.
 */
public interface ComponentScanner {
    /**
     * Retrieves a list of component classes.
     * @return a list of component classes
     */
    List<Class<?>> getComponents();

}
