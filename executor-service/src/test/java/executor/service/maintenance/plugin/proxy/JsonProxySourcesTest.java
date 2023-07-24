package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class JsonProxySourcesTest {

    @Test
    void testHolders() {
        JsonProxySources sources = new JsonProxySources();
        List<ProxyConfigHolderDto> expectedList = createTestHoldersList();
        Assertions.assertArrayEquals(expectedList.toArray(), sources.getProxyConfigHolders());
    }

    @Test
    void testHoldersIfCantRead() {
        JsonProxySources sources = new JsonProxySources("wrong.json");
        Assertions.assertThrows(RuntimeException.class, sources::getProxyConfigHolders);
    }

    @SuppressWarnings("SameParameterValue")
    private ProxyConfigHolderDto createTestHolder(String hostname, int port, String username, String password) {
        return new ProxyConfigHolderDto(
                new ProxyNetworkConfigDTO(hostname, port),
                new ProxyCredentialsDTO(username, password));
    }

    private List<ProxyConfigHolderDto> createTestHoldersList() {
        List<ProxyConfigHolderDto> list = new ArrayList<>();
        list.add(createTestHolder("localhost", 80, null, null));
        list.add(createTestHolder("localhost", 90, null, null));
        list.add(createTestHolder("localhost3", 880, null, null));
        return list;
    }

}