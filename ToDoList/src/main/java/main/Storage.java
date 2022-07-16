package main;
import main.model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Storage {
    private AtomicInteger currentId = new AtomicInteger();
    private ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();//потокобезопасный список дел

    public ArrayList<Task> allTasks() {
        return new ArrayList<>(tasks.values());
    }

    //добавление дела
    public int add(Task task) {
        int id = currentId.incrementAndGet();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    //получить дело
    public Task getTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        }
        return null;
    }

    //удалить дело
    public void delete(int id) {
        tasks.remove(id);
    }

    //обновить дело
    public Task update(int id, Task task) {
        if (tasks.containsKey(id)) {
            task.setId(id);
            tasks.put(id, task);
        }
        return null;
    }

    //удалить все
    public void deleteAll() {
        tasks.clear();
    }
}
