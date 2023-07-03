package executor.service.factory.difactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CachedServiceFactoryProviderTest {

    @Test
    void testGetFactory_resultNotNull() {
        DependencyInjectionFactory factory = CachedServiceFactoryProvider.getFactory();
        assertNotNull(factory);
    }

    @Test
    void testGetFactory_returnsSameInstance() {
        DependencyInjectionFactory factory1 = CachedServiceFactoryProvider.getFactory();
        DependencyInjectionFactory factory2 = CachedServiceFactoryProvider.getFactory();
        assertSame(factory1, factory2);
    }

}