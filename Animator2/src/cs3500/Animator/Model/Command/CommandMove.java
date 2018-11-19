package cs3500.Animator.Model.Command;

import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Model.Shape.IVector;

/**
 * This class represents the move command when the user wants to just move an IShape.
 */
public class CommandMove extends ACommand {

  private final int newX;
  private final int newY;

  /**
   * Constructor for move command class. Moves an IShape to the new X and Y coordinates starting at
   * startTime and ending at endTime.
   *
   * @param startTime The scheduled startTime for this move Command.
   * @param endTime   The scheduled endTime for this move Command.
   * @param newX      The new x coordinate that the shape will move to.
   * @param newY      The new y coordinate that the shape will move to.
   */
  public CommandMove(int startTime, int endTime, int newX, int newY) {
    super(startTime, endTime);
    this.newX = newX;
    this.newY = newY;
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
      setVelocity(new IVector[]{shape.getVectorX(), shape.getVectorY()}, new int[]{newX, newY});
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
      resetVelocity(shape.getVectorX(), shape.getVectorY());
    }
  }

  @Override
  public void doCommand(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    abstractDoCmd(new IVector[]{shape.getVectorX(), shape.getVectorY()}, new int[]{newX, newY});
  }

  @Override
  public ICommand changeStartTime(int startTime) {
    return new CommandMove(startTime, this.endTime, this.newX, this.newY);
  }

  @Override
  public String toString() {
    return "Move: Tick: " + startTime + ", X: " + this.newX + ", Y: " + this.newY;
  }
}
