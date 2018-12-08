package frontend.model.entities;

import frontend.model.enums.AttackType;
import frontend.util.Scheduler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import frontend.util.Timer;

public class PlayerSpaceship extends AbstractSpaceship {
    private Timer timer;
    private MouseEvent mouseEvent;

    private int speed = 10;
    private double shootRate = 1;
    private Scheduler shootScheduler;

    public PlayerSpaceship(Stage stage) {
        super(stage);

        this.stage.getScene().onMouseMovedProperty().setValue(this::onMouseMoved);
        this.stage.getScene().onMouseDraggedProperty().setValue(this::onMouseDragged);
        this.stage.getScene().onMousePressedProperty().setValue(this::onMousePressed);
        this.stage.getScene().onMouseReleasedProperty().setValue(this::onMouseReleased);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        shootScheduler.stop();
        shootScheduler = null;
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        attack(AttackType.LIGHT);
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        onMouseMoved(mouseEvent);
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        if (timer != null) return;
        PlayerSpaceship that = this;
        timer = new Timer() {
            @Override
            public void update(double delta) {
                if (that.moveTo(that.mouseEvent.getX(), that.mouseEvent.getY(), delta)) {
                    that.timer.stop();
                    that.timer = null;
                }
            }
        };
        timer.start();
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
        hull.setFill(Color.PURPLE);

        Rectangle body = new Rectangle();
        body.setHeight(50);
        body.setWidth(hullRadius * 2);
        body.setY(hullRadius);
        body.setX(centerX - hullRadius);
        body.setFill(Color.PURPLE);

        this.pane.getChildren().add(axle);
        this.pane.getChildren().add(bulletLeft);
        this.pane.getChildren().add(bulletRight);
        this.pane.getChildren().add(body);
        this.pane.getChildren().add(hull);
    }

    @Override
    public boolean moveTo(double toX, double toY, double rate) {
        double fromX = this.pane.getTranslateX();
        double fromY = this.pane.getTranslateY();

        double newX = fromX + (toX - fromX) * this.speed * rate;
        double newY = fromY + (toY - fromY) * this.speed * rate;

        this.pane.setTranslateX(newX);
        this.pane.setTranslateY(newY);

        return Math.abs(newX - toX) < 0.01 && Math.abs(newY - toY) < 0.01;
    }

    @Override
    public void attack(AttackType attackType) {
        PlayerSpaceship that = this;
        shootScheduler = new Scheduler(shootRate) {
            @Override
            public void execute() {
                shootAt(that.mouseEvent.getX(), that.mouseEvent.getY(), attackType);
            }
        };
        shootScheduler.start();
    }

    private void shootAt(double x, double y, AttackType attackType) {
        System.out.println("fire");

    }
}
