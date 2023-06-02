package executor.service.maintenance.plugin.proxy;

import executor.service.exception.NoMoreProxiesException;
import executor.service.model.ProxyConfigHolderDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private final ProxyValidator mValidator;
    private final List<ProxyConfigHolderDto> proxyConfigHolders;
    private Iterator<ProxyConfigHolderDto> mIterator;
    private boolean infinityLooping = false;

    public void setInfinityLooping(boolean value) {
        //todo Is it need this option?
        infinityLooping = value;
    }

    public ProxySourcesClientImpl(ProxySources sources) {
        this(sources, null);
    }

    public ProxySourcesClientImpl(ProxySources sources, ProxyValidator validator) {
        proxyConfigHolders = sources.getProxyConfigHolders();
        mValidator = validator;
        mIterator = proxyConfigHolders.iterator();
    }

    private ProxyConfigHolderDto nextHolder() {
        if (!mIterator.hasNext()) {
            if (proxyConfigHolders.isEmpty() || !infinityLooping) throw new NoMoreProxiesException();
            mIterator = proxyConfigHolders.iterator();
        }
        return mIterator.next();
    }

    @Override
    public ProxyConfigHolderDto getProxy() {
        while (true) {
            ProxyConfigHolderDto holder = nextHolder();
            if (mValidator != null && !mValidator.isValid(holder)) {
                mIterator.remove();
                continue;
            }
            return holder;
        }
    }

    public List<ProxyConfigHolderDto> getListCopy(){
        return new ArrayList<>(proxyConfigHolders);
    }
}
