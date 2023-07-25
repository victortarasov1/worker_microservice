package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProxyClient implements ProxySourcesClient {
    final List<ProxyConfigHolderDto> proxies = new ArrayList<>();
    final ProxySources mSources;

    int counter;
    private boolean infinityLooping = false;

    public ProxyClient(ProxySources sources) {
        mSources = sources;
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
        ProxyConfigHolderDto proxy = nextProxy();
        LoggerFactory.getLogger("Debug").info("Proxy: getProxy: " + (proxy == null ? null : proxy.getProxyNetworkConfig()));
        return proxy;
    }

    @Override
    public void fetchData() {
        Collections.addAll(proxies, mSources.getProxyConfigHolders());
    }

    int getProxyCount() {
        return proxies.size();
    }

    List<ProxyConfigHolderDto> getListCopy() {
        return new ArrayList<>(proxies);
    }
}
