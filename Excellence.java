package cs3500.Animator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

import cs3500.Animator.Controller.AnimatorControllerImpl;
import cs3500.Animator.Controller.IAnimatorController;
import cs3500.Animator.Model.AnimatorModel.AnimatorModelImpl;
import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Util.AnimationReader;
import cs3500.Animator.View.EditView;
import cs3500.Animator.View.IAnimatorView;
import cs3500.Animator.View.IInteractiveAnimatorView;
import cs3500.Animator.View.SVGView;
import cs3500.Animator.View.TextView;
import cs3500.Animator.View.VisualView;

/**
 * This class represents an Animator as a whole, which can generate an animation based off a
 * correctly formatted input txt file and can generate an animation which can be viewed as a text
 * description, as an svg file, or as a a visual demonstration of the animation in a GUI.
 */
public class Excellence {

  /**
   * The runnable method that can create and run an animation, using the user's input to build and
   * run an animation, which can be viewed as a text file, as an svg file, or as a visual
   * representation of the animation, which will open in a seperate window. The user must enter 2
   * pieces of information in the input Array for this function to work, and can optionally enter
   * two further pieces of information in any order they choose: 1. Which view this function should
   * display. This is defined by typing "-view" followed by one of the following, representing
   * whichever view the user would like to see: - "text" for a text based description of the view. -
   * "svg" for the code to generate an SVG file containing the animation. - "visual" for a visual
   * representation of the model, which will open in a new window. 2. The input file for the
   * animation to read from. This is defined by typing "-in" followed by whichever input file the
   * user would like to use. This file must be predefined. 3. If the view is text or svg, the user
   * can choose an output file for this to write to by tying "-out" followed by some output file's
   * name. If the file does not already exist, this will create a new file of the given name. If
   * this field is undefined, this project will simply output its information to the terminal. 4. In
   * order to change the speed of the animation, the user can type "-speed" followed by a number
   * representing how many "ticks" per second the animation should run at. If this field is left
   * undefined, this program will default the value to 1 tick per second.
   *
   * @param args The Array of Strings which the user can use to tell the method what to do.
   */
  public static void main(String[] args) throws FileNotFoundException {

    IAnimatorModel model = AnimationReader.parseFile(
            new FileReader(new File("animationFiles/big-bang-big-crunch.txt")),
            new AnimatorModelImpl.AnimatorBuilder());
/*
    IAnimatorView view = new TextView(model, new PrintStream(System.out));
    view.animate();
    */

    IInteractiveAnimatorView view = new EditView(model);
    IAnimatorController controller = new AnimatorControllerImpl(model, view, 30);
    controller.startAnimation();

    /*
    IAnimatorModel model;
    IAnimatorView view;

    FileReader input;
    Appendable output;
    int speed;

    int typeIdx = indexOf(args, "-view");
    int inIdx = indexOf(args, "-in");
    int outIdx = indexOf(args, "-out");
    int speedIdx = indexOf(args, "-speed");

    if (typeIdx == -1 || inIdx == -1) {
      throw new IllegalArgumentException("User must specify a view and a input file");
    } else {
      try {
        input = new FileReader(new File(args[inIdx + 1]));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("The given input file does not exist");
      }
    }

    if (outIdx >= 0) {
      try {
        output = new PrintStream(new File(args[outIdx + 1]));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("The given output file cannot be defined");
      }
    } else {
      output = new PrintStream(System.out);
    }

    if (speedIdx >= 0) {
      speed = Integer.valueOf(args[speedIdx + 1]);
    } else {
      speed = 1;
    }

    model = AnimationReader.parseFile(input, new AnimatorModelImpl.AnimatorBuilder());

    switch (args[typeIdx + 1]) {
      case "text":
        view = new TextView(model, output);
        break;
      case "svg":
        view = new SVGView(model, speed, output);
        break;
      case "visual":
        view = new VisualView(model, speed);
        break;
      default:
        throw new IllegalArgumentException("Must enter a valid view type");
    }

    view.animate();
    */
  }

  /**
   * Helper function for finding the index of a String within an array of Strings.
   *
   * @param arr This is the array of Strings which we are indexing.
   * @param str This is the String which we are trying to find in the array of Strings.
   * @return returns the index of the String in the array of Strings. Returns -1 if the given String
   * is not within the array of Strings.
   */
  private static int indexOf(String[] arr, String str) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].equals(str)) {
        return i;
      }
    }
    return -1;
  }
}
