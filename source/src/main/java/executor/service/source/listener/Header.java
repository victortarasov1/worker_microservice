package executor.service.source.listener;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum Header {
    AUTHORIZATION("Authorization");
    private final String value;
}
