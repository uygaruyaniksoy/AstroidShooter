package frontend.model.entities;

public interface GameObject extends Drawable {

    void move(double x, double y);
    void update(double delta);

}
