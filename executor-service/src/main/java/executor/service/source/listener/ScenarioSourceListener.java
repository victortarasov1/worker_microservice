package executor.service.source.listener;

import executor.service.source.okhttp.AuthorizationType;
import executor.service.model.RemoteConnectionDto;
import executor.service.model.ScenarioDto;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.Request;
import org.springframework.stereotype.Component;



import static com.google.common.net.HttpHeaders.AUTHORIZATION;
@Component
public class ScenarioSourceListener implements SourceListener {

    private final OkhttpLoader loader;
    private final ScenarioSourceQueueHandler scenarios;
    private final Request request;

    public ScenarioSourceListener(OkhttpLoader loader, RemoteConnectionDto remoteConnectionDto, ScenarioSourceQueueHandler scenarios) {
        this.loader = loader;
        this.scenarios = scenarios;
        request = new Request.Builder().url(remoteConnectionDto.getScenarioUrl())
                .delete().addHeader(AUTHORIZATION, AuthorizationType.BEARER.getPrefix() + remoteConnectionDto.getToken()).build();
    }

    @Override
    public void fetchData() {
        scenarios.addAll(loader.loadData(request, ScenarioDto.class));
    }

}
