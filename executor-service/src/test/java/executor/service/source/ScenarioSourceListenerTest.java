package executor.service.source;

import executor.service.model.RemoteConnectionDto;
import executor.service.model.ScenarioDto;
import executor.service.source.okhttp.OkhttpLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ScenarioSourceListenerTest {
    private OkhttpLoader loader;
    private SourceListener<ScenarioDto> sourceListener;

    @BeforeEach
    void setUp() {
        loader = mock(OkhttpLoader.class);
        RemoteConnectionDto dto = new RemoteConnectionDto("http://some/scenario/url", "http://some/proxy/url", "token");
        sourceListener = new ScenarioSourceListener(loader, dto);
    }

    @Test
    void testFetchData() {
        when(loader.loadData(any(), eq(ScenarioDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        verify(loader, times(1)).loadData(any(), eq(ScenarioDto.class));
    }

    @Test
    void testGetOne_shouldReturnPresentOptional() {
        ScenarioDto expected = new ScenarioDto();
        when(loader.loadData(any(), eq(ScenarioDto.class))).thenReturn(List.of(expected));
        sourceListener.fetchData();
        Optional<ScenarioDto> result = sourceListener.getOne();
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    void testGetOne_shouldReturnEmptyOptional() {
        when(loader.loadData(any(), eq(ScenarioDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        Optional<ScenarioDto> result = sourceListener.getOne();
        assertThat(result).isEmpty();
    }
}