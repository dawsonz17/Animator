package cs3500.Animator.View;

/**
 * This interface represents a view. A view can animate a model, for our implementations, the model
 * in the constructor. The animation can be written to whatever you want whether it is graphical or
 * textual.
 */
public interface IAnimatorView {


  /**
   * The animate method animates the model within the object. This can be done by writing to a file
   * or drawing into a gui.
   */
  void animate();

}
