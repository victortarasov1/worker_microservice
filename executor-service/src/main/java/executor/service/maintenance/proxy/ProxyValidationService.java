package executor.service.maintenance.proxy;

import executor.service.model.ProxyConfigHolderDto;

import javax.annotation.Nullable;
import java.util.List;


public interface ProxyValidationService {

    void startValidateAsync(List<ProxyConfigHolderDto> sourceProxies);
    void startValidateAsync(List<ProxyConfigHolderDto> sourceProxies, List<ProxyConfigHolderDto> validatedProxies);

    void cancelValidate();

    /** Starts an asynchronous proxy validation, runs until all threads have completed.
     *
     * @param sourceProxies not validated proxies
     * @param validatedProxies validated proxies
     */
    void validate(List<ProxyConfigHolderDto> sourceProxies, List<ProxyConfigHolderDto> validatedProxies);

    void waitForValidatedProxies(List<ProxyConfigHolderDto> validatedProxies);

    @Nullable
    ProxyConfigHolderDto waitForValidatedProxy();

    void drainTo(List<ProxyConfigHolderDto> validatedProxies);

    boolean isRunning();
}
