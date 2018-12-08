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
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        if (timer != null) return;
        PlayerSpaceship that = this;
        timer = new Timer() {
            @Override
            public void update(double delta) {
                double x = that.pane.getTranslateX();
                double y = that.pane.getTranslateY();

                double newX = x + (that.mouseEvent.getX() - x) * that.speed * delta;
                double newY = y + (that.mouseEvent.getY() - y) * that.speed * delta;

                that.pane.setTranslateX(newX);
                that.pane.setTranslateY(newY);

                if (Math.abs(newX - x) < 0.01 && Math.abs(newY - y) < 0.01) {
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
    public void moveTo(double x, double y) {

    }

    @Override
    public void attack() {

    }

}
