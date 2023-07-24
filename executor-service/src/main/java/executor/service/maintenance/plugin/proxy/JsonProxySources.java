package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.utl.JsonReader;

public class JsonProxySources implements ProxySources {
    final String source;

    public JsonProxySources() {
        this.source = "proxy_list.json";
    }

    public JsonProxySources(String resourceName) {
        this.source = resourceName;
    }

    @Override
    public ProxyConfigHolderDto[] getProxyConfigHolders() {
        return JsonReader.parseResourceToArray(source, ProxyConfigHolderDto.class);
    }
}
