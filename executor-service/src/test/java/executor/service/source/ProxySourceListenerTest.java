package executor.service.source;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.RemoteConnectionDto;
import executor.service.source.okhttp.OkhttpLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProxySourceListenerTest {

    private OkhttpLoader loader;
    private SourceListener<ProxyConfigHolderDto> sourceListener;

    @BeforeEach
    void setUp() {
        loader = mock(OkhttpLoader.class);
        RemoteConnectionDto dto = new RemoteConnectionDto("http://some/scenario/url", "http://some/proxy/url", "token");
        sourceListener = new ProxySourceListener(loader, dto);
    }

    @Test
    void testFetchData() {
        when(loader.loadData(any(), eq(ProxyConfigHolderDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        verify(loader, times(1)).loadData(any(), eq(ProxyConfigHolderDto.class));
    }

    @Test
    void testGetOne_shouldReturnPresentOptional() {
        ProxyConfigHolderDto expected = new ProxyConfigHolderDto();
        when(loader.loadData(any(), eq(ProxyConfigHolderDto.class))).thenReturn(List.of(expected));
        sourceListener.fetchData();
        Optional<ProxyConfigHolderDto> result = sourceListener.getOne();
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    void testGetOne_shouldReturnEmptyOptional() {
        when(loader.loadData(any(), eq(ProxyConfigHolderDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        Optional<ProxyConfigHolderDto> result = sourceListener.getOne();
        assertThat(result).isEmpty();
    }
}