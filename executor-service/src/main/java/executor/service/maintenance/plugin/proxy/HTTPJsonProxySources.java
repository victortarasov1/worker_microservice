package executor.service.maintenance.plugin.proxy;

import executor.service.utl.JsonReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

@Component
public class HTTPJsonProxySources extends JsonProxySources {

    public HTTPJsonProxySources(String url) {
        super(url);
    }

    @Autowired
    public HTTPJsonProxySources() {
        super("http://13.48.123.125/proxy_list.json"); //todo temporary url
    }

    @Override
    protected JsonModel[] getJsonArray() {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(source).build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            int code = response.code();
            if (code != HttpURLConnection.HTTP_OK) throw new RuntimeException("Failed get json with proxy. Response code is: " + code);
            InputStream inputStream = response.body().byteStream();
            return JsonReader.parseToArray(inputStream, JsonModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
