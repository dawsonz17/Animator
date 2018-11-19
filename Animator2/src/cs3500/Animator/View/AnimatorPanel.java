package cs3500.Animator.View;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Model.Shape.IShape;


/**
 * This panel represents the area on a JSwing GUI where an animation can actually be drawn visually
 * using rectangles and ovals to draw shapes in a specific frame. In order for this to draw a smooth
 * animation, this panel will need to be refreshed quickly allowing so that the images appear to
 * move, rather than jump.
 */
public class AnimatorPanel extends JPanel {

  IAnimatorModel model;


  /**
   * Constructor for the AnimatorPanel class. This panel can draw the current state of the model, as
   * in, each shape in the model, in its position, with the correct size, and in the correct color.
   *
   * @param model This is the model that will be drawn.
   */
  public AnimatorPanel(IAnimatorModel model) {
    super();
    this.model = model;
  }

  @Override
  public void paintComponent(Graphics gs) {
    super.paintComponent(gs);
    for (IShape shape : model.getShapes()) {
      if (shape.isVisible()) {
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        int r = shape.getRed();
        int g = shape.getGreen();
        int b = shape.getBlue();
        gs.setColor(new Color(r, g, b));
        /*
        System.out.println(shape.getBasicDesc() + ": x=" + x + ", y=" + y + ", width=" + width +
                ", height=" + height + ". Color: r=" + r + ", g=" + g + ", b=" + b);
        */
        if (shape.getBasicDesc().equals("rectangle")) {
          gs.fillRect(x, y, width, height);
        } else if (shape.getBasicDesc().equals("ellipse")) {
          gs.fillOval(x, y, width, height);
        }
      }
    }
  }
}
