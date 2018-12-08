package frontend.model.entities;

import frontend.model.entities.ammos.Ammunition;
import frontend.model.entities.ammos.Rocket;
import frontend.model.enums.AttackType;
import frontend.util.Scheduler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import frontend.util.Timer;

public class PlayerSpaceship extends AbstractGameObject implements Spaceship {
    private int speed = 10;
    private double shootRate = 0.3;
    private Scheduler shootScheduler;
    private double moveTargetX;
    private double moveTargetY;

    public PlayerSpaceship(Stage stage) {
        super(stage, 100);
    }

    @Override
    public void draw() {
        int centerX = 40;
        int centerY = 25;
        int hullRadius = 15;
        int bulletWidth = 10;
        int bulletHeight = 25;
        int bulletOffset = 30;

        Rectangle axle = new Rectangle();
        axle.setHeight(10);
        axle.setWidth(centerX * 2);
        axle.setY(centerY * 2);
        axle.setFill(Color.BLACK);

        Rectangle bulletLeft = new Rectangle();
        bulletLeft.setHeight(bulletHeight);
        bulletLeft.setWidth(bulletWidth);
        bulletLeft.setY(centerY * 2 - bulletHeight / 2.0);
        bulletLeft.setX(centerX - bulletOffset- bulletWidth / 2.0);
        bulletLeft.setFill(Color.DARKORCHID);

        Rectangle bulletRight = new Rectangle();
        bulletRight.setHeight(bulletHeight);
        bulletRight.setWidth(bulletWidth);
        bulletRight.setY(centerY * 2 - bulletHeight / 2.0);
        bulletRight.setX(centerX + bulletOffset - bulletWidth / 2.0);
        bulletRight.setFill(Color.DARKORCHID);

        Circle hull = new Circle();
        hull.setCenterX(centerX);
        hull.setCenterY(hullRadius);
        hull.setRadius(hullRadius);
        hull.setFill(Color.DARKBLUE);

        Rectangle body = new Rectangle();
        body.setHeight(50);
        body.setWidth(hullRadius * 2);
        body.setY(hullRadius);
        body.setX(centerX - hullRadius);
        body.setFill(Color.DARKBLUE);

        this.pane.getChildren().add(axle);
        this.pane.getChildren().add(bulletLeft);
        this.pane.getChildren().add(bulletRight);
        this.pane.getChildren().add(body);
        this.pane.getChildren().add(hull);
    }

    @Override
    public void move(double toX, double toY) {
        this.moveTargetX = toX;
        this.moveTargetY = toY;
    }

    @Override
    public void update(double delta) {
        double fromX = this.pane.getTranslateX();
        double fromY = this.pane.getTranslateY();

        double newX = fromX + (moveTargetX - fromX) * this.speed * delta;
        double newY = fromY + (moveTargetY - fromY) * this.speed * delta;

        this.pane.setTranslateX(newX);
        this.pane.setTranslateY(newY);
    }

    @Override
    public GameObject attack(double x, double y, AttackType attackType) {
        int centerX = 40;
        int centerY = 25;
        int bulletOffset = 30;

        int gunIndex = (int) (Math.random() * 2) * 2 - 1;

        return new Rocket(stage,
                pane.getTranslateX() + pane.getLayoutX() + centerX + bulletOffset * gunIndex,
                pane.getTranslateY() + pane.getLayoutY() + centerY * 2);
    }

    public Scheduler getShootScheduler() {
        return shootScheduler;
    }

    public void setShootScheduler(Scheduler shootScheduler) {
        this.shootScheduler = shootScheduler;
    }

    public double getShootRate() {
        return shootRate;
    }
}
