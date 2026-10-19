package com.fitness.todoapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Cumpără lapte");
        assertEquals("Cumpără lapte", task.getTitle());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testTaskCompletionToggle() {
        Task task = new Task("Alergare");
        task.setCompleted(true);
        assertTrue(task.isCompleted());

        task.setCompleted(false);
        assertFalse(task.isCompleted());
    }

    @Test
    public void testTaskTitleChange() {
        Task task = new Task("Muncă");
        task.setTitle("Proiect Android");
        assertEquals("Proiect Android", task.getTitle());
    }

    @Test
    public void testGsonSerializationAndDeserialization() {
        Gson gson = new Gson();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Citit 30 min"));
        Task completedTask = new Task("Antrenament");
        completedTask.setCompleted(true);
        tasks.add(completedTask);

        String json = gson.toJson(tasks);
        assertNotNull(json);

        Type type = new TypeToken<List<Task>>() {}.getType();
        List<Task> deserializedTasks = gson.fromJson(json, type);

        assertNotNull(deserializedTasks);
        assertEquals(2, deserializedTasks.size());
        assertEquals("Citit 30 min", deserializedTasks.get(0).getTitle());
        assertFalse(deserializedTasks.get(0).isCompleted());
        assertEquals("Antrenament", deserializedTasks.get(1).getTitle());
        assertTrue(deserializedTasks.get(1).isCompleted());
    }
}
