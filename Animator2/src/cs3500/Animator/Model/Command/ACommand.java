package cs3500.Animator.Model.Command;

import cs3500.Animator.Model.Shape.IVector;

/**
 * Abstract Command class that reduces some duplicate code amongst Commands. This class contains
 * lots of useful helper functions for Commands that follow the general pattern of having a
 * different start and end time, and cause a shape to change linearly.
 */
abstract class ACommand implements ICommand {

  protected final int startTime;
  protected final int endTime;

  /**
   * Initializes an abstract Command class. This class holds onto the scheduled startTime and
   * endTime for this command. Additionally it carries out most of the functionality for printing a
   * command.
   *
   * @param startTime The scheduled start time for this ACommand.
   * @param endTime   The Scheduled end time for this ACommand.
   * @throws IllegalArgumentException Thrown if endTime is greater than startTime.
   */
  protected ACommand(int startTime, int endTime) throws IllegalArgumentException {

    if (startTime < 0) {
      throw new IllegalArgumentException("No negative time");
    }

    if (startTime >= endTime) {
      throw new IllegalArgumentException("Start time must be before end time");
    }
    this.startTime = startTime;
    this.endTime = endTime;
  }


  /**
   * Resets the given array of Vector's vx values to zero.
   *
   * @param vectors The given Vectors whose vx values get reset to zero.
   */
  protected void resetVelocity(IVector... vectors) {
    for (IVector v : vectors) {
      v.setVx(0);
    }
  }

  /**
   * This sets the velocities of the given array of Vectors using the desired final values of that
   * vector, the current values of that vector, and the duration of time the command is scheduled to
   * take. The two given arrays should be of the same length, where each element in one array
   * corresponds with the element of the same index in the other array.
   *
   * @param vectors   This array of Vectors contains the velocity values which we will mutate, and
   *                  contain the old values, which we use to calculate the new values from.
   * @param newValues This array of integers contains the desired final values of the command.
   * @throws IllegalArgumentException Thrown if the two gien arrays are not of the same length.
   */
  protected void setVelocity(IVector vectors[], int newValues[]) throws IllegalArgumentException {
    if (vectors.length != newValues.length) {
      throw new IllegalArgumentException("The two given arrays should be of the same length, " +
              "where each element in one array corresponds with the element of the same index " +
              "in the other array.");
    }
    int duration = endTime - startTime;
    for (int i = 0; i < vectors.length && i < newValues.length; i++) {
      vectors[i].setVx((newValues[i] - vectors[i].getX()) / duration);
    }
  }


  /**
   * This makes method does three things for each vector given to it: 1. It changes the Vector's
   * velocity so that it will reach the given new value in one step. 2. It calls the function
   * change() on that vector, thus changing the vector's value to the given new value. 3. It resets
   * the Vector's velocity back to zero to prevent any further changes. This function is mainly
   * useful as a helper function to help implement the doCommand function from the ICommand
   * interface. The two given arrays should be of the same length, where each element in one array
   * corresponds with the element of the same index in the other array.
   *
   * @param vectors   This array of Vectors contains the velocity values which we will mutate, and
   *                  contain the old values, which we use to calculate the new values from.
   * @param newValues This array of integers contains the desired final values of the command.
   * @throws IllegalArgumentException Thrown if the two gien arrays are not of the same length.
   */
  protected void abstractDoCmd(IVector vectors[], int newValues[]) throws IllegalArgumentException {
    if (vectors.length != newValues.length) {
      throw new IllegalArgumentException("The two given arrays should be of the same length, " +
              "where each element in one array corresponds with the element of the same index " +
              "in the other array.");
    }
    for (int i = 0; i < vectors.length && i < newValues.length; i++) {
      vectors[i].setVx(newValues[i] - vectors[i].getX());
      vectors[i].change();
      vectors[i].setVx(0);
    }

  }

  @Override
  public boolean isOverlapping(int tick) {
    return (tick > startTime) && (tick < endTime);
  }

  @Override
  public int getStartTick() {
    return this.startTime;
  }

  @Override
  public int getEndTick() {
    return this.endTime;
  }
}
