package executor.service.maintenance.proxy;

import executor.service.model.ProxyConfigHolderDto;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class ProxyValidationAsyncService implements ProxyValidationService{
    private static final Logger LOGGER = LoggerFactory.getLogger("Debug");
    private static final int MAXIMUM_POOL_SIZE = 4;
    private final ProxyValidator mValidator;
    private final AtomicInteger runningTaskCounter = new AtomicInteger(0);
    private final BlockingQueue<ProxyConfigHolderDto> validatedProxiesQueue;
    private final ProxyConfigHolderDto poisonPill = new ProxyConfigHolderDto();
    private ThreadPoolExecutor executor;
    public ProxyValidationAsyncService(ProxyValidator validator) {
        mValidator = validator;
        validatedProxiesQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void startValidateAsync(List<ProxyConfigHolderDto> sourceProxies) {

        if (isRunning()) {
            throw new IllegalStateException("Cannot execute task: the task is already running.");
        }
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAXIMUM_POOL_SIZE);
        validatedProxiesQueue.clear();

        runningTaskCounter.set(sourceProxies.size());
        for (ProxyConfigHolderDto proxy : sourceProxies) {
            executor.execute(() -> {
                LOGGER.debug("Thread started. Tasks: " + runningTaskCounter);

                if (mValidator.isValid(proxy) && !Thread.currentThread().isInterrupted()) {
                    validatedProxiesQueue.add(proxy);
                } else {
                    if (runningTaskCounter.get() == 1) {
                        validatedProxiesQueue.add(poisonPill);
                    }
                }
                runningTaskCounter.decrementAndGet();
                LOGGER.debug("Thread ended. Remaining tasks: " + runningTaskCounter);
            });
        }
        executor.shutdown();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void cancelValidate(long value, TimeUnit nanoseconds) {
        if (executor == null) return;
        executor.shutdownNow();
        try {
            executor.awaitTermination(value, nanoseconds);
        } catch (InterruptedException ignored) {
        }
        runningTaskCounter.set(executor.getActiveCount());
        LOGGER.debug("Validation stopped. Active threads: " + executor.getActiveCount());
    }

    @Override
    public void cancelValidate() {
        cancelValidate(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    @Override
    public void validate(List<ProxyConfigHolderDto> sourceProxies, List<ProxyConfigHolderDto> validatedProxies) {
        startValidateAsync(sourceProxies);
        waitForValidatedProxies(validatedProxies);
    }

    @Override
    public void waitForValidatedProxies(List<ProxyConfigHolderDto> validatedProxies) {
        ProxyConfigHolderDto proxy;
        while ((proxy = waitForValidatedProxy()) != null) {
            validatedProxies.add(proxy);
        }
    }

    @Nullable
    public ProxyConfigHolderDto waitForValidatedProxy(long timeOut, @NotNull TimeUnit unit) {
        try {
            ProxyConfigHolderDto holder = validatedProxiesQueue.poll(timeOut, unit);
            return holder == poisonPill ? null : holder;
        } catch (InterruptedException e) {
            return null;
        }
    }
    @Override
    @Nullable
    public ProxyConfigHolderDto waitForValidatedProxy() {
        return waitForValidatedProxy(runningTaskCounter.get(), TimeUnit.MINUTES);
    }

    @Override
    public void drainTo(List<ProxyConfigHolderDto> validatedProxies) {
        validatedProxiesQueue.drainTo(validatedProxies);

        if (!validatedProxies.isEmpty()) {
            int lastIndex = validatedProxies.size() - 1;
            ProxyConfigHolderDto proxy = validatedProxies.get(lastIndex);
            if (proxy == poisonPill) validatedProxies.remove(lastIndex);
        }
    }

    @Override
    public boolean isRunning() {
        return runningTaskCounter.get() > 0;
    }
}
