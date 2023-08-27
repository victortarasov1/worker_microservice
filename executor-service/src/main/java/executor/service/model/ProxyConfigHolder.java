package executor.service.model;

import java.util.Objects;

public class ProxyConfigHolder {

    private ProxyNetworkConfig proxyNetworkConfig;

    private ProxyCredentials proxyCredentials;


    public ProxyConfigHolder() {
    }

    public ProxyConfigHolder(ProxyNetworkConfig proxyNetworkConfig, ProxyCredentials proxyCredentials) {
        this.proxyNetworkConfig = proxyNetworkConfig;
        this.proxyCredentials = proxyCredentials;
    }

    public ProxyNetworkConfig getProxyNetworkConfig() {
        return proxyNetworkConfig;
    }

    public void setProxyNetworkConfig(ProxyNetworkConfig proxyNetworkConfig) {
        this.proxyNetworkConfig = proxyNetworkConfig;
    }

    public ProxyCredentials getProxyCredentials() {
        return proxyCredentials;
    }

    public void setProxyCredentials(ProxyCredentials proxyCredentials) {
        this.proxyCredentials = proxyCredentials;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProxyConfigHolder proxyConfigHolder)) return false;
        return Objects.equals(proxyCredentials, proxyConfigHolder.proxyCredentials)
                && Objects.equals(proxyNetworkConfig, proxyConfigHolder.proxyNetworkConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proxyCredentials, proxyNetworkConfig);
    }

    @Override
    public String toString() {
        return "ProxyConfigHolderDto{" +
                "proxyNetworkConfig=" + proxyNetworkConfig +
                ", proxyCredentials=" + proxyCredentials +
                '}';
    }
}