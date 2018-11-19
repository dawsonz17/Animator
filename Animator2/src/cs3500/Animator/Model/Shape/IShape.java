package cs3500.Animator.Model.Shape;

import cs3500.Animator.Model.Command.ICommand;

import java.util.List;

/**
 * This interface represents a shape. Shapes are stored in an animationModel int the form of a List
 * and shapes store the commands that act upon them in the form of a List as well. Shapes store
 * vectors that represent their current state of a certain metric and the rate of change for that
 * metric for a given command. A shape has all its commands done to it by looping through the
 * commands.
 */
public interface IShape {

  /**
   * This method stops all commands scheduled to stop at this time.
   *
   * @param tickCounter The current global time. Used in the commands to determine if they are
   *                    scheduled to stop at this tickCounter value.
   */
  void stopCommands(int tickCounter);

  /**
   * This method activates all commands scheduled to start at this time.
   *
   * @param tickCounter The current global time. Used in the commands to determine if they are
   *                    scheduled to start at this tickCounter value.
   */
  void activateCommands(int tickCounter);

  /**
   * Returns the string "Shape" followed by the IShape's name and the type of shape it is.
   *
   * @return Returns a basic description of the the IShape including its name and the type of shape.
   */
  String getBasicDesc();

  /**
   * Changes the visibility of the shape from either visible to invisible or visible to invisible.
   */
  void flipVisibility();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorX();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorY();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorW();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorH();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorR();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorG();

  /**
   * Returns the vector for this shape.
   *
   * @return Vector stored in this Shape.
   */
  IVector getVectorB();

  int getX();

  int getY();

  int getWidth();

  int getHeight();

  int getRed();

  int getGreen();

  int getBlue();


  /**
   * Is the shape visible.
   *
   * @return Boolean representing visibility.
   */
  boolean isVisible();

  /**
   * Get name of shape.
   *
   * @return String name of shape
   */
  String getName();

  /**
   * Gets commands of the shape.
   *
   * @return List of ICommands from this shape.
   */
  List<ICommand> getCommands();

  int getStartTime();

  int getEndTime();


  /**
   * Adds the given Command to the list of Commands.
   *
   * @param command Command that will be added to the list of commands.
   */
  void addCommand(ICommand command);

  /**
   * Remove the given Command from the list of commands.
   *
   * @param command Command that will be removed from the list.
   */
  void removeCommand(ICommand command);

  /**
   * Makes a shallow copy of the shape.
   *
   * @return The shape that is the result of copying this shape.
   */
  IShape copy();


  void addKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b);

  void removeKeyFrame(int t);

  void modifyKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b);

  void changeStartTime(int t);
  void changeEndTime(int t);

  @Override
  String toString();


  // add get Commands function that returns list of commands with no ChangeVisibility Commands???
}
