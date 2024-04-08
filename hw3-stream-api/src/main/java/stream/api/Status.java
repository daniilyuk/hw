package stream.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    OPEN("открыта"),
    AT_WORK("в работе"),
    CLOSED("закрыта");
    private final String status;
}
