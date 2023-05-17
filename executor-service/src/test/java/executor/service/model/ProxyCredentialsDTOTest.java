package executor.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyCredentialsDTOTest {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private ProxyCredentialsDTO mActual;
    private ProxyCredentialsDTO mExpected;

    @BeforeEach
    public void setUp() {
        mActual = new ProxyCredentialsDTO(USER, PASSWORD);
        mExpected = new ProxyCredentialsDTO(USER, PASSWORD);
    }

    @Test
    public void testEquality() {
        assertThat(mActual).isEqualTo(mExpected);
    }

    @Test
    public void testNotEquality() {
        ProxyCredentialsDTO expected = new ProxyCredentialsDTO(USER, "");
        assertThat(mActual).isNotEqualTo(expected);
    }

    @Test
    public void testConstructorsAndGetters() {
        assertThat(USER).isEqualTo(mActual.getUsername());
        assertThat(PASSWORD).isEqualTo(mActual.getPassword());

    }

    @Test
    public void testEqualsWithSameObject() {
        assertThat(mActual).isEqualTo(mActual);
    }

    @Test
    public void testEmptyConstructor() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO();
        assertThat(pg.getUsername()).isNull();
        assertThat(pg.getPassword()).isNull();
    }

    @Test
    public void testSetterWithNull() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO(USER, PASSWORD);
        pg.setPassword(null);
        assertThat(pg.getPassword()).isNull();
    }

    @Test
    public void testSetters() {
        ProxyCredentialsDTO pg = new ProxyCredentialsDTO();
        pg.setUsername(USER);
        pg.setPassword(PASSWORD);

        assertThat(USER).isEqualTo(pg.getUsername());
        assertThat(PASSWORD).isEqualTo(pg.getPassword());
    }
}