package executor.service.maintenance.proxy;

import executor.service.model.ProxyConfigHolderDto;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;


public interface ProxyValidationService {

    void startValidateAsync(List<ProxyConfigHolderDto> sourceProxies);

    void cancelValidate(long value, TimeUnit nanoseconds);

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
