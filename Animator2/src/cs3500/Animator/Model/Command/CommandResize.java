package cs3500.Animator.Model.Command;

import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Model.Shape.IVector;


/**
 * This class represents the resize command when the user wants to just resize an IShape.
 */
public class CommandResize extends ACommand {

  private final int newWidth;
  private final int newHeight;

  /**
   * Constructor for the resize command class. Allows a user to resize a given IShape to the given
   * newWidth and newHeight values, starting at startTime and ending at endTime.
   *
   * @param startTime The scheduled startTime for this resize Command.
   * @param endTime   The scheduled endTime for this resize Command.
   * @param newWidth  The width of the IShape after the resizing.
   * @param newHeight The height of the IShape after the resizing.
   * @throws IllegalArgumentException Thrown if given an invalid RGB value.
   */
  public CommandResize(int startTime, int endTime, int newWidth, int newHeight)
          throws IllegalArgumentException {
    super(startTime, endTime);
    if (newWidth < 0 || newHeight < 0) {
      throw new IllegalArgumentException("newWidth and newHeight must be non-negative integers");
    }
    this.newWidth = newWidth;
    this.newHeight = newHeight;
  }

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
  @Override
  public void start(int currentTick, IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    if (currentTick == this.startTime) {
      setVelocity(new IVector[]{shape.getVectorW(), shape.getVectorH()},
              new int[]{newWidth, newHeight});
    }
  }

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
  @Override
  public void end(int currentTick, IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    if (currentTick == this.endTime) {
      resetVelocity(shape.getVectorW(), shape.getVectorH());
    }
  }

  @Override
  public void doCommand(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    abstractDoCmd(new IVector[]{shape.getVectorW(), shape.getVectorH()},
            new int[]{newWidth, newHeight});
  }

  @Override
  public ICommand changeStartTime(int startTime) {
    return new CommandResize(startTime, this.endTime, this.newWidth, this.newHeight);
  }

  @Override
  public String toString() {
    return "Resize: Tick: " + startTime + ", W: " + this.newWidth + ", H: " + this.newHeight;
  }
}

