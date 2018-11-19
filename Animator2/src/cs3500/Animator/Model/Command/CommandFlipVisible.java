package cs3500.Animator.Model.Command;

import cs3500.Animator.Model.Shape.IShape;


/**
 * CommandFlipVisible represents a class that changes the visibility of a shape.
 */
public class CommandFlipVisible implements ICommand {

  private final int changeTick;

  /**
   * Constructor for the command.
   *
   * @param changeTick The tick that the visibility will change at.
   */
  public CommandFlipVisible(int changeTick) {
    this.changeTick = changeTick;
  }

  @Override
  public void start(int currentTick, IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    if (currentTick == this.changeTick) {
      shape.flipVisibility();
    }
  }

  @Override
  public void end(int currentTick, IShape shape) {
    // does nothing...
  }

  @Override
  public boolean isOverlapping(int tick) {
    // cannot overlap because this takes 0 time.
    return false;
  }

  @Override
  public int getStartTick() {
    return this.changeTick;
  }

  @Override
  public int getEndTick() {
    return this.changeTick;
  }

  @Override
  public void doCommand(IShape shape) {
    this.start(this.changeTick, shape);
  }


  @Override
  public ICommand changeStartTime(int startTime) {
    return new CommandFlipVisible(startTime);
  }

  @Override
  public String toString() {
    return "Flip Visibility @ tick: " + this.changeTick;
  }
}
