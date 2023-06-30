package executor.service.factory.difactory.register;

import executor.service.exception.register.DuplicateRegistrationException;
import executor.service.exception.register.UnregisteredClassException;
import executor.service.factory.difactory.DependencyInjectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ServiceCreatorRegistryTest {

    private DependencyInjectionFactory diFactory;

    @BeforeEach
    void setUp() {
        diFactory = Mockito.mock(DependencyInjectionFactory.class);
    }

    @Test
    void testGetCreatorFunction() {
        Function<DependencyInjectionFactory, ?> creator = factory -> new SomeImpl();
        ServiceCreatorRegistry.register(SomeInterface.class, creator);
        ServiceCreatorRegistry registry = new ServiceCreatorRegistry();
        Function<DependencyInjectionFactory, ?> creatorFunction = registry.getCreatorFunction(SomeInterface.class);
        Object expectedResult = creator.apply(diFactory);
        Object result = creatorFunction.apply(diFactory);
        assertEquals(expectedResult, result);
        assertSame(creator, creatorFunction);

    }

    @Test
    void testGetCreatorFunction_ThrowsUnregisteredClassException() {
        ServiceCreatorRegistry registry = new ServiceCreatorRegistry();
        assertThatThrownBy(() -> registry.getCreatorFunction(UnregisteredInterface.class)).isInstanceOf(UnregisteredClassException.class);
    }

    @Test
    void testRegister_ThrowsDuplicateRegistrationException() {
        Function<DependencyInjectionFactory, ?> creator = factory -> new SomeImpl();
        ServiceCreatorRegistry.register(SomeOtherInterface.class, creator);
        assertThatThrownBy(() -> ServiceCreatorRegistry.register(SomeOtherInterface.class, creator))
                .isInstanceOf(DuplicateRegistrationException.class);
    }


    interface SomeInterface {
    }

    interface SomeOtherInterface {
    }
    interface UnregisteredInterface {

    }

    record SomeImpl() implements SomeInterface, SomeOtherInterface {
    }

}