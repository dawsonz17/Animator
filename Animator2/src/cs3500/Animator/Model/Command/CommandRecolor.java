package cs3500.Animator.Model.Command;

import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Model.Shape.IVector;
import cs3500.Animator.Util.UtilityFunctions;


/**
 * This class represents the recolor command when the user wants to just recolor an IShape.
 */
public class CommandRecolor extends ACommand {

  private final int newR;
  private final int newG;
  private final int newB;

  /**
   * Constructor for recolor command class. Allows a user to recolor an IShape, setting its new
   * color to the RGB value where R is the newR value given, G is the newG value given and B is the
   * newB value given. This command starts at startTime and ends at endTime.
   *
   * @param startTime The scheduled startTime for this recolor Command.
   * @param endTime   The scheduled endTime for this recolor Command.
   * @param newR      The new red component of the new RGB coloring of the IShape. Must be between 0
   *                  and 255.
   * @param newG      The new green component of the new RBG coloring of the IShape. Must be between
   *                  0 and 255.
   * @param newB      The new blue component of the new RGB coloring of the IShape. Must be between
   *                  0 and 255.
   * @throws IllegalArgumentException Thrown if given an invalid RGB value.
   */
  public CommandRecolor(int startTime, int endTime, int newR, int newG, int newB)
          throws IllegalArgumentException {
    super(startTime, endTime);

    if (!(UtilityFunctions.isValidColor(newR) && UtilityFunctions.isValidColor(newG)
            && UtilityFunctions.isValidColor(newB))) {
      throw new IllegalArgumentException("Must pass in valid RGB values - " +
              "integers between 0 and 255");
    }
    this.newR = newR;
    this.newG = newG;
    this.newB = newB;
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
      setVelocity(new IVector[]{shape.getVectorR(), shape.getVectorG(), shape.getVectorB()},
              new int[]{newR, newG, newB});
      /*
      this.startString = shape.draw(currentTick);
      this.state = CommandStateEnum.DURING;
      */
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
      resetVelocity(shape.getVectorR(), shape.getVectorG(), shape.getVectorB());
      /*
      this.endString = shape.draw(currentTick);
      this.state = CommandStateEnum.POST;
      */
    }
  }

  @Override
  public void doCommand(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    abstractDoCmd(new IVector[]{shape.getVectorR(), shape.getVectorG(), shape.getVectorB()},
            new int[]{newR, newG, newB});
  }


  @Override
  public ICommand changeStartTime(int startTime) {
    return new CommandRecolor(startTime, this.endTime, this.newR, this.newG, this.newB);
  }

  @Override
  public String toString() {
    return "Recolor: Tick: " + startTime + ", R: " + this.newR + ", G: " + this.newG
            + ", B: " + this.newB;
  }

}
