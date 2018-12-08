package frontend.model.entities;

public interface GameObject extends Drawable {

    public boolean moveTo(double x, double y, double rate);

}
