package cs3500.Animator.Controller;

import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Model.Shape.AShape;
import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.View.IInteractiveAnimatorView;

public class AnimatorControllerImpl implements IAnimatorController {

  IAnimatorModel model;
  IInteractiveAnimatorView view;
  int speed;
  boolean paused;
  boolean loop;
  int tick;

  public AnimatorControllerImpl(IAnimatorModel model, IInteractiveAnimatorView view, int speed) {
    this.model = model;
    this.view = view;
    this.speed = speed;
    this.paused = true;
    this.loop = false;
    this.tick = 0;
    this.view.addController(this);
  }

  @Override
  public void startAnimation() {
    while (true) {
      this.nextFrame();

      // wait
      try {
        Thread.sleep(1000 / this.speed);
      } catch (InterruptedException e) {
        throw new IllegalStateException("Controller interrupted during sleep");
      }
    }
  }

  private void nextFrame() {
    if (this.tick > this.model.getAnimationLength()) {
      if (this.loop) {
        this.restartAnimation();
      } else {
        return;
      }
    }

    if (!this.paused) {
      this.model.updateTick();
      this.view.updateDisplay(this.tick);
      this.tick++;
    }
  }

  @Override
  public void restartAnimation() {
    this.model.resetAnimation();
    this.view.updateDisplay(0);
    this.tick = 0;
  }

  @Override
  public void toggleAnimationLoop() {
    this.loop = !this.loop;
  }

  @Override
  public void speedUpAnimation() {
    this.speed = this.speed * 2;
  }

  @Override
  public void playPauseAnimation() {
    this.paused = !this.paused;
  }

  @Override
  public void slowDownAnimation() {
    if (this.speed > 1) {
      this.speed = this.speed / 2;
    }
  }

  // for below methods:
  // - check that input values are valid (-1 in input should be thrown out, but an error
  //   message has already given so it should be okay to just give up and end
  // - catch any exceptions thrown by the model and throw them in the view using command.

  @Override
  public void addShape(String name, String type) {

    try {
      this.model.addShape(name, type);
    } catch (IllegalArgumentException e) {
      this.view.throwException(e.getMessage());
    }
    System.out.println("add shape");
  }

  @Override
  public void removeShape(IShape shape) {
    try {
      this.model.removeShape(shape);
      this.restartAnimation();
    } catch (IllegalArgumentException e) {
      this.view.throwException(e.getMessage());
    }
    System.out.println("remove shape");
  }

  @Override
  public void addKeyframe(IShape shape, int tick, int x, int y, int width, int height,
                          int red, int green, int blue) {
    try {
      this.model.addKeyFrame(shape.getName(), tick, x, y, width, height, red, green, blue);
      this.restartAnimation();
    } catch (IllegalArgumentException e) {
      this.view.throwException(e.getMessage());
    }
    //System.out.println("add key frame");
  }

  @Override
  public void removeKeyframe(IShape shape, ICommand keyframe) {
    try {
      this.model.removeKeyFrame(shape.getName(), keyframe.getStartTick());
      this.restartAnimation();
    } catch (IllegalArgumentException e) {
      this.view.throwException(e.getMessage());
    }
    //System.out.println("remove keyframe");
  }
}
