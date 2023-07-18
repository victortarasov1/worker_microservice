package executor.service.maintenance.proxy;

import executor.service.maintenance.plugin.proxy.ProxySources;
import executor.service.maintenance.plugin.proxy.ProxySourcesClientDecorator;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;

import java.util.ArrayList;
import java.util.Arrays;
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

    // huge test, for local testing, recommended to set root level="INFO" and logger name="Debug" level="DEBUG" on logback.xml
    @Test
    @Disabled
    void testRealProxyValidation() throws InterruptedException {
        ProxyValidationAsyncService validationService = new ProxyValidationAsyncService(new HTTPProxyValidator());
        TestProxySources testProxySources = new TestProxySources();

        final boolean testServiceOnly = true;
        //noinspection ConstantValue
        if (testServiceOnly) {
            long startTime = System.currentTimeMillis();
            List<ProxyConfigHolderDto> vp = new ArrayList<>();
            validationService.validate(testProxySources.mProxies,  vp);
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Valid proxies: " + vp.size());
            vp.forEach(proxy -> {
                System.out.println("Validated proxy: " + proxy.getProxyNetworkConfig().getHostname() + ":" + proxy.getProxyNetworkConfig().getPort());
            });
            System.out.println("Validation ended: " + !validationService.isRunning() +  ", take time " + endTime + " ms");
        } else {
            ProxySourcesClientDecorator clientDecorator = new ProxySourcesClientDecorator(testProxySources, validationService);
            while (true) {
                ProxyConfigHolderDto proxy = clientDecorator.getProxy();
                if (proxy == null) {
                    if (validationService.isRunning()) {
                        //noinspection BusyWait
                        Thread.sleep(500);
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Validated proxy: " + proxy.getProxyNetworkConfig().getHostname() + ":" + proxy.getProxyNetworkConfig().getPort());
                }
            }

        }
        Assertions.assertFalse(validationService.isRunning());
    }

    private static class TestProxySources implements ProxySources {

        private final List<ProxyConfigHolderDto> mProxies = new ArrayList<>();

        public TestProxySources() {
            String proxies = """
                            20.210.113.32:80
                            190.61.88.147:8080
                            207.2.120.61:80
                            20.24.43.214:80
                            103.167.134.31:80
                            133.18.234.13:80
                            13.95.173.197:80
                            117.251.103.186:8080
                    """;
            Arrays.stream(proxies.split("\n")).forEach(address -> TestProxySources.this.addProxy(address.trim()));
        }

        private void addProxy(String address) {
            mProxies.add(createHolder(address));
        }

        @Override
        public List<ProxyConfigHolderDto> getProxyConfigHolders() {
            return mProxies;
        }
    }
}