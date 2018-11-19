
import org.junit.Test;

import cs3500.Animator.Model.Shape.*;
import cs3500.Animator.Model.Command.*;
import cs3500.Animator.Model.AnimatorModel.*;

import static org.junit.Assert.assertEquals;

import cs3500.Animator.View.*;

public class TestAnimatorModelImpl {


  //test empty list of shapes.
  @Test
  public void testAnimatorConstructor2() {
    AnimatorModelImpl animator = new AnimatorModelImpl.AnimatorBuilder().build();
    Appendable a = new StringBuilder();
    IAnimatorView view = new TextView(animator, a);
    view.animate();
    assertEquals("canvas 0 0 0 0\n",
            a.toString());
  }


  //tests null Name when constructing AShape
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    IShape a = new AShape.ShapeBuilder(null, "rectangle").build();
  }

  //tests undefined type when constructing AShape
  @Test(expected = IllegalStateException.class)
  public void testNullType() {
    IShape a = new AShape.ShapeBuilder("name", "a").build();
  }

  //tests null Name when constructing AShape
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCommands() {
    IAnimatorModel model = new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle")
            .addMotion("r", 0, 0, 0, 10, 10, 0, 0, 0, 10, 0, 0, 10, 10, 0, 0, 0)
            .addMotion("r", 1, 0, 0, 10, 10, 0, 0, 0, 10, 0, 0, 10, 10, 0, 0, 0).build();
  }


  //test empty list of commands
  @Test
  public void testEmptyListCommands() {
    IAnimatorModel animator =
            new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle").build();

    Appendable a = new StringBuilder();
    IAnimatorView view = new TextView(animator, a);
    view.animate();
    assertEquals("canvas 0 0 0 0\n" +
            "shape r rectangle\n", a.toString());

  }


  //tests move and stay for one Rectangle
  @Test
  public void testPlayAnimation1() {

    AnimatorModelImpl.AnimatorBuilder builder = new AnimatorModelImpl.AnimatorBuilder();

    IAnimatorModel animator =
            builder.declareShape("r", "rectangle")
                    .addMotion("r", 0, 200, 200, 50, 100, 255, 0, 0,
                            10, 10, 200, 50, 100, 255, 0, 0)
                    .addMotion("r", 10, 10, 200, 50, 100, 255, 0, 0,
                            20, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("r", 20, 300, 300, 50, 100, 255, 0, 0,
                            45, 300, 300, 50, 100, 255, 0, 0).build();


    Appendable a = new StringBuilder();
    IAnimatorView view = new TextView(animator, a);
    view.animate();

    assertEquals("canvas 0 0 0 0\n" +
                    "shape r rectangle\n" +
                    "motion r 0 200 200 50 100 255 0 0   0 200 200 50 100 255 0 0\n" +
                    "motion r 0 200 200 50 100 255 0 0   10 10 200 50 100 255 0 0\n" +
                    "motion r 10 10 200 50 100 255 0 0   20 300 300 50 100 255 0 0\n" +
                    "motion r 20 300 300 50 100 255 0 0   45 300 300 50 100 255 0 0\n",
            a.toString());
  }


  //tests move and stay for a rectangle and an oval
  @Test
  public void testPlayAnimation2() {

    IAnimatorModel animator =
            new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle")
                    .declareShape("c", "ellipse")
                    .addMotion("r", 0, 200, 200, 50, 100, 255, 0, 0,
                            10, 10, 200, 50, 100, 255, 0, 0)
                    .addMotion("r", 10, 10, 200, 50, 100, 255, 0, 0,
                            20, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("r", 20, 300, 300, 50, 100, 255, 0, 0,
                            45, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("c", 0, 440, 70, 60, 120, 0, 0, 255, 10,
                            100, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 20, 200, 100, 60, 120, 0, 0, 255, 45,
                            200, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 10, 100, 100, 60, 120, 0, 0, 255, 20,
                            200, 100, 60, 120, 0, 0, 255).build();


    Appendable a = new StringBuilder();
    IAnimatorView view = new TextView(animator, a);
    view.animate();

    assertEquals("canvas 0 0 0 0\n" +
                    "shape r rectangle\n" +
                    "motion r 0 200 200 50 100 255 0 0   0 200 200 50 100 255 0 0\n" +
                    "motion r 0 200 200 50 100 255 0 0   10 10 200 50 100 255 0 0\n" +
                    "motion r 10 10 200 50 100 255 0 0   20 300 300 50 100 255 0 0\n" +
                    "motion r 20 300 300 50 100 255 0 0   45 300 300 50 100 255 0 0\n" +
                    "shape c ellipse\n" +
                    "motion c 0 440 70 60 120 0 255 0   0 440 70 60 120 0 255 0\n" +
                    "motion c 0 440 70 60 120 0 255 0   10 100 100 60 120 0 255 0\n" +
                    "motion c 10 100 100 60 120 0 255 0   20 200 100 60 120 0 255 0\n" +
                    "motion c 20 200 100 60 120 0 255 0   45 200 100 60 120 0 255 0\n",
            a.toString());
  }


  //tests move and stay for a rectangle and an oval
  @Test
  public void testAddShapeAndResetAnimation() {

    IAnimatorModel animator =
            new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle")
                    .addMotion("r", 0, 200, 200, 50, 100, 255, 0, 0,
                    10, 10, 200, 50, 100, 255, 0, 0)
                    .addMotion("r", 10, 10, 200, 50, 100, 255, 0, 0,
                            20, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("r", 20, 300, 300, 50, 100, 255, 0, 0,
                            45, 300, 300, 50, 100, 255, 0, 0)
                    .declareShape("c", "ellipse")
                    .addMotion("c", 0, 440, 70, 60, 120, 0, 0, 255, 10,
                            100, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 20, 200, 100, 60, 120, 0, 0, 255, 45,
                            200, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 10, 100, 100, 60, 120, 0, 0, 255, 20,
                            200, 100, 60, 120, 0, 0, 255).build();

    //animator.addShape(new AShape.ShapeBuilder("x", "rectangle").build());
    animator.resetAnimation();

    assertEquals(3, animator.getShapes().size());
  }

  //tests move and stay for a rectangle and an oval
  @Test
  public void testAddCommand() {

    IAnimatorModel animator =
            new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle")
                    .addMotion("r", 5, 200, 200, 50, 100, 255, 0, 0,
                            10, 10, 200, 50, 100, 255, 0, 0)
                    .addMotion("r", 10, 10, 200, 50, 100, 255, 0, 0,
                            20, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("r", 20, 300, 300, 50, 100, 255, 0, 0,
                            45, 300, 300, 50, 100, 255, 0, 0)
                    .declareShape("c", "ellipse")
                    .addMotion("c", 0, 440, 70, 60, 120, 0, 0, 255, 10,
                            100, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 20, 200, 100, 60, 120, 0, 0, 255, 45,
                            200, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 10, 100, 100, 60, 120, 0, 0, 255, 20,
                            200, 100, 60, 120, 0, 0, 255).build();

    animator.addKeyFrame("c", 50, 0, 0, 1, 1, 0, 0, 0);

    animator.addKeyFrame("r", 0, 0, 0, 1, 1, 0, 0, 0);

    //animator.addCommand(animator.getShapes().get(0), new CommandMove(5, 15, 30, 20));

    Appendable a = new StringBuilder();
    IAnimatorView view = new TextView(animator, a);
    view.animate();

    assertEquals("canvas 0 0 0 0\n" +
                    "shape r rectangle\n" +
                    "motion r 0 200 200 50 100 255 0 0   0 200 200 50 100 255 0 0\n" +
                    "motion r 0 200 200 50 100 255 0 0   10 10 200 50 100 255 0 0\n" +
                    "motion r 10 10 200 50 100 255 0 0   20 300 300 50 100 255 0 0\n" +
                    "motion r 20 300 300 50 100 255 0 0   45 300 300 50 100 255 0 0\n" +
                    "motion r 45 300 300 50 100 255 0 0   50 30 20 50 100 255 0 0\n" +
                    "shape c ellipse\n" +
                    "motion c 0 440 70 60 120 0 255 0   0 440 70 60 120 0 255 0\n" +
                    "motion c 0 440 70 60 120 0 255 0   10 100 100 60 120 0 255 0\n" +
                    "motion c 10 100 100 60 120 0 255 0   20 200 100 60 120 0 255 0\n" +
                    "motion c 20 200 100 60 120 0 255 0   45 200 100 60 120 0 255 0\n",
            a.toString());
  }


  //Test remove shape
  @Test
  public void testRemoveShape() {
    IAnimatorModel animator =
            new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle")
                    .declareShape("c", "ellipse")
                    .addMotion("r", 0, 200, 200, 50, 100, 255, 0, 0,
                            10, 10, 200, 50, 100, 255, 0, 0)
                    .addMotion("r", 10, 10, 200, 50, 100, 255, 0, 0,
                            20, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("r", 20, 300, 300, 50, 100, 255, 0, 0,
                            45, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("c", 0, 440, 70, 60, 120, 0, 0, 255, 10,
                            100, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 20, 200, 100, 60, 120, 0, 0, 255, 45,
                            200, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 10, 100, 100, 60, 120, 0, 0, 255, 20,
                            200, 100, 60, 120, 0, 0, 255).build();

    animator.removeShape(animator.getShapes().get(0));
    animator.resetAnimation();

    assertEquals(1, animator.getShapes().size());
  }


  //Test remove command
  @Test
  public void testRemoveCommand() {

    IAnimatorModel animator =
            new AnimatorModelImpl.AnimatorBuilder().declareShape("r", "rectangle")
                    .declareShape("c", "ellipse")
                    .addMotion("r", 0, 200, 200, 50, 100, 255, 0, 0,
                            10, 10, 200, 50, 100, 255, 0, 0)
                    .addMotion("r", 10, 10, 200, 50, 100, 255, 0, 0,
                            20, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("r", 20, 300, 300, 50, 100, 255, 0, 0,
                            45, 300, 300, 50, 100, 255, 0, 0)
                    .addMotion("c", 0, 440, 70, 60, 120, 0, 0, 255, 10,
                            100, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 20, 200, 100, 60, 120, 0, 0, 255, 45,
                            200, 100, 60, 120, 0, 0, 255)
                    .addMotion("c", 10, 100, 100, 60, 120, 0, 0, 255, 20,
                            200, 100, 60, 120, 0, 0, 255).build();

    animator.removeCommand(animator.getShapes().get(0),
            animator.getShapes().get(0).getCommands().get(1));

    animator.resetAnimation();
    Appendable a = new StringBuilder();
    IAnimatorView view = new TextView(animator, a);
    view.animate();

    assertEquals("canvas 0 0 0 0\n" +
                    "shape r rectangle\n" +
                    "motion r 0 200 200 50 100 255 0 0   0 200 200 50 100 255 0 0\n" +
                    "motion r 10 200 200 50 100 255 0 0   20 300 300 50 100 255 0 0\n" +
                    "motion r 20 300 300 50 100 255 0 0   45 300 300 50 100 255 0 0\n" +
                    "shape c ellipse\n" +
                    "motion c 0 440 70 60 120 0 255 0   0 440 70 60 120 0 255 0\n" +
                    "motion c 0 440 70 60 120 0 255 0   10 100 100 60 120 0 255 0\n" +
                    "motion c 10 100 100 60 120 0 255 0   20 200 100 60 120 0 255 0\n" +
                    "motion c 20 200 100 60 120 0 255 0   45 200 100 60 120 0 255 0\n",
            a.toString());
  }
}
