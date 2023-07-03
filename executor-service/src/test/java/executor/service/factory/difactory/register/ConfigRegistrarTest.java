package executor.service.factory.difactory.register;

import executor.service.annotation.Component;
import executor.service.annotation.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ConfigRegistrarTest {

    private InstanceRegistrar configRegistrar;


    @BeforeEach
    void setUp() {
        configRegistrar = new ConfigRegistrar();
    }

    @Test
    public void testSetNextRegistrar() throws NoSuchFieldException {
        InstanceRegistrar newRegistrar = Mockito.mock(InstanceRegistrar.class);
        configRegistrar.setNextRegister(newRegistrar);
        Field field = configRegistrar.getClass().getDeclaredField("nextRegister");
        Assertions.assertNotNull(field);
    }

    @Test
    public void testRegisterShouldInvoke() {
        InstanceRegistrar configRegistrarMock = Mockito.mock(ConfigRegistrar.class);
        configRegistrarMock.register(ConfigAnnotatedClass.class);
        Mockito.verify(configRegistrarMock, times(1)).register(ConfigAnnotatedClass.class);
    }

    @Test
    public void testRegisterShouldTransferResponsibility() {
        InstanceRegistrar newRegistrar = Mockito.mock(InstanceRegistrar.class);
        configRegistrar.setNextRegister(newRegistrar);
        Mockito.doNothing().when(newRegistrar).register(ComponentAnnotatedClass.class);
        configRegistrar.register(ComponentAnnotatedClass.class);
        Mockito.verify(newRegistrar, times(1)).register(ComponentAnnotatedClass.class);
    }

    @Component
    static class ComponentAnnotatedClass {
    }
    @Config
    static class ConfigAnnotatedClass{
    }
}
