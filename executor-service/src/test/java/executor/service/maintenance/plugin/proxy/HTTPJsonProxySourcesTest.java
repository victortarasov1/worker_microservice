package executor.service.maintenance.plugin.proxy;

import com.fasterxml.jackson.core.JacksonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HTTPJsonProxySourcesTest {
    private static final String JSON_PROXY_URL = "http://13.48.123.125/proxy_list.json";


    @Test
    void testHTTPRequestAndParsing() {
        ProxySources sources = new HTTPJsonProxySources(JSON_PROXY_URL);

        Assertions.assertDoesNotThrow(() -> {
            try {
                sources.getProxyConfigHolders();
            } catch (RuntimeException e) {
                Throwable cause = e.getCause();
                if (cause instanceof JacksonException) {
                    throw cause;
                } else {
                    System.out.println("testHTTPRequestAndParsing:" + e.getMessage());
                }
            }
        });
    }
}