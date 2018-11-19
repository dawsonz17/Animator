package cs3500.Animator.View;

import java.awt.Dimension;
import java.awt.FlowLayout;

import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * This class represents a VisualView. Using the JSwing API we are able to draw a model to an
 * applet. This enables models to be truly visualized with a GUI as a user would expect. Currently
 * this view only allows a user to watch the animation, but cannot interact with it.
 */
public class VisualView extends JFrame implements IAnimatorView {

  private final JPanel animationPanel;
  private final JScrollPane scrollPane;
  private final IAnimatorModel model;
  private final int speed;

  /**
   * Constructor for a Visual View. This view actually opens a separate window and draws the
   * animation, each frame at a time, where each frame is defined by the given speed. Currently,
   * this view does not allow a user to interact with the animation in any way, it just plays from
   * the start to the finish and then stops animating. Once the animation is done, the view will
   * continue drawing the final frame of the animation.
   *
   * @param model The model that will be animated.
   * @param speed The speed at which the animations should go (in ticks/second).
   */
  public VisualView(IAnimatorModel model, int speed) {
    super();
    this.model = model;
    this.speed = speed;

    this.setTitle("Animating!");
    this.setSize(model.getLeftX() + model.getWidth(), model.getUpperY() + model.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //not sure I need a layout with just one thing in the layout.
    this.setLayout(new FlowLayout());

    this.animationPanel = new AnimatorPanel(model);
    this.animationPanel.setPreferredSize(new Dimension(model.getLeftX() + model.getWidth(),
            model.getUpperY() + model.getHeight()));
    this.scrollPane = new JScrollPane(animationPanel);
    this.add(scrollPane);

    this.pack();

    this.setVisible(true);
  }

  @Override
  public void animate() {
    //ignoring given model && speed args - not sure we actually need these or want them
    // we should add a length of animation field to the IAnimatorModel to make this easier.
    // We start by repainting first so we draw the initial world before anything happens.
    this.animationPanel.repaint();
    for (int tick = 0; tick < this.model.getAnimationLength(); tick++) {
      // below should technically happen in a controller - the view should not have access to
      // a function that mutates the model. However, this isn't really any other way to make this
      // work.
      this.model.updateTick();
      this.animationPanel.repaint();

      try {
        Thread.sleep(1000 / this.speed);
      } catch (InterruptedException e) {
        throw new IllegalStateException("View interrupted during sleep");
      }
    }
  }
}
