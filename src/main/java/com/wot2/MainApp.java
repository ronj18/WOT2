package com.wot2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {
    private WorkoutManager workoutManager;
    private TextArea workoutDetails;
    private DatePicker datePicker;

    @Override
    public void start(Stage primaryStage) {
        workoutManager = new WorkoutManager();

        // Create UI elements
        datePicker = new DatePicker(LocalDate.now());
        workoutDetails = new TextArea();
        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");

        // Event Handlers
        datePicker.setOnAction(event -> loadWorkout());
        saveButton.setOnAction(event -> saveWorkout());
        deleteButton.setOnAction(event -> deleteWorkout());

        // Layout
        BorderPane root = new BorderPane();
        root.setTop(datePicker);
        root.setCenter(workoutDetails);
        root.setBottom(new ToolBar(saveButton, deleteButton));

        // Scene and Stage setup
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Workout Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial workout
        loadWorkout();
    }

    private void loadWorkout() {
        LocalDate selectedDate = datePicker.getValue();
        String details = workoutManager.getWorkout(selectedDate);
        workoutDetails.setText(details);
    }

    private void saveWorkout() {
        LocalDate selectedDate = datePicker.getValue();
        String details = workoutDetails.getText();
        workoutManager.addWorkout(selectedDate, details);
    }

    private void deleteWorkout() {
        LocalDate selectedDate = datePicker.getValue();
        workoutManager.removeWorkout(selectedDate);
        workoutDetails.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
