package executor.service.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RemoteConnectionTest {

    private static final String URL1 = "url1";
    private static final String PROXY1 = "proxy1";
    private static final String TOKEN1 = "token1";
    private static final String URL2 = "url2";
    private static final String PROXY2 = "proxy2";
    private static final String TOKEN2 = "token2";
    private static final String TEST_SCENARIO_URL = "testScenarioUrl";
    private static final String TEST_PROXY_URL = "testProxyUrl";
    private static final String TEST_TOKEN = "testToken";

    @Test
    public void testDefaultConstructor() {
        RemoteConnection remoteConnection = new RemoteConnection();
        assertThat(remoteConnection.getScenarioUrl()).isNull();
        assertThat(remoteConnection.getProxyUrl()).isNull();
        assertThat(remoteConnection.getToken()).isNull();
    }

    @Test
    public void testParameterizedConstructor() {
        RemoteConnection remoteConnection = new RemoteConnection(URL1, PROXY1, TOKEN1);

        assertThat(remoteConnection.getScenarioUrl()).isEqualTo(URL1);
        assertThat(remoteConnection.getProxyUrl()).isEqualTo(PROXY1);
        assertThat(remoteConnection.getToken()).isEqualTo(TOKEN1);
    }

    @Test
    public void testScenarioUrlGetterAndSetter() {
        RemoteConnection remoteConnection = new RemoteConnection();
        remoteConnection.setScenarioUrl(TEST_SCENARIO_URL);
        assertThat(remoteConnection.getScenarioUrl()).isEqualTo(TEST_SCENARIO_URL);
    }

    @Test
    public void testProxyUrlGetterAndSetter() {
        RemoteConnection remoteConnection = new RemoteConnection();
        remoteConnection.setProxyUrl(TEST_PROXY_URL);
        assertThat(remoteConnection.getProxyUrl()).isEqualTo(TEST_PROXY_URL);
    }

    @Test
    public void testTokenGetterAndSetter() {
        RemoteConnection remoteConnection = new RemoteConnection();
        remoteConnection.setToken(TEST_TOKEN);
        assertThat(remoteConnection.getToken()).isEqualTo(TEST_TOKEN);
    }

    @Test
    public void testEqualsWithNull() {
        RemoteConnection connection1 = new RemoteConnection(URL1, PROXY1, TOKEN1);
        assertThat(connection1).isNotEqualTo(null);
    }

    @Test
    public void testEqualsWithDifferentType() {
        RemoteConnection connection1 = new RemoteConnection(URL1, PROXY1, TOKEN1);
        String differentType = "string";
        assertThat(connection1).isNotEqualTo(differentType);
    }

    @Test
    public void testEqualsWithItself() {
        RemoteConnection connection1 = new RemoteConnection(URL1, PROXY1, TOKEN1);
        assertThat(connection1).isEqualTo(connection1);
    }

    @Test
    public void testEqualsAndHashCodeForEqualConnections() {
        RemoteConnection connection1 = new RemoteConnection(URL1, PROXY1, TOKEN1);
        RemoteConnection connection2 = new RemoteConnection(URL1, PROXY1, TOKEN1);
        assertThat(connection1).isEqualTo(connection2);
        assertThat(connection1.hashCode()).isEqualTo(connection2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeForDifferentConnections() {
        RemoteConnection connection1 = new RemoteConnection(URL1, PROXY1, TOKEN1);
        RemoteConnection connection3 = new RemoteConnection(URL2, PROXY2, TOKEN2);
        assertThat(connection1).isNotEqualTo(connection3);
        assertThat(connection1.hashCode()).isNotEqualTo(connection3.hashCode());
    }

    @Test
    public void testToString() {
        RemoteConnection connection = new RemoteConnection(URL1, PROXY1, TOKEN1);
        String expectedToString = "RemoteConnection{scenarioUrl='" + URL1 + "', proxyUrl='" + PROXY1 + "', token='" + TOKEN1 + "'}";
        assertThat(connection.toString()).isEqualTo(expectedToString);
    }
}