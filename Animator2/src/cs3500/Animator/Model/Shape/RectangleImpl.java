package cs3500.Animator.Model.Shape;


import java.util.ArrayList;
import java.util.List;
import cs3500.Animator.Model.Command.*;


/**
 * This class represents an rectangle which extends an AShape. It is an example of what type of
 * shapes can be created using out model.
 */
public class RectangleImpl extends AShape {


  /**
   * Constructs a Rectangle object with the given fields. Note: passes in ints but these are all
   * made doubles when put into the vectors to allow for better movement.
   *
   * @param x        start x
   * @param y        start y
   * @param height   start height
   * @param width    start width
   * @param r        start red
   * @param g        start green
   * @param b        start blue
   * @param commands commands to be done on shape in the form of a List
   * @param name     name of Rectangle
   */
  RectangleImpl(int x, int y, int height, int width, int r, int g, int b,
                List<ICommand> commands, String name, int startTime, int endTime) {
    super(x, y, height, width, r, g, b, commands, name, startTime, endTime);
  }

  private RectangleImpl(int x, int y, int height, int width, int r, int g, int b,
                   List<ICommand> commands, String name, int startTime,
                   int endTime, boolean isVisible) throws IllegalArgumentException {
    super(x, y, height, width, r, g, b, commands, name, startTime, endTime, isVisible);
  }

  /**
   * Returns the string "Shape" followed by the IShape's name and the type of shape it is.
   *
   * @return Returns a basic description of the the IShape including its name and the type of shape.
   */
  @Override
  public String getBasicDesc() {
    return "rectangle";
  }

  @Override
  public IShape copy() {
    return new RectangleImpl(this.getX(), this.getY(), this.getHeight(), this.getWidth(),
            this.getRed(), this.getGreen(), this.getBlue(),
            new ArrayList<ICommand>(this.getCommands()), this.getName(),
            this.getStartTime(), this.getEndTime(), this.isVisible());
  }
}
