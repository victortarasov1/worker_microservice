package executor.service.maintenance.plugin.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testGetProxy() {
        System.out.println("==== testGetProxy =====");
        ProxySourcesClientImpl client = new ProxySourcesClientImpl(new JsonProxySources());
        Exception exception = Assertions.assertThrows(RuntimeException.class, createExecutable(client, 6));
        System.out.println("testGetProxy -> " + exception.getMessage() + "\n");
        assertEquals("No more proxies", exception.getMessage());
    }

    @Test
    void testGetProxyIfListEmpty() {
        System.out.println("==== testGetProxyIfListEmpty =====");
        ProxySourcesClientImpl client = new ProxySourcesClientImpl(ArrayList::new);
        Exception exception = Assertions.assertThrows(RuntimeException.class, createExecutable(client, 1));
        System.out.println("testGetProxyIfListEmpty -> " + exception.getMessage() + "\n");
        assertEquals("No more proxies", exception.getMessage());
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