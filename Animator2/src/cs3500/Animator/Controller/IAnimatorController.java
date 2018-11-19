package cs3500.Animator.Controller;

import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Model.Shape.IShape;

public interface IAnimatorController {

  void startAnimation();

  void restartAnimation();

  void toggleAnimationLoop();

  void speedUpAnimation();

  void playPauseAnimation();

  void slowDownAnimation();

  void addShape(String name, String type);

  void removeShape(IShape shape);

  void addKeyframe(IShape shape, int tick, int x, int y, int width, int height,
                   int red, int green, int blue);

  void removeKeyframe(IShape shape, ICommand keyframe);

}
