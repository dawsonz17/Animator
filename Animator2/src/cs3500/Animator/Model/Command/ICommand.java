package cs3500.Animator.Model.Command;

import cs3500.Animator.Model.Shape.IShape;

/**
 * This interface contains commands which the user can use to affect an IShape. Using an interface
 * to represent commands allows for the user to easily add functionality and new commands to IShapes
 * without needing to change any code in IShape or any of its implementing classes. When the user
 * calls go, given any specific instance of an IShape and the current time on the tickCounter, the
 * command activates, affecting the IShape my changing its attributes in some way (or not) and then
 * returns the tickCounter time after the command has activated. Invariant: Every time you call a
 * function in ICommand, it should be with the same IShape instance every time so that the IShape
 * and ICommand are effectively linked.
 */
public interface ICommand {

  /**
   * Starts this command so that it affects the given IShape starting at the scheduled start time
   * defined within the ICommand.
   *
   * @param shape       This is the IShape that is affected by this command.
   * @param currentTick This is the current tick counter for the Animator overall. The command
   *                    should start affecting the given IShape when the currentTick equals the
   *                    scheduled start time defined within the ICommand.
   * @throws IllegalArgumentException Thrown if given a Null IShape.
   */
  void start(int currentTick, IShape shape);

  /**
   * Ends this command so that it stops affecting the given IShape. It stops at the scheduled stop
   * time defined within the ICommand.
   *
   * @param shape       This is the IShape that is affected by this command.
   * @param currentTick This is the current tick counter for the Animator overall. The command
   *                    should stop affecting the given IShape when the currentTick equals the
   *                    scheduled stop time defined within the ICommand.
   * @throws IllegalArgumentException Thrown if given a Null IShape.
   */
  void end(int currentTick, IShape shape);

  /**
   * This function determines if the given tick occurs between the start and end times of this
   * command.
   *
   * @param tick The given tick in the animation.
   * @return Returns true if the tick occurs between the start and end times of this command.
   */
  boolean isOverlapping(int tick);

  /**
   * Returns the tick in the animation on which this command starts.
   *
   * @return Returns the tick on which this command starts.
   */
  int getStartTick();

  /**
   * Returns the tick in the animation on which this command ends.
   *
   * @return Returns the tick on which this command ends.
   */
  int getEndTick();

  /**
   * This function causes a command to occur on the given IShape, all at once, without dealing with
   * waiting to call the method start or stopping the command with the method end. This function is
   * especially useful for bypassing the anything to do with ticks if you are only iterating through
   * Commands.
   *
   * @param shape This is the IShape that is affected by this command.
   */
  void doCommand(IShape shape);

  ICommand changeStartTime(int startTick);

  @Override
  String toString();
}
