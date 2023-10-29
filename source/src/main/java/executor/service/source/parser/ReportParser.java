package executor.service.source.parser;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.collection.queue.scenario.ScenarioReportQueueHandler;
import executor.service.model.ScenarioReport;
import executor.service.source.exception.DataParsingException;
import executor.service.source.config.Header;
import executor.service.source.model.RemoteConnection;
import executor.service.source.okhttp.AuthorizationType;
import executor.service.source.okhttp.sender.OkhttpSender;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class ReportParser implements SourceParser {
    private final RemoteConnection connection;
    private final OkhttpSender sender;
    private final ScenarioReportQueueHandler reports;
    private final ObjectMapper mapper;

    @Override
    public void parseData() {
        RequestBody body = getRequestBody();
        Request request = getRequest(body);
        sender.sendData(request);

    }

    @NotNull
    private Request getRequest(RequestBody body) {
        String header = AuthorizationType.BEARER.getPrefix() + connection.getToken();
        return new Request.Builder().url(connection.getReportUrl())
                .post(body).addHeader(Header.AUTHORIZATION.getValue(), header)
                .build();
    }

    private RequestBody getRequestBody() {
        List<ScenarioReport> reportList = reports.removeAll();
        String json = serializeReportsToJson(reportList);
        MediaType mediaType = MediaType.parse(APPLICATION_JSON_VALUE);
        return RequestBody.create(json, mediaType);
    }

    private String serializeReportsToJson(List<ScenarioReport> reportList) {
        try {
            return mapper.writeValueAsString(reportList);
        } catch (JsonProcessingException ex) {
            throw new DataParsingException(ex);
        }
    }
}
