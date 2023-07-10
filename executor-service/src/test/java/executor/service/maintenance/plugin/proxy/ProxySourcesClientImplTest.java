package executor.service.maintenance.plugin.proxy;

import executor.service.exception.NoMoreProxiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProxySourcesClientImplTest {

    private static ProxyValidator createValidator() {
        return holder -> {
            boolean valid = !holder.getProxyCredentials().getUsername().equals("notValid"); //for testing purposes
            if (!valid) System.err.println("Proxy: " + holder + " not valid");
            return valid;
        };
    }

    private static Executable createExecutable(ProxySourcesClientImpl client, int steps) {
        return () -> IntStream.range(0, steps).mapToObj(i -> client.getProxy()).forEach(System.out::println);
    }

    @Test
    void testGetProxy() throws Throwable {
        System.out.println("==== testGetProxy =====");
        ProxySourcesClientImpl client = new ProxySourcesClientImpl(new JsonProxySources());
        createExecutable(client, client.getProxyCount()).execute();
        assertNull(client.getProxy());
    }

    @Test
    void testGetProxyIfListEmpty() {
        System.out.println("==== testGetProxyIfListEmpty =====");
        ProxySourcesClientImpl client = new ProxySourcesClientImpl(ArrayList::new);
        assertNull(client.getProxy());
    }

    @Test
    void testGetProxyWithValidator() {
        System.out.println("==== testGetProxyWithValidator =====");
        ProxySourcesClientImpl client = new ProxySourcesClientImpl(new JsonProxySources(), createValidator());
        Assertions.assertDoesNotThrow(createExecutable(client, 3));
        System.out.println();
    }

    @Test
    void testGetProxyWithInfinityLoop() {
        System.out.println("==== testGetProxyWithInfinityLoop =====");
        ProxySourcesClientImpl client = new ProxySourcesClientImpl(new JsonProxySources(), createValidator());
        client.setInfinityLooping(true);
        Assertions.assertDoesNotThrow(createExecutable(client, 10));
        System.out.println();
    }
}