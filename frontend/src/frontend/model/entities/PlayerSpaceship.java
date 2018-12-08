package frontend.model.entities;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import frontend.util.Timer;

public class PlayerSpaceship extends AbstractSpaceship {
    private Timer timer;
    private MouseEvent mouseEvent;


    public PlayerSpaceship(Stage stage) {
        super(stage);

        this.stage.getScene().onMouseMovedProperty().setValue(this::onMouseMoved);
        this.stage.getScene().onMouseDraggedProperty().setValue(this::onMouseMoved);
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
        this.pane.setStyle("-fx-background-color: brown");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(50);
        rectangle.setWidth(50);
        rectangle.setY(0);
        rectangle.setX(0);
        rectangle.setFill(Color.PURPLE);

        this.pane.getChildren().add(rectangle);
    }

    @Override
    public boolean moveTo(double toX, double toY, double rate) {
        double fromX = this.pane.getTranslateX();
        double fromY = this.pane.getTranslateY();

        double newX = fromX + (toX - fromX) * this.speed * rate;
        double newY = fromY + (toY - fromY) * this.speed * rate;

        this.pane.setTranslateX(newX);
        this.pane.setTranslateY(newY);

        System.out.println("Move" + rate);

        return Math.abs(newX - toX) < 0.01 && Math.abs(newY - toY) < 0.01;
    }

    @Override
    public void attack() {

    }

}
