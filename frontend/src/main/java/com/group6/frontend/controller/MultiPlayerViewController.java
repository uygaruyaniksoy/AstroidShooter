package com.group6.frontend.controller;

import com.group6.frontend.Main;
import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.PlayerSpaceship;
import com.group6.frontend.model.entities.RivalSpaceship;
import com.group6.frontend.model.entities.ammos.Ammunition;
import com.group6.frontend.model.entities.enemies.BossEnemy;
import com.group6.frontend.model.entities.enemies.Enemy;
import com.group6.frontend.model.entities.enemies.PassiveEnemy;
import com.group6.frontend.model.entities.webConsumer.ScoreBoardDTO;
import com.group6.frontend.model.enums.GameScreen;
import com.group6.frontend.util.Scheduler;
import com.group6.frontend.util.StringResources;
import com.group6.frontend.util.Timer;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class MultiPlayerViewController extends Timer {
    private final Stage stage;
    private Pane pane;
    private Pane healthbar;
    private Text score;
    private Text timeView;

    private PlayerSpaceship player;
    private RivalSpaceship rival;
    private BossEnemy boss;
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private double time = 60;

    private String HOST = "localhost";
    private int PORT = 8080;
    private Socket socket;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public MultiPlayerViewController(Stage stage) {
        this.stage = stage;
    }

    void startGame() {
        player = new PlayerSpaceship(stage);
        rival = new RivalSpaceship(stage);
        gameObjects.add(player);
        gameObjects.add(rival);

        this.stage.getScene().onMouseMovedProperty().setValue(this::onMouseMoved);
        this.stage.getScene().onMouseDraggedProperty().setValue(this::onMouseDragged);
        this.stage.getScene().onMousePressedProperty().setValue(this::onMousePressed);
        this.stage.getScene().onMouseReleasedProperty().setValue(mouseEvent1 -> onMouseReleased());

        this.pane = ((Pane) stage.getScene().getRoot());

        setHealthBar();
        startEnemySpawner();
        setPlayerShootingScheduler();
        initScoreTextAndTime();

        start(); // start timer so that every frame update function will be called

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (outputStream == null || inputStream == null) return;
                try {
                    sendMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 16);
        java.util.Timer timer2 = new java.util.Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (outputStream == null || inputStream == null) return;
                try {
                    receiveMessage();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 16);
    }

    private void receiveMessage() throws IOException, ClassNotFoundException {
        Pair<Double, Double> dto = (Pair<Double, Double>) inputStream.readObject();
//        System.out.println("received: " + dto);
        rival.move(dto.getKey(), dto.getValue());
//        System.out.println(""+ rival.getRootPane().getTranslateX() + " " + rival.getRootPane().getTranslateY());
    }

    private void sendMessage() throws IOException {
        Pair<Double, Double> dto = new Pair<>(player.getRootPane().getTranslateX(), player.getRootPane().getTranslateY());
//        System.out.println("sent " + dto.toString());
        outputStream.writeObject(dto);
    }

    private void initScoreTextAndTime() {
        score = new Text("Score: " + (((int) player.getScore())));
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        score.setFill(Color.DARKORCHID);
        score.setStrokeWidth(3);
        score.setStroke(Color.BLACK);
        score.setTextAlignment(TextAlignment.LEFT);

        score.translateYProperty().set(80);
        score.translateXProperty().set(20);
        pane.getChildren().add(score);

        timeView = new Text("Score: " + time);
        timeView.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        timeView.setFill(Color.DARKORCHID);
        timeView.setStrokeWidth(3);
        timeView.setStroke(Color.BLACK);
        timeView.setTextAlignment(TextAlignment.LEFT);

        timeView.translateXProperty().set(20);
        timeView.translateYProperty().set(40);
        pane.getChildren().add(timeView);
    }

    private void setPlayerShootingScheduler() {
        // set player to attack upwards every player.getShootRate() seconds.
        player.setShootScheduler(new Scheduler(player.getShootRate()) {
            @Override
            public void execute() {
                gameObjects.add(player.attack(
                ));
            }
        });
        player.getShootScheduler().start();
        rival.setShootScheduler(new Scheduler(player.getShootRate()) {
            @Override
            public void execute() {
                gameObjects.add(rival.attack(
                ));
                gameObjects.add(boss.attack(
                ));
            }
        });
        rival.getShootScheduler().start();
    }

    private void setHealthBar() {
        this.healthbar = new Pane();
        healthbar.setPrefWidth(25);
        healthbar.prefHeightProperty().bind(stage.heightProperty().divide(4));
        healthbar.translateYProperty().bind(stage.heightProperty().divide(4).multiply(3).subtract(25));
        healthbar.setTranslateX(25);

        healthbar.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        new CornerRadii(100),
                        new BorderWidths(2))));

        healthbar.setBackground(player.getHealthBar());

        this.pane.getChildren().add(healthbar);
    }

    private void onMouseReleased() {
        if (player.isAutoShooting()) return;
        player.getShootScheduler().stop();
        player.setShootScheduler(null);
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        if (player.isAutoShooting()) return;
        player.setShootScheduler(new Scheduler(player.getShootRate()) {
            @Override
            public void execute() {
                gameObjects.add(player.attack());
            }
        });
        player.getShootScheduler().start();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        player.move(mouseEvent.getX(), mouseEvent.getY());
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        player.move(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * every 0.5 seconds spawns enemies.
     * <p>
     * after 25 player kill spawner starts to spawn 1 more enemy
     * after 100 player kill spawner starts to spawn 1 more enemy
     */
    private void startEnemySpawner() {

        for (int i = 0; i < 5; i++) {
            PassiveEnemy enemy = new PassiveEnemy(stage, stage.getScene().getWidth() / 10 * 1, -50 * i - 100);
            gameObjects.add(enemy);
            enemies.add(enemy);
            enemy = new PassiveEnemy(stage, stage.getScene().getWidth() / 10 * 3, -50 * i - 100);
            gameObjects.add(enemy);
            enemies.add(enemy);
            enemy = new PassiveEnemy(stage, stage.getScene().getWidth() / 10 * 5, -50 * i - 100);
            gameObjects.add(enemy);
            enemies.add(enemy);
            enemy = new PassiveEnemy(stage, stage.getScene().getWidth() / 10 * 7, -50 * i - 100);
            gameObjects.add(enemy);
            enemies.add(enemy);
            enemy = new PassiveEnemy(stage, stage.getScene().getWidth() / 10 * 9, -50 * i - 100);
            gameObjects.add(enemy);
            enemies.add(enemy);
        }

        BossEnemy enemy = new BossEnemy(stage, stage.getScene().getWidth() / 2);
        boss = enemy;
        gameObjects.add(enemy);
        enemies.add(enemy);
    }

    /**
     * update function runs every frame.
     * <p>
     * it calculates new position of every element, checks if there is a collision among game objects and handles it,
     * <p>
     * also updates gui and checks end game.
     *
     * @param delta time in seconds since the last update function run
     */
    @Override
    public void update(double delta) {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            if (gameObject == null) continue;
            gameObject.update(delta);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject otherGameObject = gameObjects.get(j);
                if (otherGameObject != null &&
                    gameObject
                        .getRootPane()
                        .getBoundsInParent()
                        .intersects(otherGameObject.getRootPane().getBoundsInParent()) &&
                        !((gameObject instanceof Enemy && otherGameObject instanceof Ammunition && otherGameObject.getSource() instanceof Enemy) ||
                                (otherGameObject instanceof Enemy && gameObject instanceof Ammunition && gameObject.getSource() instanceof Enemy) ||
                                (gameObject instanceof RivalSpaceship && otherGameObject instanceof Ammunition && otherGameObject.getSource() instanceof PlayerSpaceship) ||
                                (gameObject instanceof PlayerSpaceship && otherGameObject instanceof Ammunition && otherGameObject.getSource() instanceof RivalSpaceship) ||
                                (gameObject instanceof PlayerSpaceship && otherGameObject instanceof RivalSpaceship) ||
                                (gameObject instanceof RivalSpaceship && otherGameObject instanceof PlayerSpaceship) ||
                                (gameObject instanceof Enemy && otherGameObject instanceof Enemy))
                ) {
                    gameObject.intersect(otherGameObject);

                    checkKill(otherGameObject, gameObject);
                    checkKill(gameObject, otherGameObject);
                }
            }
        }

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            if (gameObject != null && gameObject.isDead()) {
                gameObjects.remove(gameObject);
                if (gameObject instanceof Enemy) enemies.remove(gameObject);
                this.pane.getChildren().remove(gameObject.getRootPane());

                if (gameObject instanceof BossEnemy) {
                    endLevel();
                }
                i--;
            }
        }

        this.healthbar.setBackground(player.getHealthBar());
        this.score.setText("Score: " + (int) player.getScore());
        this.timeView.setText("Time: " + new DecimalFormat("##,##").format(time));

        time -= delta;

        if (player.isDead()) {
            finish();
        } else if (time < 0) {
            endLevel();
        }
    }

    /**
     * handle timer's timeout which is the end level.
     */
    private void endLevel() {
        MultiPlayerViewController that = this;
        this.pane.getChildren().removeIf(node -> node != that.player.getRootPane());

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/space.jpg")));
        image.fitWidthProperty().bind(stage.widthProperty());
        image.fitHeightProperty().bind(stage.heightProperty());
        pane.getChildren().add(0, image);

        enemies.clear();
        gameObjects.clear();
        gameObjects.add(player);
        player.setLevel(player.getLevel() + 1);

        player.getShootScheduler().stop();
        rival.getShootScheduler().stop();
        stop();

        Text text = new Text("Game is over. Congrats!");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        text.setFill(Color.DARKORCHID);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setStrokeWidth(3);
        text.setStroke(Color.BLACK);
        text.translateXProperty().bind(stage.widthProperty().divide(2).subtract(text.getLayoutBounds().getWidth() / 2));
        text.translateYProperty().bind(stage.heightProperty().divide(2).subtract(20));

        this.pane.getChildren().add(text);

        System.out.println("endd");

        Button mainMenu = new Button("Main Menu");
        Bounds bounds = mainMenu.getLayoutBounds();
        mainMenu.translateXProperty().bind(stage.widthProperty().subtract(bounds.getWidth()).divide(2));
        mainMenu.translateYProperty().bind(stage.heightProperty().divide(4).multiply(3));
        mainMenu.setOnMouseClicked(e -> {
            stage.setScene(Main.getScenes().get(GameScreen.MAIN_MENU));
            Main.resetGameView();
        });
        pane.getChildren().add(mainMenu);

        System.out.println("gfame");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-access-token",Main.TOKEN);

        System.out.println("sakdsa");

        ScoreBoardDTO body = new ScoreBoardDTO((int) player.getScore());
        HttpEntity<ScoreBoardDTO> request = new HttpEntity<>(body, headers);

        System.out.println("jdkzlsjsksajkl");

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://" + HOST + ":" + PORT + "/";
        restTemplate.exchange(
                resourceUrl + "scoreboard/update", HttpMethod.POST, request, Void.class);

        System.out.println("klsdaklsa");
    }

    private void checkKill(GameObject gameObject, GameObject otherGameObject) {
        if (otherGameObject.isDead() && gameObject.getSource() == player) {
            player.addScore(otherGameObject.getMaxHealth());
            player.increateKillCount();
        }
    }

    /**
     * handles player death
     * <p>
     * clears all the timers and guis and prompts to go back to main menu
     */
    private void finish() {
        player.getShootScheduler().stop();
        rival.getShootScheduler().stop();
        // stop the timer and end the game
        stop();

        Text endGameText = new Text(StringResources.getEndGameText());
        endGameText.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
        endGameText.setFill(Color.DARKORCHID);
        endGameText.setStrokeWidth(3);
        endGameText.setStroke(Color.BLACK);

        Bounds bounds = endGameText.getLayoutBounds();
        endGameText.translateXProperty().bind(stage.widthProperty().subtract(bounds.getWidth()).divide(2));
        endGameText.translateYProperty().bind(stage.heightProperty().subtract(bounds.getHeight()).divide(2));
        pane.getChildren().add(endGameText);

        Text scoreText = new Text("Score: " + (((int) player.getScore())));
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        scoreText.setFill(Color.DARKORCHID);
        scoreText.setStrokeWidth(3);
        scoreText.setStroke(Color.BLACK);

        bounds = scoreText.getLayoutBounds();
        scoreText.translateXProperty().bind(stage.widthProperty().subtract(bounds.getWidth()).divide(2));
        scoreText.translateYProperty().bind(stage.heightProperty().subtract(bounds.getHeight()).divide(1.66));
        pane.getChildren().add(scoreText);

        Button mainMenu = new Button("Main Menu");
        bounds = mainMenu.getLayoutBounds();
        mainMenu.translateXProperty().bind(stage.widthProperty().subtract(bounds.getWidth()).divide(2));
        mainMenu.translateYProperty().bind(stage.heightProperty().divide(4).multiply(3));
        mainMenu.setOnMouseClicked(e -> {
            stage.setScene(Main.getScenes().get(GameScreen.MAIN_MENU));
            Main.resetGameView();
        });
        pane.getChildren().add(mainMenu);


        this.stage.getScene().onMouseMovedProperty().setValue(null);
        this.stage.getScene().onMouseDraggedProperty().setValue(null);
        this.stage.getScene().onMousePressedProperty().setValue(null);
        this.stage.getScene().onMouseReleasedProperty().setValue(null);
    }

    void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
