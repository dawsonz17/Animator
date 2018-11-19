package cs3500.Animator.View;

import java.io.IOException;

import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Model.Shape.IShape;


/**
 * This class represents a TextView. The objects of this class have the ability to animate a model
 * by writing all of the shapes and motions into an appendable according to the specifications
 * provided by homework 5 and 6.
 */
public class TextView implements IAnimatorView {

  private IAnimatorModel model;
  private Appendable ap;

  /**
   * Constructs a TextView that can be used to make txt files of the given animations.
   *
   * @param model The model that will be animated.
   * @param ap    The appendable object that the text will be appended to.
   */
  public TextView(IAnimatorModel model, Appendable ap) {

    if (model == null || ap == null) {
      throw new IllegalArgumentException("appendable cannot be null");
    }
    this.model = model;
    this.ap = ap;
  }

  /**
   * Appends the string to the appendable.
   *
   * @param str String that is appended to the appendable.
   * @throws IllegalStateException If appendable is not working.
   */
  private void transmit(String str) throws IllegalStateException {
    try {
      ap.append(str + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("appendable is not working properly.");
    }
  }

  /**
   * Returns a String that represents a shapes x y width height r g and b according to the given
   * specifications.
   *
   * @param shape The shape that is being represented as a String.
   * @return String that represents a shapes x y width height r g and b according to the given
   * specifications.
   */
  private String printShape(IShape shape) {
    return shape.getX() + " " + shape.getY() + " " + shape.getWidth() + " " + shape.getHeight()
            + " " + shape.getRed() + " " + shape.getBlue() + " " + shape.getGreen();
  }

  @Override
  public void animate() {
    this.transmit("canvas " + this.model.getLeftX() + " " + this.model.getUpperY() + " "
            + this.model.getWidth() + " " + this.model.getHeight());
    for (IShape shape : model.getShapes()) {
      // adds shape descriptor to output.
      this.transmit("shape " + shape.getName() + " " + shape.getBasicDesc());
      for (ICommand cmd : shape.getCommands()) {

        String startingPos = cmd.getStartTick() + " " + printShape(shape);
        cmd.doCommand(shape);
        String endingPos = cmd.getEndTick() + " " + printShape(shape);

        this.transmit("motion " + shape.getName() + " " + startingPos + "   " + endingPos);
      }
    }
  }
}

