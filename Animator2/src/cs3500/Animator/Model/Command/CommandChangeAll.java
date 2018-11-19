package cs3500.Animator.Model.Command;


import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Model.Shape.IVector;


/**
 * This class represents a Command which allows for the user to change every value of an IShape,
 * that is stored in an IVector This class is also useful for automatically generating Commands
 * because it does not worry about which values are changing because it will change all of them.
 */
public class CommandChangeAll extends ACommand {
  private final int newX;
  private final int newY;
  private final int newWidth;
  private final int newHeight;
  private final int newR;
  private final int newG;
  private final int newB;

  /**
   * Constructor for the CommandChangeAll class, which can affect every value stored in an IVector
   * in an IShape. This Command will change all vector values from their initial starting values, to
   * the values given in this constructor, starting at startTime and ending at endTime.
   *
   * @param startTime The scheduled startTime for this Command.
   * @param endTime   The scheduled endTime for this Command.
   * @param newX      The new x coordinate that the shape will move to.
   * @param newY      The new y coordinate that the shape will move to.
   * @param newWidth  The new width of the Shape after this Command runs.
   * @param newHeight The new height of the Shape after this Command runs.
   * @param newR      The new red component of the new RGB coloring of the IShape. Must be between 0
   *                  and 255.
   * @param newG      The new green component of the new RBG coloring of the IShape. Must be between
   *                  0 and 255.
   * @param newB      The new blue component of the new RGB coloring of the IShape. Must be between
   *                  0 and 255.
   * @throws IllegalArgumentException Thrown if given an invalid RGB value.
   */
  public CommandChangeAll(int startTime, int endTime, int newX, int newY, int newWidth,
                          int newHeight, int newR, int newG, int newB)
          throws IllegalArgumentException {
    super(startTime, endTime);

    if (!(isValidColor(newR) && isValidColor(newG) && isValidColor(newB))) {
      throw new IllegalArgumentException("Must pass in valid RGB values - " +
              "integers between 0 and 255");
    }

    this.newX = newX;
    this.newY = newY;
    this.newWidth = newWidth;
    this.newHeight = newHeight;
    this.newR = newR;
    this.newG = newG;
    this.newB = newB;
  }

  /**
   * Is the given number able to be used to construct a Color.
   *
   * @param n number being checked.
   * @return Is the given number able to be used to construct a Color?
   */
  private boolean isValidColor(double n) {
    return (n <= 255 && n >= 0);
  }

  @Override
  public void start(int currentTick, IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    if (currentTick == this.startTime) {
      setVelocity(new IVector[]{shape.getVectorX(), shape.getVectorY(), shape.getVectorW(),
                      shape.getVectorH(), shape.getVectorR(),
                      shape.getVectorG(), shape.getVectorB()},
              new int[]{this.newX, this.newY, this.newWidth, this.newHeight,
                      this.newR, this.newG, this.newB});
    }
  }

  @Override
  public void end(int currentTick, IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    if (currentTick == this.endTime) {
      resetVelocity(shape.getVectorX(), shape.getVectorY(), shape.getVectorW(), shape.getVectorH(),
              shape.getVectorR(), shape.getVectorG(), shape.getVectorB());
    }
  }

  @Override
  public void doCommand(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("given IShape cannot be null");
    }
    abstractDoCmd(new IVector[]{shape.getVectorX(), shape.getVectorY(), shape.getVectorW(),
                    shape.getVectorH(), shape.getVectorR(),
                    shape.getVectorG(), shape.getVectorB()},
            new int[]{this.newX, this.newY, this.newWidth, this.newHeight,
                    this.newR, this.newG, this.newB});
  }

  @Override
  public ICommand changeStartTime(int startTick) {
    return new CommandChangeAll(startTick, this.endTime, this.newX, this.newY, this.newWidth,
            this.newHeight, this.newR, this.newG, this.newB);
  }

  @Override
  public String toString() {
    return "Change all: Tick: " + startTime + ", X: " + this.newX + ", Y: " + this.newY + ", W: "
            + this.newWidth + ", H: " + this.newHeight + ", R: " + this.newR + ", G: " + this.newG
            + ", B: " + this.newB;
  }

}
