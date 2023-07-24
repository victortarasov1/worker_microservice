package executor.service.maintenance.plugin.proxy;

import executor.service.maintenance.proxy.ProxyValidationService;
import executor.service.model.ProxyConfigHolderDto;

import java.util.ArrayList;
import java.util.List;

public class ProxyClientWithAsyncValidation extends ProxyClient {
    private final ProxyValidationService mValidationService;
    private final List<ProxyConfigHolderDto> sourceProxies;

    private boolean validated = false;

    public ProxyClientWithAsyncValidation(ProxySources sources, ProxyValidationService validationService) {
        super(new ArrayList<>());
        sourceProxies = sources.getProxyConfigHolders();
        mValidationService = validationService;
        validate();
    }

    void validate() {
        counter = 0;
        validated = false;
        proxies.clear();

        mValidationService.startValidateAsync(sourceProxies, null);
        ProxyConfigHolderDto proxy = mValidationService.waitForValidatedProxy();// wait for first valid proxy
        if (proxy != null) sourceProxies.add(proxy);
    }

    private void drainProxies() {
        if (!validated) {
            if (mValidationService.isRunning()) {
                mValidationService.drainTo(proxies);
            } else {
                mValidationService.drainTo(proxies);
                validated = true;
            }
        }
    }

    ProxyConfigHolderDto nextProxy() {
        drainProxies();
        return super.nextProxy();
    }

    @Override
    int getProxyCount() {
        drainProxies();
        return super.getProxyCount();
    }

    List<ProxyConfigHolderDto> getListCopy() {
        drainProxies();
        return super.getListCopy();
    }

    boolean isValidated() {
        drainProxies();
        return validated;
    }
}
