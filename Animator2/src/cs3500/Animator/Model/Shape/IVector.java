package cs3500.Animator.Model.Shape;

/**
 * This Interface represents a vector which you can only indirectly affect the main value stored.
 */
public interface IVector {


  /**
   * Changes the value or values of the vector by vx.
   */
  void change();

  /**
   * Changes the velocity of the vector to the given value.
   *
   * @param vx New velocity in the form of a double.
   */
  void setVx(double vx);

  /**
   * Returns the value stored in the x spot of the vector.
   *
   * @return Returns the x value in the form of an int.
   */
  double getX();

}
