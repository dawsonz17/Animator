package cs3500.Animator.View;

import java.io.IOException;
//import java.util.List;

import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Model.Shape.IShape;

/**
 * This class represents an SVGView that can be used to make SVG files.
 */
public class SVGView implements IAnimatorView {


  private IAnimatorModel model;
  private int speed;
  private Appendable ap;

  /**
   * Constructs an SVGView with the given model view and appendable.
   *
   * @param model IAnimatorModel that will be run.
   * @param speed Speed of animation (int ticks/second).
   * @param ap    Appendable that will hold the output.
   */
  public SVGView(IAnimatorModel model, int speed, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.model = model;
    this.speed = (100 / speed);
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
      ap.append(str);
    } catch (IOException e) {
      throw new IllegalStateException("appendable is not working properly.");
    }
  }


  @Override
  public void animate() {
    this.transmit("<svg width=\"" + (this.model.getLeftX() + this.model.getWidth()) + "\" height=\""
            + (this.model.getUpperY() + this.model.getHeight()) + "\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\" >\n");
    for (IShape shape : model.getShapes()) {
      // adds shape descriptor to output.
      this.transmit("<" + getSVGType(shape) + " id=\"" + shape.getName() + "\" "
              + getSVGDesc(shape) + " >\n");
      for (ICommand cmd : shape.getCommands()) {

        String movements;

        int startX = shape.getX();
        int startY = shape.getY();
        int startW = shape.getWidth();
        int startH = shape.getHeight();
        int startR = shape.getRed();
        int startG = shape.getGreen();
        int startB = shape.getBlue();
        boolean isVisibleStart = shape.isVisible();

        cmd.doCommand(shape);

        int endX = shape.getX();
        int endY = shape.getY();
        int endW = shape.getWidth();
        int endH = shape.getHeight();
        int endR = shape.getRed();
        int endG = shape.getGreen();
        int endB = shape.getBlue();
        boolean isVisibleEnd = shape.isVisible();


        movements = checkSVGCommands(startX, startY, startW, startH, startR, startG, startB, endX, endY,
                endW, endH, endR, endG, endB, shape, cmd);

        movements += commandVisibility(isVisibleStart, isVisibleEnd, cmd);


        this.transmit(movements);
      }

      this.transmit("</" + getSVGType(shape) + ">");
    }

    this.transmit("\n</svg>");
  }

  /**
   * Checks to see what SVG commands need to be done and creates a String to represent the command
   * in SVG commands.
   *
   * @param startX  Starting X of the shape.
   * @param startY  Starting Y of the shape.
   * @param startW  Starting width of the shape.
   * @param startH  Starting height of the shape.
   * @param startR  Starting red of the shape.
   * @param startG  Starting green of the shape.
   * @param startB  Starting blue of the shape.
   * @param endX    Ending X of the shape.
   * @param endY    Ending Y of the shape.
   * @param endW    Ending width of the shape.
   * @param endH    Ending height of the shape.
   * @param endR    Ending red of the shape.
   * @param endG    Ending green of the shape.
   * @param endB    Ending blue of the shape.
   * @param shape   The shape the command is acting upon.
   * @param command The command that is acting upon the shape.
   * @return Return the String representing all SVG commands that are being done to shape.
   */
  private String checkSVGCommands(int startX, int startY, int startW, int startH,
                                  int startR, int startG, int startB, int endX, int endY,
                                  int endW, int endH, int endR, int endG, int endB, IShape shape,
                                  ICommand command) {
    String result = "";

    if (startX != endX) {
      result += xCommand(shape, command, startX, endX);
    }
    if (startY != endY) {
      result += yCommand(shape, command, startY, endY);
    }
    if (startW != endW) {
      result += widthCommand(shape, command, startW, endW);
    }
    if (startH != endH) {
      result += heightCommand(shape, command, startH, endH);
    }
    if (startR != endR || startG != endG || startB != endB) {
      result += colorCommand(command, startR, endR, startG, endG, startB, endB);
    }

    return result;
  }


  /**
   * Returns the SVG type of the shape (ie. rect ellipse).
   *
   * @param shape The shape whose SVG type will be returned.
   * @return String representation of the SVG type.
   */
  private String getSVGType(IShape shape) {
    if (shape.getBasicDesc().equals("rectangle")) {
      return "rect";
    } else if (shape.getBasicDesc().equals("ellipse")) {
      return "ellipse";
    } else {
      throw new IllegalArgumentException("Not given a valid shape");
    }
  }

  /**
   * Returns the description of the shape for SVG declaration.
   *
   * @param shape shape that is being turned into SVG declaration.
   * @return String representing SVG declaration.
   */
  private String getSVGDesc(IShape shape) {
    String result = "";
    if (shape.getBasicDesc().equals("rectangle")) {
      result = "x=\"" + shape.getX()
              + "\" y=\"" + shape.getY()
              + "\" width=\"" + shape.getWidth()
              + "\" height=\"" + shape.getHeight()
              + "\" fill=\"rgb(" + shape.getRed()
              + "," + shape.getBlue()
              + "," + shape.getGreen() + ")\""
              + " visibility=";

      if (shape.isVisible()) {
        result = result + "\"visible\"";
      } else {
        result = result + "\"hidden\"";
      }
    } else if (shape.getBasicDesc().equals("ellipse")) {
      result = "cx=\"" + shape.getX()
              + "\" cy=\"" + shape.getY()
              + "\" rx=\"" + shape.getWidth()
              + "\" ry=\"" + shape.getHeight()
              + "\" fill=\"rgb(" + shape.getRed()
              + "," + shape.getBlue()
              + "," + shape.getGreen() + ")\""
              + " visibility=\"";

      if (shape.isVisible()) {
        result = result + "visible\"";
      } else {
        result = result + "hidden\"";
      }
    }

    return result;
  }

  /**
   * Creates a String representing an SVG command for a motion in the x direction.
   *
   * @param shape  Shape that is being acted upon.
   * @param cmd    Command that is acting upon it.
   * @param startX Starting X of shape.
   * @param endX   Ending X of shape.
   * @return String representing an SVG command for a motion in the x direction
   */
  private String xCommand(IShape shape, ICommand cmd, int startX, int endX) {

    String start = "<animate attributeType=\"xml\" begin=\"" +
            +cmd.getStartTick() * speed
            + "ms\" dur=\""
            + (cmd.getEndTick() - cmd.getStartTick()) * speed
            + "ms\" attributeName=\"";

    if (shape.getBasicDesc().equals("ellipse")) {
      start += "cx";
    } else {
      start += "x";
    }

    start += "\" from=\""
            + startX
            + "\" to=\"";
    start += endX
            + "\" fill=\"freeze\" />\n";

    return start;
  }

  /**
   * Creates a String representing an SVG command for a motion in the y direction.
   *
   * @param shape  Shape that is being acted upon.
   * @param cmd    Command that is acting upon it.
   * @param startY Starting Y of shape.
   * @param endY   Ending Y of shape.
   * @return String representing an SVG command for a motion in the y direction
   */
  private String yCommand(IShape shape, ICommand cmd, int startY, int endY) {

    String start = "<animate attributeType=\"xml\" begin=\""
            + cmd.getStartTick() * speed
            + "ms\" dur=\""
            + (cmd.getEndTick() - cmd.getStartTick()) * speed
            + "ms\" attributeName=\"";

    if (getSVGType(shape).equals("ellipse")) {
      start += "cy";
    } else {
      start += "y";
    }

    start += "\" from=\""
            + startY
            + "\" to=\"";
    start += endY
            + "\" fill=\"freeze\" />\n";

    return start;
  }

  /**
   * Creates a String representing an SVG command for a motion in the width direction.
   *
   * @param shape      Shape that is being acted upon.
   * @param cmd        Command that is acting upon it.
   * @param startWidth Starting width of shape.
   * @param endWidth   Ending width of shape.
   * @return String representing an SVG command for a motion in the width direction
   */
  private String widthCommand(IShape shape, ICommand cmd, int startWidth, int endWidth) {

    String start = "<animate attributeType=\"xml\" begin=\""
            + cmd.getStartTick() * speed
            + "ms\" dur=\""
            + (cmd.getEndTick() - cmd.getStartTick()) * speed
            + "ms\" attributeName=\"";

    if (shape.getBasicDesc().equals("ellipse")) {
      start += "rx";
    } else {
      start += "width";
    }

    start += "\" from=\""
            + startWidth
            + "\" to=\"";
    start += endWidth
            + "\" fill=\"freeze\" />\n";

    return start;
  }

  /**
   * Creates a String representing an SVG command for a motion in the height direction.
   *
   * @param shape       Shape that is being acted upon.
   * @param cmd         Command that is acting upon it.
   * @param startHeight Starting height of shape.
   * @param endHeight   Ending height of shape.
   * @return String representing an SVG command for a motion in the height direction
   */
  private String heightCommand(IShape shape, ICommand cmd, int startHeight, int endHeight) {

    String start = "<animate attributeType=\"xml\" begin=\""
            + cmd.getStartTick() * speed
            + "ms\" dur=\""
            + (cmd.getEndTick() - cmd.getStartTick()) * speed
            + "ms\" attributeName=\"";

    if (shape.getBasicDesc().equals("ellipse")) {
      start += "ry";
    } else {
      start += "height";
    }

    start += "\" from=\""
            + startHeight
            + "\" to=\"";
    start += endHeight
            + "\" fill=\"freeze\" />\n";

    return start;
  }


  /**
   * Creates a String representing an SVG command for a color change.
   *
   * @param cmd    Command that is acting upon it.
   * @param startR Starting red.
   * @param endR   Ending red.
   * @param startG Starting green.
   * @param endG   Ending green.
   * @param startB Strating blue.
   * @param endB   Ending blue.
   * @return String representing an SVG command for a color change.
   */
  private String colorCommand(ICommand cmd, int startR, int endR,
                              int startG, int endG, int startB, int endB) {

    String start = "<animate attributeName = \"fill\" "
            + "attributeType=\"xml\" begin=\""
            + cmd.getStartTick() * speed
            + "ms\" dur=\""
            + (cmd.getEndTick() - cmd.getStartTick()) * speed
            + "ms\"";

    start += " from=\""
            + "rgb("
            + startR
            + ","
            + startG
            + ","
            + startB
            + ")\" to=\"";
    start += "rgb("
            + endR
            + ","
            + endG
            + ","
            + endB
            + ")\" fill=\"freeze\" />\n";

    return start;
  }


  /**
   * Returns a String that represents a visibility change in SVG.
   *
   * @param startVis Is the shape visible at the beginning of the command.
   * @param endVis   Is the shape visible at the end of the command.
   * @param cmd      The command that is being executed.
   * @return String representing a visibility change in SVG command terms.
   */
  private String commandVisibility(boolean startVis, boolean endVis, ICommand cmd) {
    String result = "";

    if (startVis && endVis) {
      return result;
    } else {
      result += "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"";

      if (startVis) {
        result += "hidden\" ";
      } else {
        result += "visible\" ";
      }
    }

    result += "begin =\""
            + (cmd.getStartTick() * speed)
            + "ms\" />\n";

    return result;
  }


}
