package cs3500.Animator.View;

import cs3500.Animator.Controller.IAnimatorController;

public interface IInteractiveAnimatorView {
  void throwException(String error);
  void updateDisplay(int tick);
  void addController(IAnimatorController controller);
}
