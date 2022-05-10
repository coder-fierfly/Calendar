package com.company;

//TODO: тут есть импорты со звездочкой, если получится их исправить

import javafx.animation.FadeTransition;
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

    public void init() {
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(656);
        progressText = new Label("Необходимо найти слова для словаря...");
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
        showSplash(initStage, friendTask, () -> showMainStage());
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

    private void showMainStage() {
        // todo ожидание завершения поиска
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
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        //TODO: может, 338 и 113 можно вызывать откуда-то? если нет, то просто сотрите меня
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 338);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 113);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }
}
