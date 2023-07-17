package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import executor.service.utl.JsonReader;

import java.util.ArrayList;
import java.util.List;

public class JsonProxySources implements ProxySources {

    final String source;
    private final List<ProxyConfigHolderDto> proxyConfigHolderDtoList = new ArrayList<>();

    public JsonProxySources() {
        this.source = "proxy_list.json";
    }

    public JsonProxySources(String resourceName) {
        this.source = resourceName;
    }

    @Override
    public List<ProxyConfigHolderDto> getProxyConfigHolders() {
        if (proxyConfigHolderDtoList.isEmpty()) parse();
        return proxyConfigHolderDtoList;
    }

    private void parse() {
        JsonModel[] arr = getJsonArray();

        for (JsonModel model : arr) {
            for (ProxyCredentialsDTO credentials : model.credential) {
                ProxyConfigHolderDto holder = new ProxyConfigHolderDto(
                        new ProxyNetworkConfigDTO(model.hostname, model.port), credentials
                );
                proxyConfigHolderDtoList.add(holder);
            }
        }
    }

    protected JsonModel[] getJsonArray() {
        return JsonReader.parseResourceToArray(source, JsonModel.class);
    }

    record JsonModel(String hostname, Integer port, List<ProxyCredentialsDTO> credential) {
    }
}
