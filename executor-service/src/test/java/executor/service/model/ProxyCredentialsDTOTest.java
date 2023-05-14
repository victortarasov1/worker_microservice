package executor.service.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyCredentialsDTOTest {

    private final String user = "user";
    private final String password = "password";

    @Test
    public void testEquality() {
        ProxyCredentialsDTO actual = new ProxyCredentialsDTO(user, password);
        ProxyCredentialsDTO expected = new ProxyCredentialsDTO(user, password);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testNotEquality() {
        ProxyCredentialsDTO actual = new ProxyCredentialsDTO(user, password);
        ProxyCredentialsDTO expected = new ProxyCredentialsDTO(user, "");
        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    public void testConstructorsAndGetters() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO(user, password);

        assertThat(user).isEqualTo(pg.getUsername());
        assertThat(password).isEqualTo(pg.getPassword());

    }

    @Test
    public void testEqualsWithSameObject() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO(user, password);
        assertThat(pg).isEqualTo(pg);
    }

    @Test
    public void testEmptyConstructor() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO();
        assertThat(pg.getUsername()).isNull();
        assertThat(pg.getPassword()).isNull();
    }

    @Test
    public void testSetterWithNull() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO(user, password);
        pg.setPassword(null);
        assertThat(pg.getPassword()).isNull();
    }

    @Test
    public void testSetters() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO();
        pg.setUsername(user);
        pg.setPassword(password);

        assertThat(user).isEqualTo(pg.getUsername());
        assertThat(password).isEqualTo(pg.getPassword());
    }
}