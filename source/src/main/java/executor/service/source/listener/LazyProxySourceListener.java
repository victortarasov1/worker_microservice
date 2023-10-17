package executor.service.source.listener;

import executor.service.source.model.RemoteConnection;
import executor.service.source.okhttp.AuthorizationType;
import executor.service.model.ProxyConfigHolder;
import executor.service.collection.queue.proxy.ProxySourceQueueHandler;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.Request;
import org.springframework.stereotype.Component;

@Component
public class LazyProxySourceListener implements SourceListener {
    private final OkhttpLoader loader;
    private final Request request;
    private final ProxySourceQueueHandler proxies;

    public LazyProxySourceListener(OkhttpLoader loader, RemoteConnection remoteConnection, ProxySourceQueueHandler proxies) {
        this.loader = loader;
        this.proxies = proxies;
        request = new Request.Builder().url(remoteConnection.getProxyUrl())
                .delete().header(Header.AUTHORIZATION.getValue(), AuthorizationType.BEARER.getPrefix() + remoteConnection.getToken()).build();
    }

    @Override
    public void fetchData() {
        if(proxies.getSize() == 0) proxies.addAll(loader.loadData(request, ProxyConfigHolder.class));
    }
}
