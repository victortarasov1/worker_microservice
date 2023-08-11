package executor.service.source;

import executor.service.enums.AuthorizationType;
import executor.service.maintenance.proxy.ProxyValidator;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.RemoteConnectionDto;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.Request;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class ProxySourceListener implements SourceListener<ProxyConfigHolderDto> {
    private final OkhttpLoader loader;
    private final Request request;
    private final Queue<ProxyConfigHolderDto> proxies;
    private final ProxyValidator validator;

    public ProxySourceListener(OkhttpLoader loader, Queue<ProxyConfigHolderDto> proxies, RemoteConnectionDto remoteConnectionDto, ProxyValidator validator) {
        this.loader = loader;
        this.proxies = proxies;
        request = new Request.Builder().url(remoteConnectionDto.getProxyUrl())
                .delete().header(AuthorizationType.BEARER.getPrefix(), remoteConnectionDto.getToken()).build();
        this.validator = validator;
    }

    @Override
    public void fetchData() {
       loader.loadData(request, ProxyConfigHolderDto.class).forEach(proxy -> CompletableFuture.runAsync(() -> addIfValid(proxy)));
    }

    private void addIfValid(ProxyConfigHolderDto proxy) {
        if(validator.isValid(proxy)) proxies.add(proxy);
    }

    @Override
    public Optional<ProxyConfigHolderDto> getOne() {
        return Optional.ofNullable(proxies.poll());
    }
}
