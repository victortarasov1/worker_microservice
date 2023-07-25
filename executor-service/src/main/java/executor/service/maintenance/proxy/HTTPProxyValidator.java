package executor.service.maintenance.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Component
public class HTTPProxyValidator implements ProxyValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger("Debug");
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 10000;
    private static final String ENDPOINT = "http://httpbin.org/ip"; // "http://ipinfo.io/" http://api.proxychecker.co/;

    private static void setAuthHeader(HttpURLConnection connection, ProxyCredentialsDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (username == null || password == null) return;
        final String authHeader = new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
        connection.setRequestProperty("Proxy-Authorization", "Basic " + authHeader);
    }

    @Override
    public boolean isValid(ProxyConfigHolderDto holder) {
        boolean result = false;
        ProxyNetworkConfigDTO config = holder.getProxyNetworkConfig();

        long startTime = System.currentTimeMillis();
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress(config.getHostname(), config.getPort()));

            validate(proxy, holder.getProxyCredentials());
            result = true;
        } catch (Exception e) {
            LOGGER.error("Proxy " + config.getHostname() + ":" + config.getPort() + ". Validation error: " + e.getMessage());
        }

        LOGGER.debug("Proxy " + config.getHostname() + ":" + config.getPort() + (result ? "" : " not") + " validated. " +
                "Time cost " + (System.currentTimeMillis() - startTime) + " ms");
        LOGGER.debug("==============");

        return result;
    }

    private void validate(Proxy proxy, ProxyCredentialsDTO proxyCredentials) throws IOException {
        String proxyAddress = proxy.address().toString();

        long connectStartTime = System.currentTimeMillis();
        HttpURLConnection connection = connect(proxy, proxyCredentials);
        LOGGER.debug("Proxy " + proxyAddress + " Connect time cost: " + (System.currentTimeMillis() - connectStartTime));
        int responseCode = connection.getResponseCode();
        LOGGER.debug("Response code: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Response code is: " + responseCode);
        }
        long readStartTime = System.currentTimeMillis();
        LOGGER.debug("Proxy. Site response: " + getResponse(connection));
        LOGGER.debug("Proxy " + proxyAddress + " - Read time cost: " + (System.currentTimeMillis() - readStartTime));
    }

    private HttpURLConnection connect(Proxy proxy, ProxyCredentialsDTO proxyCredentials) throws IOException {

        HttpURLConnection connection;

        connection = (HttpURLConnection) new URL(ENDPOINT).openConnection(proxy);
        setAuthHeader(connection, proxyCredentials);

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.connect();
        return connection;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

}
