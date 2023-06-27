package executor.service.factory.difactory;

import executor.service.factory.difactory.register.InstanceCreatorRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CachedServiceFactoryTest {

    private DependencyInjectionFactory factory;
    private InstanceCreatorRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = Mockito.mock(InstanceCreatorRegistry.class);
        factory = new CachedServiceFactory(registry);
    }

    @Test
    void testCreateInstance_interface_ReturnsImplementationClass() {
        Class<SomeInterface> interfaceClass = SomeInterface.class;
        SomeImpl implementation = new SomeImpl();
        Function<DependencyInjectionFactory, SomeInterface> creatorFunction = f -> implementation;
        when(registry.getCreatorFunction(interfaceClass)).thenAnswer(invocation -> creatorFunction);
        SomeInterface instance = factory.createInstance(interfaceClass);
        assertEquals(implementation, instance);
    }

    @Test
    void testCreateInstance_interface_ReturnsSameInstanceOnMultipleCalls() {
        Class<SomeInterface> interfaceClass = SomeInterface.class;
        SomeImpl implementation = new SomeImpl();
        Function<DependencyInjectionFactory, SomeInterface> creatorFunction = f -> implementation;
        when(registry.getCreatorFunction(interfaceClass)).thenAnswer(invocation -> creatorFunction);
        SomeInterface instance1 = factory.createInstance(interfaceClass);
        SomeInterface instance2 = factory.createInstance(interfaceClass);
        assertSame(instance1, instance2);
        verify(registry, times(1)).getCreatorFunction(interfaceClass);
    }


    interface SomeInterface {
    }

    static class SomeImpl implements SomeInterface {
    }
}