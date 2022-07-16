package main.controller;

import main.model.Task;
import main.model.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoListController {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @GetMapping("/tasks/")//список всех дел
    public List<Task> list() {
        Iterable<Task> taskIterable = toDoListRepository.findAll();
        List<Task> newTasks = new ArrayList<>();
        for (Task task : taskIterable) {
            newTasks.add(task);
        }
        return newTasks;
    }

    @PostMapping("/tasks/")//добавить дело
    public int add(Task task) {
        Task newTask = toDoListRepository.save(task);
        return newTask.getId();
    }

    @GetMapping("/tasks/{id}")//получить дело по id
    public ResponseEntity get(@PathVariable int id) {
        Optional<Task> optionalTask = toDoListRepository.findById(id);
        return optionalTask.map(task ->
                new ResponseEntity(task, HttpStatus.OK)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/tasks/{id}")//удалить дело по id
    public ResponseEntity<String> delete(@PathVariable int id) {
        toDoListRepository.deleteById(id);
        //storage.delete(id);
        return new ResponseEntity<>("Task cleared", HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")//обновить дело
    public ResponseEntity<? extends Object> update(@PathVariable int id, Task newTask) {
        Optional<Task> optionalTask = toDoListRepository.findById(id);
        if (optionalTask.isPresent()) {
            newTask.setId(id);
            toDoListRepository.save(newTask);
            return new ResponseEntity<>("Task update", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/tasks/")//очистить список
    public ResponseEntity<String> deleteAllTasks() {
        toDoListRepository.deleteAll();
        return new ResponseEntity<>("Task list cleared", HttpStatus.OK);
    }
}
