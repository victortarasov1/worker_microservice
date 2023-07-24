package executor.service.maintenance.plugin.proxy;

import com.fasterxml.jackson.core.JacksonException;
import executor.service.exception.okhttp.OkHttpException;
import executor.service.exception.okhttp.UnsuccessfulResponseException;
import executor.service.model.ProxyConfigHolderDto;
import okhttp3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HTTPJsonProxySourcesTest {
    final private InputStream bodyStream = getStream();
    OkHttpClient okHttpClient;
    Call call;
    Response response;
    ResponseBody responseBody;

    @BeforeEach
    public void setUp() {
        okHttpClient = Mockito.mock(OkHttpClient.class);
        call = mock(Call.class);
        response = mock(Response.class);
        responseBody = mock(ResponseBody.class);
    }

    @Test
    void testGet_SuccessfulResponse() throws IOException {
        testFetchData(true, responseBody, bodyStream);
    }

    @Test
    void testFetchData_unSuccessfulResponse() {
        Assertions.assertThrowsExactly(UnsuccessfulResponseException.class, () -> testFetchData(false, responseBody, bodyStream));
    }

    @Test
    public void testFetchData_emptyResponseBody() {
        String actualMessage = Assertions.assertThrowsExactly(OkHttpException.class,
                () -> testFetchData(true, null, bodyStream)).getMessage();
        Assertions.assertEquals("Response body is empty", actualMessage);
    }

    @Test
    public void testFetchData_DataParsing() throws IOException {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> testFetchData(true, responseBody, new ByteArrayInputStream(new byte[0])));

        Assertions.assertInstanceOf(JacksonException.class, exception.getCause());
    }

    private void testFetchData(boolean value, ResponseBody body, InputStream stream) throws IOException {
        ProxyConfigHolderDto[] expected = new ProxyConfigHolderDto[]{new ProxyConfigHolderDto(), new ProxyConfigHolderDto()};

        ProxySources sources = new HTTPJsonProxySources("http://example.com/", null, okHttpClient);

        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);

        when(call.execute()).thenReturn(response);
        when(response.isSuccessful()).thenReturn(value);

        when(response.body()).thenReturn(body);
        doNothing().when(response).close();
        if (body != null) when(body.byteStream()).thenReturn(stream);

        Assertions.assertArrayEquals(sources.getProxyConfigHolders(), expected);
    }

    private InputStream getStream() {
        String js = "[{\"proxyNetworkConfig\":null,\"proxyCredentials\":null},{\"proxyNetworkConfig\":null,\"proxyCredentials\":null}]";
        return new ByteArrayInputStream(js.getBytes());
    }
}