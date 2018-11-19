
package cs3500.Animator.Model.AnimatorModel;

import java.util.ArrayList;
import java.util.List;

import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Model.Shape.AShape;
import cs3500.Animator.Util.AnimationBuilder;


/**
 * This class represents an animator object that can run an animation.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private final List<IShape> initialShapes;
  private List<IShape> shapes;
  private int tickCounter;
  private final int leftX;
  private final int leftY;
  private final int width;
  private final int height;
  private int animationLength;



/*
  public AnimatorModelImpl(AnimatorBuilder builder) {
    this.shapes = builder.shapes;
    this.tickCounter = 0;
    this.leftX = builder.leftX;
    this.leftY = builder.leftY;
    this.width = builder.width;
    this.height = builder.height;
  }*/

  /**
   * Constructs an animator with the given values.
   *
   * @param shapes          list of IShapes
   * @param leftX           top left x of canvas
   * @param leftY           top left y of canvas
   * @param width           width x of canvas
   * @param height          height x of canvas
   * @param animationLength desired length of animation to run
   */
  private AnimatorModelImpl(List<IShape> shapes, int leftX, int leftY, int width, int height,
                            int animationLength)
          throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("List of shapes can not be null");
    }
    this.initialShapes = shapes;
    this.leftX = leftX;
    this.leftY = leftY;
    this.width = width;
    this.height = height;
    this.animationLength = animationLength;
    this.resetAnimation();
  }

  /**
   * This class represents an Animator builder which constructs an Animator.
   */
  public static class AnimatorBuilder implements AnimationBuilder<AnimatorModelImpl> {
    private List<AShape.ShapeBuilder> shapes;
    //private int tickCounter;
    private int leftX;
    private int leftY;
    private int width;
    private int height;
    private int animationLength;

    /**
     * Constructor of a Builder object for an animator.
     */
    public AnimatorBuilder() {
      //this.tickCounter = 0;
      this.shapes = new ArrayList<AShape.ShapeBuilder>();
      this.leftX = 0;
      this.leftY = 0;
      this.width = 0;
      this.height = 0;
      this.animationLength = 0;
    }


    /**
     * Constructs a builder object of type AnimatorModelImpl.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the bounding box
     * @param height The height of the bounding box
     * @return A builder for the AnimatorModelImpl
     */
    @Override
    public AnimationBuilder<AnimatorModelImpl> setBounds(int x, int y, int width, int height) {
      this.leftX = x;
      this.leftY = y;
      this.width = width;
      this.height = height;
      return this;
    }

    /**
     * Adds a new shape to the list of shapes.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return ABuilder who has the shape in the list of shpaes fiel.
     */
    @Override
    public AnimationBuilder<AnimatorModelImpl> declareShape(String name, String type) {

      for (AShape.ShapeBuilder shape : shapes) {
        if (shape.getName().equals(name)) {
          throw new IllegalArgumentException("names must be unique.");
        }
      }

      this.shapes.add(new AShape.ShapeBuilder(name, type));

      return this;
    }

    /**
     * Adds a command to the shape with the given name.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return A builder who has had a command added to the given shape.
     * @throws IllegalArgumentException Throws exception if the given shape name does not exist.
     */
    @Override
    public AnimationBuilder<AnimatorModelImpl> addMotion(String name,
                                                         int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                                                         int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
            throws IllegalArgumentException {
      for (AShape.ShapeBuilder shape : shapes) {
        if (shape.getName().equals(name)) {
          shape.addCommand(t1, x1, y1, w1, h1, r1, g1, b1,
                  t2, x2, y2, w2, h2, r2, g2, b2);
          if (this.animationLength < t2) {
            this.animationLength = t2;
          }
          return this;
        }
      }
      throw new IllegalArgumentException("No shape of that name has been defined");
    }

    /**
     * Adds a keyFrame to the given shape of this model.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t    The time for this keyframe
     * @param x    The x-position of the shape
     * @param y    The y-position of the shape
     * @param w    The width of the shape
     * @param h    The height of the shape
     * @param r    The red color-value of the shape
     * @param g    The green color-value of the shape
     * @param b    The blue color-value of the shape
     * @return AnimatorBuilder with given keyFrame, which does not exist.
     */
    public AnimationBuilder<AnimatorModelImpl> addKeyframe(String name, int t, int x, int y, int w,
                                                           int h, int r, int g, int b) {
      if (this.animationLength < t) {
        this.animationLength = t;
      }


      for (AShape.ShapeBuilder shape : shapes) {
        if (shape.getName().equals(name)) {
          shape.addKeyFrame(t, x, y, w, h, r, g, b);
          return this;
        }
      }


      throw new IllegalArgumentException("no shape with this name");
    }


    /**
     * Builds animator with based of the builders value.
     *
     * @return AnimatorModelImpl from the given values.
     */
    @Override
    public AnimatorModelImpl build() {
      ArrayList<IShape> result = new ArrayList<>();
      for (AShape.ShapeBuilder s : shapes) {
        result.add(s.build());
      }
      return new AnimatorModelImpl(result, this.leftX, this.leftY, this.width, this.height,
              this.animationLength);
    }
  }

  /**
   * Updates the tickCounter and all Vectors within every IShape in this animation.
   */
  @Override
  public void updateTick() {
    for (IShape shape : shapes) {
      shape.stopCommands(tickCounter);
      shape.activateCommands(tickCounter);
      shape.getVectorX().change();
      shape.getVectorY().change();
      shape.getVectorW().change();
      shape.getVectorH().change();
      shape.getVectorR().change();
      shape.getVectorG().change();
      shape.getVectorB().change();
    }
    tickCounter++;
    // wait??
  }


  @Override
  public int getLeftX() {
    return this.leftX;
  }

  @Override
  public int getUpperY() {
    return this.leftY;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getAnimationLength() {
    return this.animationLength;
  }

  @Override
  public List<IShape> getShapes() {

    ArrayList<IShape> copyShapes = new ArrayList<IShape>(this.shapes.size());
    for (IShape shape : this.shapes) {
      copyShapes.add(shape.copy());
      //copyShapes.add(shape);
    }
    return copyShapes;

    //return new ArrayList<IShape>(this.shapes);
  }

  @Override
  public void resetAnimation() {
    this.tickCounter = 0;
    this.shapes = new ArrayList<IShape>(this.initialShapes.size());
    for (IShape shape : this.initialShapes) {
      shapes.add(shape.copy());
    }
  }

  @Override
  public void addShape(String name, String type) {

    if (name == null) {
      throw new IllegalArgumentException("Cannot have null name");
    }

    if (name.equals("")) {
      throw new IllegalArgumentException("Cannot have an empty name");
    }


    for (IShape shape: this.initialShapes) {
      if (name.equals(shape.getName())) {
        throw new IllegalArgumentException("Cannot add duplicate names");
      }
    }

    this.initialShapes.add(new AShape.ShapeBuilder(name, type).build());
    this.resetAnimation();
  }

  @Override
  public void removeShape(IShape shape) {


    boolean removed = false;

    for (int i = 0; i < initialShapes.size(); i++) {
      if (shape.toString().equals(initialShapes.get(i).toString())) {
        initialShapes.remove(i);
        this.resetAnimation();
        removed = true;
        //return;
        //i--;
      }
    }

    if (!removed) {
      throw new IllegalArgumentException("Shape does not exist in this animation");
    }

    /*
    if (!this.initialShapes.remove(shape)) {
      System.out.println(this.initialShapes.contains(shape));
      System.out.println(shape.toString());
      throw new IllegalArgumentException("Shape does not exist in this animation");
    }*/
    //this.resetAnimation();
  }
/*
  //@Override
  public void resetShapes() {
    this.tickCounter = 0;
    this.shapes = new ArrayList<IShape>(this.initialShapes.size());
    for (IShape shape : this.initialShapes) {
      shapes.add(shape.copy());
    }
  }*/

  @Override
  public void addCommand(IShape shape, ICommand command) {
    shape.addCommand(command);
  }

  @Override
  public void removeCommand(IShape shape, ICommand command) {
    shape.removeCommand(command);
  }


  @Override
  public void addKeyFrame(String name, int t, int x, int y, int w, int h, int r, int g, int b) {

    if (this.animationLength < t) {
      this.animationLength = t;
    }


    for (IShape shape : initialShapes) {
      if (shape.getName().equals(name)) {
        shape.addKeyFrame(t, x, y, w, h, r, g, b);
      }
    }

    this.resetAnimation();
  }

  @Override
  public void removeKeyFrame(String name, int t) {

    if (this.animationLength < t) {
      throw new IllegalArgumentException("No shapes operate in this time range.");
    }


    for (IShape shape : initialShapes) {
      if (shape.getName().equals(name)) {
        shape.removeKeyFrame(t);
      }
    }

    this.resetAnimation();
  }


}
