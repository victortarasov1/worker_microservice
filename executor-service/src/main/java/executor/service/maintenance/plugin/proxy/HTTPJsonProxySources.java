package executor.service.maintenance.plugin.proxy;

import com.fasterxml.jackson.core.JacksonException;
import com.google.common.net.HttpHeaders;
import executor.service.enums.AuthorizationType;
import executor.service.exception.okhttp.OkHttpException;
import executor.service.exception.okhttp.UnsuccessfulResponseException;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.utl.JsonReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class HTTPJsonProxySources extends JsonProxySources {

    final String token;
    private final OkHttpClient okHttpClient;

    @Autowired
    public HTTPJsonProxySources(@Value("${publisher.proxy.url}") String url,
                                @Value("${publisher.jwt.token}") String token,
                                OkHttpClient okHttpClient) {
        super(url);
        this.token = token;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public ProxyConfigHolderDto[] getProxyConfigHolders() {
        Request.Builder builder = new Request.Builder().url(source).delete();
        if (token != null) builder.addHeader(HttpHeaders.AUTHORIZATION, AuthorizationType.BEARER.getPrefix() + token);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new UnsuccessfulResponseException(response.code());
            ResponseBody body = response.body();
            if (body == null) throw new OkHttpException("Response body is empty");
            InputStream inputStream = body.byteStream();
            return JsonReader.parseToArray(inputStream, ProxyConfigHolderDto.class);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new OkHttpException(e);
        }
    }
}
