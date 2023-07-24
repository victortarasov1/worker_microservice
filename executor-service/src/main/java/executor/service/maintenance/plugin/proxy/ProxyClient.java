package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ProxyClient implements ProxySourcesClient {
    final List<ProxyConfigHolderDto> proxies;

    int counter;
    private boolean infinityLooping = false;

    public ProxyClient(ProxySources sources) {
        proxies = sources.getProxyConfigHolders();
    }

    ProxyClient(List<ProxyConfigHolderDto> proxies) {
        this.proxies = proxies;
    }

    public void setInfinityLooping(boolean value) {
        //todo Is it need this option?
        infinityLooping = value;
    }

    ProxyConfigHolderDto nextProxy() {
        if (counter == proxies.size()) {
            if (infinityLooping && !proxies.isEmpty()) {
                counter = 0;
            } else {
                return null;
            }
        }
        return proxies.get(counter++);
    }

    @Nullable
    @Override
    public ProxyConfigHolderDto getProxy() {
        return nextProxy();
    }

    int getProxyCount() {
        return proxies.size();
    }

    List<ProxyConfigHolderDto> getListCopy() {
        return new ArrayList<>(proxies);
    }
}
