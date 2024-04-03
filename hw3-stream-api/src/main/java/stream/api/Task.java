package stream.api;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Task {
    private int id;
    private String name;
    private Status status;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status.getStatus() +
                '}';
    }
}
