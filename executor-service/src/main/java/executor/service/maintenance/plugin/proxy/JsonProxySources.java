package executor.service.maintenance.plugin.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonProxySources implements ProxySources {

    private static class JsonModel {
        public String hostname;
        public Integer port;

        public List<ProxyCredentialsDTO> credential;
    }

    final private ObjectMapper mapper = new ObjectMapper();

    private final List<ProxyConfigHolderDto> proxyConfigHolderDtoList = new ArrayList<>();

    private static final String PROXY_LIST_FILENAME = "proxy_list.json";

    static InputStream getInputStream() throws IOException {

        InputStream in = JsonProxySources.class.getResourceAsStream("/" + PROXY_LIST_FILENAME);
        if (in == null) {
            throw new IOException("Error: " + PROXY_LIST_FILENAME + " file not found.");
        }
        return in;
    }

    private <T> T[] parseJsonToArray(InputStream inputStream, Class<T> tClass) throws IOException {
        return mapper.readerForArrayOf(tClass).readValue(inputStream);
    }

    public JsonProxySources() {
        parse();
    }

    @Override
    public List<ProxyConfigHolderDto> getProxyConfigHolders() {
        return proxyConfigHolderDtoList;
    }

    private void parse() {
        try (InputStream inputStream = getInputStream()) {
            JsonModel[] arr = parseJsonToArray(inputStream, JsonModel.class);

            for (JsonModel model : arr) {
                for (ProxyCredentialsDTO credentials : model.credential) {
                    ProxyConfigHolderDto holder = new ProxyConfigHolderDto(
                            new ProxyNetworkConfigDTO(model.hostname, model.port), credentials
                    );
                    proxyConfigHolderDtoList.add(holder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
