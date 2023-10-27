package executor.service.source.okhttp.sender;

import executor.service.source.exception.okhttp.CallException;
import executor.service.source.exception.okhttp.UnsuccessfulResponseException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OkHttpSenderImpl implements OkhttpSender {
    private final OkHttpClient client;
    @Override
    public void sendData(Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new UnsuccessfulResponseException(response.code());
        } catch (IOException ex) {
            throw new CallException(ex);
        }
    }
}
