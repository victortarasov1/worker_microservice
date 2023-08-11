package executor.service.source;

import executor.service.enums.AuthorizationType;
import executor.service.model.RemoteConnectionDto;
import executor.service.model.ScenarioDto;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.Request;

import java.util.Optional;
import java.util.Queue;

public class ScenarioSourceListener implements SourceListener<ScenarioDto> {

    private final OkhttpLoader loader;
    private final Queue<ScenarioDto> scenarios;
    private final Request request;

    public ScenarioSourceListener(OkhttpLoader loader, Queue<ScenarioDto> scenarios, RemoteConnectionDto remoteConnectionDto) {
        this.loader = loader;
        this.scenarios = scenarios;
        request = new Request.Builder().url(remoteConnectionDto.getScenarioUrl())
                .delete().addHeader(AuthorizationType.BEARER.getPrefix(), remoteConnectionDto.getToken()).build();
    }

    @Override
    public void fetchData() {
        scenarios.addAll(loader.loadData(request, ScenarioDto.class));
    }

    @Override
    public Optional<ScenarioDto> getOne() {
        return Optional.ofNullable(scenarios.poll());
    }
}
