package executor.service.source;

import executor.service.enums.AuthorizationType;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.RemoteConnectionDto;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.Request;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
@Component
public class ProxySourceListener implements SourceListener<ProxyConfigHolderDto> {
    private final OkhttpLoader loader;
    private final Request request;
    private final Queue<ProxyConfigHolderDto> proxies;

    public ProxySourceListener(OkhttpLoader loader, RemoteConnectionDto remoteConnectionDto) {
        this.loader = loader;
        this.proxies = new ConcurrentLinkedQueue<>();
        request = new Request.Builder().url(remoteConnectionDto.getProxyUrl())
                .delete().header(AUTHORIZATION, AuthorizationType.BEARER.getPrefix() + remoteConnectionDto.getToken()).build();
    }

    @Override
    public void fetchData() {
       proxies.addAll(loader.loadData(request, ProxyConfigHolderDto.class));
    }

    @Override
    public Optional<ProxyConfigHolderDto> getOne() {
        return Optional.ofNullable(proxies.poll());
    }
}
