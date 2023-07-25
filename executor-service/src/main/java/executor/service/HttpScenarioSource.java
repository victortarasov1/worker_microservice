package executor.service;

import com.google.common.net.HttpHeaders;
import executor.service.enums.AuthorizationType;
import executor.service.exception.okhttp.OkHttpException;
import executor.service.exception.okhttp.UnsuccessfulResponseException;
import executor.service.model.ScenarioDto;
import executor.service.utl.JsonReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

@Service
@Primary
public class HttpScenarioSource implements ScenarioSource {
    private final String scenariosUrl;
    private final String token;
    private final OkHttpClient httpClient;

    public HttpScenarioSource(@Value("${publisher.scenarios.url}") String scenariosUrl,
                              @Value("${publisher.server.token}") String token, OkHttpClient okHttpClient) {
        if (StringUtils.isBlank(scenariosUrl))  {
            throw new InvalidParameterException("Scenarios url is invalid");
        }

        if (StringUtils.isBlank(token))  {
            throw new InvalidParameterException("Token is invalid");
        }

        this.scenariosUrl = scenariosUrl;
        this.token = token;
        this.httpClient = okHttpClient;
    }

    @Override
    public List<ScenarioDto> getScenarios() {
        Response response = makeRequest();
        ResponseBody responseBody = validateResponse(response);
        ScenarioDto[] scenarioDtos = convertResponseBodyToScenarios(responseBody);
        responseBody.close();

        return Arrays.asList(scenarioDtos);
    }

    private Response makeRequest() {
        Request request = new Request.Builder()
                .url(scenariosUrl)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, AuthorizationType.BEARER.getPrefix() + token)
                .build();

        try {
            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseBody validateResponse(Response response) {
        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw new UnsuccessfulResponseException(response.code());
        }

        if (response.body() == null) {
            throw new OkHttpException("The response body from " + scenariosUrl + " was invalid");
        }

        return response.body();
    }

    private ScenarioDto[] convertResponseBodyToScenarios(ResponseBody responseBody) {
        try {
            return JsonReader.parseToArray(responseBody.byteStream(), ScenarioDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
