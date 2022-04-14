package com.company;

import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

public class FadeApp {

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;

    public void init() {
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Will find friends for peanuts . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setEffect(new DropShadow());
    }

    Task<ObservableList<String>> friendTask;
    public void startLoad() {
        friendTask = new Task<>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> foundFriends =
                        FXCollections.observableArrayList();
                updateMessage("Идет поиск слов...");
                for (int i = 0; i < 30; i++) {
                    Thread.sleep(300);
                    updateProgress(i + 1, 30);
                }
                Thread.sleep(200);
                updateMessage("Все слова найдены.");

                return foundFriends;
            }
        };
        initStage = new Stage();
        showSplash(initStage, friendTask, () -> showMainStage(friendTask.valueProperty()));
        new Thread(friendTask).start();
    }

    public Stage initStage;

    public void endLoad() {
        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.1), splashLayout);
        fadeSplash.setFromValue(1.0);
        fadeSplash.setToValue(0.0);
        fadeSplash.setOnFinished(actionEvent -> initStage.hide());
        fadeSplash.play();
    }

    private void showMainStage(ReadOnlyObjectProperty<ObservableList<String>> friends) {
        //mainStage = new Stage();
        //mainStage.setTitle("My Friends");

        //final ListView<String> peopleView = new ListView<>();
        //peopleView.itemsProperty().bind(friends);

        //mainStage.setScene(new Scene(peopleView));
        //mainStage.show();
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();

                initCompletionHandler.complete();
            }  // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
        //endLoad();
    }

    public interface InitCompletionHandler {
        void complete();
    }
}
