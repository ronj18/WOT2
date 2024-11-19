package com.wot2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class WorkoutManager {
    private Map<LocalDate, String> workouts;

    public WorkoutManager() {
        workouts = loadWorkouts();
    }

    // Load workouts from the JSON file
    private Map<LocalDate, String> loadWorkouts() {
        File file = new File(getDataFilePath());
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<LocalDate, String>>() {
            }.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // Save workouts to the JSON file
    private void saveWorkouts() {
        try (FileWriter writer = new FileWriter(getDataFilePath())) {
            new Gson().toJson(workouts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the data file path (can be overridden in tests)
    protected String getDataFilePath() {
        return "workouts.json";
    }

    // Add or update a workout
    public void addWorkout(LocalDate date, String details) {
        workouts.put(date, details);
        saveWorkouts();
    }

    // Remove a workout
    public void removeWorkout(LocalDate date) {
        workouts.remove(date);
        saveWorkouts();
    }

    // Get workout details for a specific date
    public String getWorkout(LocalDate date) {
        return workouts.getOrDefault(date, "");
    }

    // Get all workouts
    public Map<LocalDate, String> getAllWorkouts() {
        return workouts;
    }
}
