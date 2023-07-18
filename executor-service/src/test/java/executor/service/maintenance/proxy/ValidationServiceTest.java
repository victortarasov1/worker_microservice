package executor.service.maintenance.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ValidationServiceTest {

    private static ProxyConfigHolderDto createHolder(String proxy) {
        String[] arr = proxy.split(":");
        return new ProxyConfigHolderDto(
                new ProxyNetworkConfigDTO(arr[0], Integer.parseInt(arr[1])),
                new ProxyCredentialsDTO());
    }

    @Test
    void testProxyValidation() {
        ProxyValidator validator = Mockito.mock(ProxyValidator.class);
        ProxyValidationAsyncService service = new ProxyValidationAsyncService(validator);

        ProxyConfigHolderDto proxy1 = createHolder("localhost:8080");
        ProxyConfigHolderDto proxy2 = createHolder("localhost:8081");

        List<ProxyConfigHolderDto> sourceProxies = List.of(proxy1, proxy2);
        List<ProxyConfigHolderDto> validatedProxies = new ArrayList<>();

        when(validator.isValid(proxy1)).thenReturn(true);
        doAnswer(new AnswersWithDelay(500, new Returns(false))).when(validator).isValid(proxy2);

        service.startValidateAsync(sourceProxies);
        Assertions.assertTrue(service.isRunning());

        service.waitForValidatedProxies(validatedProxies);

        verify(validator, times(sourceProxies.size())).isValid(Mockito.any(ProxyConfigHolderDto.class));

        Assertions.assertEquals(List.of(proxy1), validatedProxies);

        List<ProxyConfigHolderDto> drainedProxies = new ArrayList<>();
        service.drainTo(drainedProxies);
        Assertions.assertTrue(drainedProxies.isEmpty());

        Assertions.assertFalse(service.isRunning());
    }
}