

package cs3500.Animator.Model.Shape;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import cs3500.Animator.Model.Command.CommandChangeAll;
import cs3500.Animator.Model.Command.CommandFlipVisible;
import cs3500.Animator.Model.Command.CommandStay;
import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Util.UtilityFunctions;

/**
 * This abstract class represents a shape that is defined by vectors and has a list of commands to
 * be done.
 */
public abstract class AShape implements IShape {

  private final IVector vectorX;
  private final IVector vectorY;
  private final IVector vectorW;
  private final IVector vectorH;
  private final IVector vectorR;
  private final IVector vectorG;
  private final IVector vectorB;


  private final List<ICommand> initialCommands;
  private List<ICommand> commands;

  protected final String name;

  private boolean isVisible;

  private int startTime;
  private int endTime;

  /**
   * Constructs a shape based on the given values.
   *
   * @param x        start x
   * @param y        start y
   * @param height   start height
   * @param width    start width
   * @param r        start red
   * @param g        start green
   * @param b        start blue
   * @param commands commands to be done on shape
   * @param name     name of shape
   * @throws IllegalArgumentException if given null values for commands or name or color is not
   *                                  valid
   */
  protected AShape(int x, int y, int height, int width, int r, int g, int b,
                   List<ICommand> commands, String name, int startTime, int endTime,
                   boolean isVisible) throws IllegalArgumentException {
    if (commands == null || name == null) {
      throw new IllegalArgumentException("No values may be null");
    }

    if (!(UtilityFunctions.isValidColor(r) && UtilityFunctions.isValidColor(g)
            && UtilityFunctions.isValidColor(b))) {
      throw new IllegalArgumentException("not a valid color");
    }

    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("height and width must be non-negative integers");
    }

    this.initialCommands = commands;
    this.commands = new ArrayList<ICommand>(initialCommands);

