package cs3500.Animator.Model.Shape;


/**
 * This class represents a vector. This vector maintains two fields, a number, and how much to
 * change that number by. This means that the user cannot directly change the value x, rather they
 * need to change vx and then call the function change() to affect the value of x. x and vx are
 * doubles to avoid accumulating rounding errors, but these values should be rounded to ints if you
 * need integer representations of these values.
 */
public class Vector implements IVector {

  // These values are not final because we believe redefining a new Vector each time a frame occurs
  // would be less efficient than just redefining an int, which may matter if you are animating
  // a lot of IShapes.
  private double x;
  private double vx;


  /**
   * Constructor for a Vector that sets it's initial x value to 0 and it's initial vx, or change, to
   * 0.
   */
  public Vector() {
    this.x = 0;
    this.vx = 0;
  }

  /**
   * Constructor for a Vector that sets it's initial x value to the given x value and it's initial
   * vx value, or change, to 0.
   *
   * @param x Sets the initial x value of this Vector to the given x.
   */
  public Vector(double x) {
    this.x = x;
    this.vx = 0;
  }

  /**
   * Constructor for a Vector that sets it's initial x value to the given x value and it's initial
   * vx value, or change, to the given vx value.
   *
   * @param x  Sets the initial x value of this Vector to the given x.
   * @param vx Sets the initial vx value of this Vector to the given vx.
   */
  public Vector(double x, double vx) {
    this.x = x;
    this.vx = vx;
  }

  /**
   * Changes the x value of this Vector by vx.
   */
  public void change() {
    this.x += this.vx;
  }

  /**
   * Sets the vx value of this Vector to a new vx value.
   *
   * @param vx The new vx value of this Vector.
   */
  public void setVx(double vx) {
    this.vx = vx;
  }

  /**
   * Returns the x value of this Vector.
   *
   * @return The x value of this Vector.
   */
  public double getX() {
    return this.x;
  }

}
