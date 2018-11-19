package cs3500.Animator.Model.Command;


import cs3500.Animator.Model.Shape.IShape;

/**
 * This class represents the move command when the user wants to just move an IShape.
 */
public class CommandStay extends ACommand {

  /**
   * Constructor for stay command class. Makes a shape remain where it is starting at startTime and
   * ending at endTime.
   *
   * @param startTime The scheduled startTime for this move Command.
   * @param endTime   The scheduled endTime for this move Command.
   */
  public CommandStay(int startTime, int endTime) {
    super(startTime, endTime);
  }

  @Override
  public void start(int currentTick, IShape shape) {
    // does nothing
  }

  @Override
  public void end(int currentTick, IShape shape) {
    // does nothing
  }

  @Override
  public void doCommand(IShape shape) {
    // does nothing
  }

  @Override
  public ICommand changeStartTime(int startTime) {
    return new CommandStay(startTime, this.endTime);
  }

  @Override
  public String toString() {
    return "Stay put: Tick: " + startTime;
  }
}
