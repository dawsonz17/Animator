package cs3500.Animator.Model.AnimatorModel;

import java.util.List;

import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Model.Command.ICommand;


/**
 * This Interface represents the interface for the model of the easy animator.
 */
public interface IAnimatorModel {

  /**
   * Makes a chart showing the state of all of the IShapes in this IAnimatorModel. This function
   * allows you to stop before the animation is supposed to finish so that you can see the
   * animation while it is working. The animation runs for duration iterations.
   * @param duration Defines the number of iterations for the animation to go through.
   * @return Returns a chart the shows all the motions made on the shapes of the animation.
   */
  //String playAnimation(int duration);


  /**
   * Return the list of shapes the model contains.
   *
   * @return List of shapes stored by model.
   */
  List<IShape> getShapes();

  /**
   * Moves each shape by one tick for the commands that are currently being run.
   */
  void updateTick();


  /**
   * Top left X of a view.
   *
   * @return integer value of the top left x value of canvas.
   */
  int getLeftX();

  /**
   * Top left Y of a view.
   *
   * @return integer value of the top left y value of canvas.
   */
  int getUpperY();

  /**
   * Width of the view.
   *
   * @return integer value of width of canvas.
   */
  int getWidth();

  /**
   * Height of the view.
   *
   * @return integer value of height of canvas.
   */
  int getHeight();

  /**
   * Total length of the view in ticks.
   *
   * @return integer value of length of canvas.
   */
  int getAnimationLength();

  /**
   * Resets the counter of ticks to 0.
   */
  void resetAnimation();


  /**
   * Adds the given command to the given shape.
   *
   * @param shape   IShape who will have the command added to its list of commands.
   * @param command ICommand that will be added to the IShapes list of commands.
   */
  void addCommand(IShape shape, ICommand command);

  /**
   * Removes the given command to the given shape.
   *
   * @param shape   IShape who will have the command removed from its list of commands.
   * @param command ICommand that will be removed from the IShapes list of commands.
   */
  void removeCommand(IShape shape, ICommand command);


  /**
   * Adds a shape with the given name and type.
   * @param name Name of shape.
   * @param type Type of shape.
   */
  void addShape(String name, String type);

  /**
   * Removes the given shape from the list of shapes in the model.
   *
   * @param shape The shape that will be removed from the model.
   */
  void removeShape(IShape shape);


  void addKeyFrame(String name, int t, int x, int y, int w, int h, int r, int g, int b);

  void removeKeyFrame(String name, int t);
}
