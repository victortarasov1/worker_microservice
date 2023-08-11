package executor.service.source;

import java.util.Optional;

public interface SourceListener<T> {
    void fetchData();

    Optional<T> getOne();
}
