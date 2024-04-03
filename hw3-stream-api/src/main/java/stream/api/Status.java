package stream.api;

public enum Status {
    OPEN("открыта"),
    AT_WORK("в работе"),
    CLOSED("закрыта");
    private final String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
