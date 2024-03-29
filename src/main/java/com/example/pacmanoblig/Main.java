package com.example.pacmanoblig;
//Imports
import com.example.pacmanoblig.Ghosts.Ghost;
import com.example.pacmanoblig.UI.Lives;
import com.example.pacmanoblig.UI.Top;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    BorderPane borderPane;
    GameMap map;
    Top top = new Top();

    @Override
    public void start(Stage primaryStage) throws Exception {
        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        map = GameMap.getMap();
        borderPane.setTop(top);
        primaryStage.setResizable(false);
        borderPane.setCenter(map.createMap());
        Scene scene = new Scene(borderPane, Color.BLACK);
        scene.setOnKeyPressed(this::actionEvents);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        int targetFps = 80;
        long interval = (long) (1e9 / targetFps);

        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                long elapsedNanos = now - lastTime;
                if (elapsedNanos < interval) {
                    return;
                }
                double frameTime = elapsedNanos / 1e9;
                if (Lives.lives == 0) {
                    Platform.exit();
                }
                update(frameTime);
                lastTime = now;
            }

        };
        timer.start();
    }

    private void update(double frameTime) {
        map.getPlayer().update();
        top.getS().checkScore();
        top.getL().checkLivesLeft();
        Ghost.getAllInstances().forEach(Ghost::update);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void actionEvents(KeyEvent e) {
        map.getPlayer().handleKeyEvent(e);
    }

}