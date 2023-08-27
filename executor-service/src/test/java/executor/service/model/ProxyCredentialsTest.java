package executor.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyCredentialsTest {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private ProxyCredentials mActual;
    private ProxyCredentials mExpected;

    @BeforeEach
    public void setUp() {
        mActual = new ProxyCredentials(USER, PASSWORD);
        mExpected = new ProxyCredentials(USER, PASSWORD);
    }

    @Test
    public void testEquality() {
        assertThat(mActual).isEqualTo(mExpected);
    }

    @Test
    public void testNotEquality() {
        ProxyCredentials expected = new ProxyCredentials(USER, "");
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
        ProxyCredentials pg = new ProxyCredentials();
        assertThat(pg.getUsername()).isNull();
        assertThat(pg.getPassword()).isNull();
    }

    @Test
    public void testSetterWithNull() {
        ProxyCredentials pg = new ProxyCredentials(USER, PASSWORD);
        pg.setPassword(null);
        assertThat(pg.getPassword()).isNull();
    }

    @Test
    public void testSetters() {
        ProxyCredentials pg = new ProxyCredentials();
        pg.setUsername(USER);
        pg.setPassword(PASSWORD);

        assertThat(USER).isEqualTo(pg.getUsername());
        assertThat(PASSWORD).isEqualTo(pg.getPassword());
    }
}