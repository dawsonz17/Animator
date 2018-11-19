package cs3500.Animator.Util;

public class UtilityFunctions {

  /**
   * Is the given number able to be used to construct a Color.
   *
   * @param n number being checked.
   * @return Is the given number able to be used to construct a Color?
   */
  public static boolean isValidColor(double n) {
    return (n <= 255 && n >= 0);
  }
}
