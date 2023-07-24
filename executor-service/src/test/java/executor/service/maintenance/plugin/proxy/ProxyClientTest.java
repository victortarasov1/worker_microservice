package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNull;

class ProxyClientTest {

    private static Executable createExecutable(ProxyClient client, int steps) {
        return () -> IntStream.range(0, steps).mapToObj(i -> client.getProxy()).forEach(System.out::println);
    }

    @Test
    void testGetProxy() throws Throwable {
        System.out.println("==== testGetProxy =====");
        ProxyClient client = new ProxyClient(new JsonProxySources());
        client.fetchData();
        createExecutable(client, client.getProxyCount()).execute();
        assertNull(client.getProxy());
    }

    @Test
    void testGetProxyIfListEmpty() {
        System.out.println("==== testGetProxyIfListEmpty =====");
        ProxyClient client = new ProxyClient(() -> new ProxyConfigHolderDto[0]);
        client.fetchData();
        assertNull(client.getProxy());
    }

    @Test
    void testGetProxyWithInfinityLoop() {
        System.out.println("==== testGetProxyWithInfinityLoop =====");
        ProxyClient client = new ProxyClient(new JsonProxySources());
        client.fetchData();
        client.setInfinityLooping(true);
        Assertions.assertDoesNotThrow(createExecutable(client, 10));
        System.out.println();
    }
}