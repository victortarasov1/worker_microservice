package executor.service.maintenance.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonProxySources implements ProxySources {

    final private ObjectMapper mapper = new ObjectMapper();

    private final List<ProxyConfigHolderDto> proxyConfigHolderDtoList = new ArrayList<>();

    static final String PROXY_CREDENTIALS_FILENAME = "ProxyCredentials.json";
    static final String PROXY_NETWORK_FILENAME = "ProxyNetwork.json";

    static InputStream getInputStream(String fileName) throws IOException {
        InputStream in = JsonProxySources.class.getResourceAsStream("/" + fileName);
        if (in == null) {
            throw new IOException("Error: " + fileName + " file not found.");
        }
        return in;
    }

    private <T> T[] parseJsonToArray(String fileName, Class<T> tClass) throws IOException {
        try (InputStream inputStream = getInputStream(fileName)) {
            return mapper.readerForArrayOf(tClass).readValue(inputStream);
        }
    }

    public int getSize() {
        return proxyConfigHolderDtoList.size();
    }

    public JsonProxySources() {
        init();
    }

    @Override
    public List<ProxyConfigHolderDto> getProxyConfigHolders() {
        return proxyConfigHolderDtoList;
    }

    private void init() {

        ProxyCredentialsDTO[] credentialsDTOS;
        ProxyNetworkConfigDTO[] proxyNetworkConfigDTOs;

        try {
            credentialsDTOS = parseJsonToArray(PROXY_CREDENTIALS_FILENAME, ProxyCredentialsDTO.class);
            proxyNetworkConfigDTOs = parseJsonToArray(PROXY_NETWORK_FILENAME, ProxyNetworkConfigDTO.class);

            for (int i = 0; i < credentialsDTOS.length; i++) {
                ProxyConfigHolderDto holder = new ProxyConfigHolderDto(proxyNetworkConfigDTOs[i], credentialsDTOS[i]);
                proxyConfigHolderDtoList.add(holder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