    this.vectorX = new Vector(x);
    this.vectorY = new Vector(y);
    this.vectorW = new Vector(width);
    this.vectorH = new Vector(height);
    this.vectorR = new Vector(r);
    this.vectorG = new Vector(g);
    this.vectorB = new Vector(b);
    this.commands = commands;
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.isVisible = isVisible;
  }

  protected AShape(int x, int y, int height, int width, int r, int g, int b,
                   List<ICommand> commands, String name, int startTime, int endTime)
          throws IllegalArgumentException {
    this(x, y, height, width, r, g, b, commands, name, startTime, endTime, false);
  }

  /**
   * This class represents a ShapeBuilder. The shape builder is used to make a Shape whose values
   * are final.
   */
  public static final class ShapeBuilder {
    private int x;
    private int y;
    private int height;
    private int width;
    private int r;
    private int g;
    private int b;
    private List<ICommand> commands;
    String name;
    String type;
    private int startTime;
    private int endTime;

    /**
     * The constructor of a String Builder. Sets all values to zero, as they should be changed
     * later.
     *
     * @param name Name of the shape.
     * @param type Type of the shape(ie. rectangle, ellipse)
     */
    public ShapeBuilder(String name, String type) {
      this.name = name;
      this.type = type;
      this.x = 0;
      this.y = 0;
      this.height = 0;
      this.width = 0;
      this.r = 0;
      this.g = 0;
      this.b = 0;
      this.commands = new ArrayList<ICommand>();
      this.startTime = 0;
      this.endTime = 0;
    }

    /**
     * Returns the name of this ShapeBuilder
     *
     * @return String representation of ShapeBuilder.
     */
    public String getName() {
      return this.name;
    }

    /**
     * Sets the initial values of the ShapeBuilder.
     *
     * @param x      starting x
     * @param y      starting y
     * @param height starting height
     * @param width  starting width
     * @param r      starting red
     * @param g      starting green
     * @param b      starting blue
     * @return Returns the ShapeBuilder with its values set.
     * @throws IllegalArgumentException Throws exception if invalid color, height, or width is
     *                                  given. This means colors are below 0 or above 255 or width
     *                                  or height are below 0.
     */
    public ShapeBuilder setInitialValues(int x, int y, int height, int width, int r, int g, int b)
            throws IllegalArgumentException {
      if (!(UtilityFunctions.isValidColor(r) && UtilityFunctions.isValidColor(g)
              && UtilityFunctions.isValidColor(b))) {
        throw new IllegalArgumentException("not a valid color");
      }

      if (height < 0 || width < 0) {
        throw new IllegalArgumentException("height and width must be non-negative integers");
      }
      this.x = x;
      this.y = y;
      this.height = height;
      this.width = width;
      this.r = r;
      this.g = g;
      this.b = b;
      return this;
    }


    /**
     * Adds the given command to the list of commands stored by the ShapeBuilder.
     *
     * @param command ICommand that is being added.
     * @return Returns the ShapeBuilder with the command added to its list of commands.
     * @throws IllegalArgumentException Throws exception if command overlaps with other commands in
     *                                  terms of time.
     */
    public ShapeBuilder addCommand(ICommand command) throws IllegalArgumentException {
      /*if (this.commands.size() == 0) {
        this.commands.add(new CommandFlipVisible(command.getStartTick()));
      }*/
      for (ICommand cmd : this.commands) {
        if (cmd.isOverlapping(command.getStartTick()) || cmd.isOverlapping(command.getEndTick())) {
          throw new IllegalArgumentException("Commands cannot overlap timewise");
        }
      }

      //insertCommand(this.commands, command);

      this.commands.add(command);
      return this;
    }

    /*
    private static void insertCommand(List<ICommand> commands, ICommand command) {


      commands.add(command);

      if (commands.size() == 0) {
        commands.add(command);
        return;
      }

      if (commands.get(0).getStartTick() >= command.getEndTick()) {
        commands.add(0, command);
        return;
      }

      if (commands.get(commands.size() - 1).getEndTick() <= command.getStartTick()) {
        commands.add(command);
        return;
      }


      for (int i = 0; i < commands.size() - 1; i++) {
        if (command.getStartTick() >= commands.get(i).getEndTick()
                && command.getEndTick() <= commands.get(i + 1).getStartTick()) {
          commands.add(i, command);
          return;
        }
      }
    }*/


    /**
     * Adds the given command to the list of commands stored by the ShapeBuilder.
     *
     * @param t1 start tick
     * @param x1 start x
     * @param y1 start y
     * @param w1 start width
     * @param h1 start height
     * @param r1 start red
     * @param g1 start green
     * @param b1 start blue
     * @param t2 end tick
     * @param x2 end x
     * @param y2 end y
     * @param w2 end width
     * @param h2 end height
     * @param r2 end red
     * @param g2 end green
     * @param b2 end blue
     * @return ShapeBuilder with given command added.
     * @throws IllegalStateException Throws exception if the type of shape trying to be constructed
     *                               is not currently defined.
     */
    public ShapeBuilder addCommand(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                                   int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
            throws IllegalStateException {
      // this is an interesting method because we don't use so many variables.
      if (this.commands.size() == 0) {
        this.setInitialValues(x1, y1, h1, w1, r1, g1, b1);
      }
      // if all values match we can't use it as a normal command because commands generally
      // can't start and end on the same tick.
      if (t1 == t2 && x1 == x2 && y1 == y2 && w1 == w2 &&
              h1 == h2 && r1 == r2 && g1 == g2 && b1 == b2) {
        //this.commands.add(new CommandFlipVisible(t1));
        return this;
      }
      return this.addCommand(new CommandChangeAll(t1, t2, x2, y2, w2, h2, r2, g2, b2));
    }

    /**
     * Calls the constructor for the shape with the given type and holding the values stored in the
     * ShapeBuilder.
     *
     * @return IShape that is instantiated from the ShapeBuilder(ie. a rectangle or ellipse)
     */
    public IShape build() {

      Collections.sort(this.commands, new Compare());
      try {
        startTime = commands.get(0).getStartTick();
        endTime = commands.get(commands.size() - 1).getEndTick();
      } catch (IndexOutOfBoundsException e) {
        startTime = 0;
        endTime = 0;
      }
      commands.add(0, new CommandFlipVisible(startTime));
      commands.add(new CommandFlipVisible(endTime));
      switch (this.type) {
        case "rectangle":
          return new RectangleImpl(this.x, this.y, this.height, this.width, this.r,
                  this.g, this.b, this.commands, this.name, this.startTime, this.endTime);
        case "ellipse":
          return new OvalImpl(this.x, this.y, this.height, this.width, this.r,
                  this.g, this.b, this.commands, this.name, this.startTime, this.endTime);
        default:
          throw new IllegalStateException("type undefined");
      }
    }

    class Compare implements Comparator<ICommand> {
      @Override
      public int compare(ICommand a, ICommand b) {
        return a.getStartTick() - b.getStartTick();
      }
    }


    /**
     * This commands add a key frame into an animation by taking a command and dividing it into 2
     * commands that meet in the middle at the given values.
     *
     * @param t time of key frame
     * @param x x of key frame
     * @param y y of key frame
     * @param w width of key frame
     * @param h height of key frame
     * @param r red component of key frame
     * @param g green component of jey frame
     * @param b blue component of jey frame
     */
    public void addKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b) {


      if (commands.size() == 0) {
        this.addCommand(new CommandStay(t, t + 1));
        //this.startTime = t;
        //commands.add(new CommandStay(t, t + 1));
        return;
      }

      for (int i = 0; i < commands.size(); i++) {
        ICommand command = commands.get(i);

        if (command.getStartTick() == t || command.getEndTick() == t) {
          throw new IllegalArgumentException("Key Frames cannot be added over existing key frames." +
                  "Choose a valid time to add the key frame.");
        }

        if (command.getStartTick() < t && command.getEndTick() > t) {
          commands.set(i, command.changeStartTime(t));
          commands.add(new CommandChangeAll(command.getStartTick(), t, x, y, w, h, r, g, b));
          return;
        }
      }

      if (commands.get(0).getStartTick() > t) {
        //this.startTime = t;
        commands.set(0, commands.get(0).changeStartTime(t));
      } else {
        //the command goes afterwards
        //this.endTime = t;
        commands.add(new CommandChangeAll(commands.get(commands.size() - 2).getEndTick(),
                t, x, y, w, h, r, g, b));
      }
    }
  }

  /**
   * This method stops all commands scheduled to stop at this time.
   *
   * @param tickCounter The current global time. Used in the commands to determine if they are
   *                    scheduled to stop at this tickCounter value.
   */
  @Override
  public void stopCommands(int tickCounter) {
    for (ICommand command : commands) {
      command.end(tickCounter, this);
    }
  }

  /**
   * This method activates all commands scheduled to start at this time.
   *
   * @param tickCounter The current global time. Used in the commands to determine if they are
   *                    scheduled to start at this tickCounter value.
   */
  @Override
  public void activateCommands(int tickCounter) {
    for (ICommand command : commands) {
      command.start(tickCounter, this);
    }
  }

  @Override
  public IVector getVectorX() {
    return this.vectorX;
  }

  @Override
  public IVector getVectorY() {
    return this.vectorY;
  }

  @Override
  public IVector getVectorW() {
    return this.vectorW;
  }

  @Override
  public IVector getVectorH() {
    return this.vectorH;
  }

  @Override
  public IVector getVectorR() {
    return this.vectorR;
  }

  @Override
  public IVector getVectorG() {
    return this.vectorG;
  }

  @Override
  public IVector getVectorB() {
    return this.vectorB;
  }

  @Override
  public int getX() {
    return (int) Math.round(this.vectorX.getX());
  }

  @Override
  public int getY() {
    return (int) Math.round(this.vectorY.getX());
  }

  @Override
  public int getWidth() {
    return (int) Math.round(this.vectorW.getX());
  }

  @Override
  public int getHeight() {
    return (int) Math.round(this.vectorH.getX());
  }

  @Override
  public int getRed() {
    return (int) Math.round(this.vectorR.getX());
  }

  @Override
  public int getGreen() {
    return (int) Math.round(this.vectorG.getX());
  }

  @Override
  public int getBlue() {
    return (int) Math.round(this.vectorB.getX());
  }

  @Override
  public void flipVisibility() {
    this.isVisible = !this.isVisible;
  }

  @Override
  public boolean isVisible() {
    return this.isVisible;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public List<ICommand> getCommands() {
    return new ArrayList<ICommand>(this.commands);
  }


  @Override
  public void addCommand(ICommand command) {

    //if there is nothing in the list of commands make this visible at the start of the given command
    if (commands.size() == 0) {
      commands.add(new CommandFlipVisible(command.getStartTick()));
      commands.add(command);
      commands.add(new CommandFlipVisible(command.getEndTick()));
    }

    //check for overlapping commands
    for (ICommand cmd : this.commands) {
      if (cmd.isOverlapping(command.getStartTick()) || cmd.isOverlapping(command.getEndTick())) {
        throw new IllegalArgumentException("Commands cannot overlap timewise");
      }
    }

    //if it is before all other commands add it to the beginning and change when shape
    // is made visible
    if (command.getStartTick() < commands.get(0).getStartTick()) {
      //remove old visibility change
      commands.remove(0);
      //add given command to beginning of list
      commands.add(0, command);
      //make the shape visible as that command is starting
      commands.add(0, new CommandFlipVisible(command.getStartTick()));
    } else if (command.getStartTick() > commands.get(commands.size() - 1).getEndTick()) {

      commands.remove(commands.size() - 1);

      commands.add(command);

      commands.add(new CommandFlipVisible(command.getEndTick()));

    } else {

      //finds spot to insert command by start time.
      int spot = Collections.binarySearch(this.commands, command, new Compare());

      try {
        //try to place it in the list at the given spot
        commands.add((spot + 1) * -1, command);
      } catch (IndexOutOfBoundsException e) {
        //add to the end if for some reason something goes wrong
        commands.add(commands.size() - 2, command);
      }
    }
  }

  @Override
  public void removeCommand(ICommand command) {


    if (commands.indexOf(command) == 0 || commands.indexOf(command) == commands.size() - 1) {
      throw new IllegalArgumentException("Cannot remove flip visibility command");
    }


    //if the command is the first command that is not the flip visibility command
    //remove the command and change when the shape will change visibility to the
    //end of the given command.
    if (commands.indexOf(command) == 1) {
      commands.remove(0);
      commands.add(0, new CommandFlipVisible(command.getEndTick()));
      commands.remove(1);
      return;
    }

    if (commands.indexOf(command) == commands.size() - 2) {
      commands.remove(commands.size() - 1);
      commands.remove(commands.size() - 2);
      commands.add(new CommandFlipVisible(command.getStartTick()));
    }

    //remove command and throw exception if its not done
    if (!commands.remove(command)) {
      throw new IllegalArgumentException("command does not exist in given shape.");
    }
  }

  /**
   * This commands add a key frame into an animation by taking a command and dividing it into 2
   * commands that meet in the middle at the given values.
   *
   * @param t time of key frame
   * @param x x of key frame
   * @param y y of key frame
   * @param w width of key frame
   * @param h height of key frame
   * @param r red component of key frame
   * @param g green component of jey frame
   * @param b blue component of jey frame
   */
  @Override
  public void addKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b) {

    //add a quick flash if no commands existed before
    if (commands.size() == 0) {
      this.addCommand(new CommandStay(t, t + 1));
      //commands.add(new CommandStay(t, t + 1));
      return;
    }

    //add to the beginning or end if necessary
    if (commands.get(0).getStartTick() > t) {
      commands.remove(0);
      commands.add(0, new CommandFlipVisible(t));
      //this.addCommand(new CommandFlipVisible(t));
      this.changeStartTime(t);
      //commands.set(0, commands.get(0).changeStartTime(t));
      //this.addCommand(new CommandFlipVisible(t));
      this.setValues(t, x, y, w, h, r, g, b);
      //this.addCommand(new CommandChangeAll(t, commands.get(0).getStartTick(), ));
      commands.set(1, commands.get(1).changeStartTime(t));
      //this.addCommand(new CommandFlipVisible(t));
      return;
    }

    if (commands.get(commands.size() - 1).getEndTick() < t) {
      //the command goes afterwards
      this.changeEndTime(t);
      //commands.remove(commands.size() - 1);
      //commands.add(new CommandFlipVisible(t));
      this.addCommand(new CommandChangeAll(commands.get(commands.size() - 2).getEndTick(),
              t, x, y, w, h, r, g, b));
      return;
    }

    //check everything in the list to see if it falls in between
    for (ICommand command : commands) {
      //ICommand command = commands.get(i);

      if (command.getStartTick() == t || command.getEndTick() == t) {
        //System.out.println();
        throw new IllegalArgumentException("Key Frames cannot be added over existing key frames." +
                "Choose a valid time to add the key frame.");
      }

      if (command.getStartTick() < t && command.getEndTick() > t) {

        int startTime = command.getStartTick();
        //remove the command that is being split
        this.removeCommand(command);
        //add the command back but make it shorter
        this.addCommand(command.changeStartTime(t));
        //add new command that gets you to the key frame
        this.addCommand(new CommandChangeAll(startTime, t, x, y, w, h, r, g, b));
        return;
      }
    }
  }

  private void setValues(int t, int x, int y, int w, int h, int r, int g, int b) {
    this.vectorX.setVx(x - this.vectorX.getX());
    this.vectorY.setVx(y - this.vectorY.getX());
    this.vectorW.setVx(w - this.vectorW.getX());
    this.vectorH.setVx(h - this.vectorH.getX());
    this.vectorR.setVx(r - this.vectorR.getX());
    this.vectorG.setVx(g - this.vectorG.getX());
    this.vectorB.setVx(b - this.vectorB.getX());

    this.vectorX.change();
    this.vectorY.change();
    this.vectorW.change();
    this.vectorH.change();
    this.vectorR.change();
    this.vectorG.change();
    this.vectorB.change();

  }

  @Override
  public void removeKeyFrame(int t) {


    if (commands.size() == 0) {
      throw new IllegalArgumentException("No keyframes in this shape");
    }


    if (this.startTime == t) {
      /*
      //remove the flip visible
      this.removeCommand(commands.get(0));*/
      //remove the command after the flip visible
      commands.remove(0);
      ICommand temp = commands.remove(0);

      commands.add(0, new CommandFlipVisible(temp.getEndTick()));

      /*
      //this.removeCommand(commands.get(1));
      try {
        this.addCommand(commands.get(0).changeStartTime(commands.get(1).getStartTick()));
      } catch (IndexOutOfBoundsException e) {
        //remove everything if there is no other commands
        commands.remove(0);
      }*/

      return;
    }

    if (this.endTime == t) {
      commands.remove(commands.size() - 1);
      ICommand temp = commands.remove(commands.size() - 1);
      commands.add(new CommandFlipVisible(temp.getStartTick()));
      return;
    }


    //check everything in the middle
    for (int i = 0; i < commands.size(); i++) {
      ICommand command = commands.get(i);

      if (command.getStartTick() == t) {
        commands.remove(command);
        try {
          //remove the next command temporarily to change the start time and re-add it
          ICommand temp = commands.remove(i + 1);
          this.addCommand(temp.changeStartTime(t));
        } catch (IndexOutOfBoundsException e) {
          //if removing at the end of a list just chang ethe end time
          this.changeEndTime(t);
        }
        return;
      }

    }

    //throw exception if there is no key frame at the given time.
    throw new IllegalArgumentException("Not a key frame at this time");
  }

  @Override
  public void modifyKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b) {
    this.removeKeyFrame(t);
    this.addKeyFrame(t, x, y, w, h, r, g, b);
  }

  @Override
  public void changeStartTime(int t) {
    this.startTime = t;
  }

  @Override
  public void changeEndTime(int t) {
    this.endTime = t;
  }

  @Override
  public String toString() {
    return this.getBasicDesc() + ": " + this.name;
  }

}


/**
 * This class is a comparator used to sort and insert motions into the list of commands to ensure
 * that they are ordered by time.
 */
class Compare implements Comparator<ICommand> {
  @Override
  public int compare(ICommand a, ICommand b) {
    return a.getStartTick() - b.getStartTick();
  }
}


