package com.wot2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutManagerTest {

    private WorkoutManager workoutManager;
    private static final String TEST_DATA_FILE = "test_workouts.json";

    @BeforeEach
    void setUp() {
        // Use a separate test file to avoid interfering with actual data
        workoutManager = new WorkoutManager() {
            @Override
            protected String getDataFilePath() {
                return TEST_DATA_FILE;
            }
        };
    }

    @AfterEach
    void tearDown() {
        // Clean up the test data file after each test
        File file = new File(TEST_DATA_FILE);
        if (file.exists()) {
            assertTrue(file.delete(), "Test data file should be deleted after test.");
        }
    }

    @Test
    void testAddAndGetWorkout() {
        LocalDate date = LocalDate.of(2024, 11, 19);
        String details = "Morning run for 5km";

        workoutManager.addWorkout(date, details);
        String retrieved = workoutManager.getWorkout(date);

        assertEquals(details, retrieved, "Workout details should match the added data.");
    }

    @Test
    void testRemoveWorkout() {
        LocalDate date = LocalDate.of(2024, 11, 19);
        String details = "Morning run for 5km";

        workoutManager.addWorkout(date, details);
        workoutManager.removeWorkout(date);

        String retrieved = workoutManager.getWorkout(date);
        assertEquals("", retrieved, "Workout should be removed successfully.");
    }

    @Test
    void testGetAllWorkouts() {
        LocalDate date1 = LocalDate.of(2024, 11, 19);
        LocalDate date2 = LocalDate.of(2024, 11, 20);
        workoutManager.addWorkout(date1, "Workout 1");
        workoutManager.addWorkout(date2, "Workout 2");

        Map<LocalDate, String> allWorkouts = workoutManager.getAllWorkouts();

        assertEquals(2, allWorkouts.size(), "Should return all added workouts.");
        assertEquals("Workout 1", allWorkouts.get(date1), "First workout should match.");
        assertEquals("Workout 2", allWorkouts.get(date2), "Second workout should match.");
    }

    @Test
    void testOverwriteWorkout() {
        LocalDate date = LocalDate.of(2024, 11, 19);
        workoutManager.addWorkout(date, "Initial workout");
        workoutManager.addWorkout(date, "Updated workout");

        String retrieved = workoutManager.getWorkout(date);
        assertEquals("Updated workout", retrieved, "Workout should be overwritten.");
    }
}
