package executor.service.maintenance.plugin.proxy;

import executor.service.maintenance.proxy.ProxyValidationService;
import executor.service.model.ProxyConfigHolderDto;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProxyClientWithAsyncValidation extends ProxyClient {

    private final ProxyValidationService mValidationService;
    private final List<ProxyConfigHolderDto> sourceProxies = new ArrayList<>();

    private boolean validated = false;

    public ProxyClientWithAsyncValidation(ProxySources sources, ProxyValidationService validationService) {
        super(sources);
        mValidationService = validationService;
    }

    void validate() {
        counter = 0;
        validated = false;
        proxies.clear();

        mValidationService.startValidateAsync(sourceProxies);
        ProxyConfigHolderDto proxy = mValidationService.waitForValidatedProxy();// wait for first valid proxy
        mValidationService.cancelValidate(0, TimeUnit.NANOSECONDS);
        if (proxy != null) proxies.add(proxy);
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

    @Override
    public void fetchData() {
        sourceProxies.clear();
        Collections.addAll(sourceProxies, mSources.getProxyConfigHolders());
        LoggerFactory.getLogger("Debug").info("Proxy: fetched " + sourceProxies.size() + " proxies");
        validate();
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
