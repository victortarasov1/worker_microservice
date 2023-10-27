package executor.service.source.okhttp.sender;

import okhttp3.Request;

public interface OkhttpSender {

    void sendData(Request request);
}
