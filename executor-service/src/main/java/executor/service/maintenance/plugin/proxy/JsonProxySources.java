package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import executor.service.utl.Json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonProxySources implements ProxySources {

    private static final String PROXY_LIST_FILENAME = "proxy_list.json";

    private final List<ProxyConfigHolderDto> proxyConfigHolderDtoList = new ArrayList<>();

    public JsonProxySources() {
        parse();
    }

    static InputStream getInputStream() {
        return JsonProxySources.class.getResourceAsStream("/" + PROXY_LIST_FILENAME);
    }

    @Override
    public List<ProxyConfigHolderDto> getProxyConfigHolders() {
        return proxyConfigHolderDtoList;
    }

    private void parse() {

        JsonModel[] arr = Json.parseToArray(getInputStream(), JsonModel.class);

        for (JsonModel model : arr) {
            for (ProxyCredentialsDTO credentials : model.credential) {
                ProxyConfigHolderDto holder = new ProxyConfigHolderDto(
                        new ProxyNetworkConfigDTO(model.hostname, model.port), credentials
                );
                proxyConfigHolderDtoList.add(holder);
            }
        }

    }

    private static class JsonModel {
        public String hostname;
        public Integer port;

        public List<ProxyCredentialsDTO> credential;
    }
}
