package executor.service.factory.difactory.register;

import executor.service.annotation.Component;
import executor.service.annotation.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ComponentRegistrarTest {

    private InstanceRegistrar componentRegistrar;

    @BeforeEach
    void setUp() {
        componentRegistrar = new ComponentRegistrar("123f");
    }

    @Test
    public void testSetNextRegistrar() throws NoSuchFieldException {
        InstanceRegistrar newRegistrar = new ConfigRegistrar();
        componentRegistrar.setNextRegister(newRegistrar);
        Field field = componentRegistrar.getClass().getDeclaredField("nextRegister");
        Assertions.assertNotNull(field);
    }

    @Test
    public void testRegisterShouldTransferResponsibility() {
        InstanceRegistrar newRegistrar = Mockito.mock(InstanceRegistrar.class);
        componentRegistrar.setNextRegister(newRegistrar);
        Mockito.doNothing().when(newRegistrar).register(ConfigAnnotatedClass.class);
        componentRegistrar.register(ConfigAnnotatedClass.class);
        Mockito.verify(newRegistrar, times(1)).register(ConfigAnnotatedClass.class);
    }

    @Test
    public void testRegisterShouldInvoke() {
        InstanceRegistrar componentRegistrarMock = Mockito.mock(ComponentRegistrar.class);
        componentRegistrarMock.register(ComponentAnnotatedClass.class);
        Mockito.verify(componentRegistrarMock, times(1)).register(ComponentAnnotatedClass.class);
    }

    @Component
    static class ComponentAnnotatedClass {
    }

    @Config
    static class ConfigAnnotatedClass {
    }

}
