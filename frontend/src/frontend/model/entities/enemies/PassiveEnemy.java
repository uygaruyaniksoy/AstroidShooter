package frontend.model.entities.enemies;

import frontend.model.entities.AbstractGameObject;
import frontend.model.entities.GameObject;
import frontend.model.entities.Spaceship;
import frontend.model.entities.ammos.Rocket;
import frontend.model.enums.AttackType;
import frontend.util.Scheduler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class PassiveEnemy extends AbstractGameObject implements Spaceship, AIShip {
    private int speed = -60;

    public PassiveEnemy(Stage stage, double x) {
        super(stage);
        this.pane.setTranslateX(x);
        this.pane.setTranslateY(100);
    }

    @Override
    public void draw() {
        int centerX = 40;
        int centerY = 25;
        int hullRadius = 15;
        int bulletWidth = 15;
        int bulletHeight = 15;
        int bulletOffset = 30;

        Rectangle axle = new Rectangle();
        axle.setHeight(15);
        axle.setWidth(centerX * 2);
        axle.setY(centerY * 2);
        axle.setFill(Color.BLACK);

        Rectangle bulletLeft = new Rectangle();
        bulletLeft.setHeight(bulletHeight);
        bulletLeft.setWidth(bulletWidth);
        bulletLeft.setY(centerY * 2 - bulletHeight / 2.0);
        bulletLeft.setX(centerX - bulletOffset- bulletWidth / 2.0);
        bulletLeft.setFill(Color.BLACK);
        bulletLeft.setRotate(45);

        Rectangle bulletRight = new Rectangle();
        bulletRight.setHeight(bulletHeight);
        bulletRight.setWidth(bulletWidth);
        bulletRight.setY(centerY * 2 - bulletHeight / 2.0);
        bulletRight.setX(centerX + bulletOffset - bulletWidth / 2.0);
        bulletRight.setFill(Color.BLACK);
        bulletRight.setRotate(45);

        Circle hull = new Circle();
        hull.setCenterX(centerX);
        hull.setCenterY(hullRadius);
        hull.setRadius(hullRadius);
        hull.setFill(Color.DARKRED);

        Rectangle body = new Rectangle();
        body.setHeight(50);
        body.setWidth(hullRadius * 2);
        body.setY(hullRadius);
        body.setX(centerX - hullRadius);
        body.setFill(Color.DARKRED);

        this.pane.getChildren().add(bulletLeft);
        this.pane.getChildren().add(bulletRight);
        this.pane.getChildren().add(axle);
        this.pane.getChildren().add(body);
        this.pane.getChildren().add(hull);

        this.pane.setRotate(180);
        this.pane.setScaleX(0.6);
        this.pane.setScaleY(0.6);
    }

    @Override
    public void update(double delta) {
        this.pane.setTranslateX(this.pane.getTranslateX());
        this.pane.setTranslateY(this.pane.getTranslateY() - speed * delta);
    }

    @Override
    public GameObject attack(double x, double y, AttackType attackType) {
        return null;
    }

    @Override
    public void updateAI() {

    }
}
