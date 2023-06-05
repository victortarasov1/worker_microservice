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
        Assertions.assertArrayEquals(expectedList.toArray(), sources.getProxyConfigHolders().toArray());
    }

    @Test
    void testHoldersIfCantRead() {
        JsonProxySources sources = new JsonProxySources("wrong.json");
        Assertions.assertThrows(RuntimeException.class, sources::getProxyConfigHolders);
    }

    private ProxyConfigHolderDto createTestHolder(String hostname, int port, String username, String password) {
        return new ProxyConfigHolderDto(
                new ProxyNetworkConfigDTO(hostname, port),
                new ProxyCredentialsDTO(username, password));
    }

    private List<ProxyConfigHolderDto> createTestHoldersList() {
        List<ProxyConfigHolderDto> list = new ArrayList<>();
        list.add(createTestHolder("host1", 8080, "Username", "password"));
        list.add(createTestHolder("host1", 8080, "notValid", "password2"));
        list.add(createTestHolder("host2", 8081, "Username1", "password2"));
        list.add(createTestHolder("host3", 8080, "", ""));
        return list;
    }

}