import org.junit.Before;
import org.junit.Test;

import java.nio.CharBuffer;

import cs3500.Animator.Model.AnimatorModel.AnimatorModelImpl;
import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Util.AnimationReader;
import cs3500.Animator.View.IAnimatorView;
import cs3500.Animator.View.SVGView;

import static org.junit.Assert.assertEquals;

public class TestAnimatorSVGView {

  IAnimatorModel modelSimple;
  IAnimatorModel modelToh;

  @Before
  public void initModels() {
    String simpleIn = "# initializes the canvas, with top-left corner (200,70) and\n" +
            "# dimensions 360x360\n" +
            "canvas 200 70 360 360\n" +
            "# declares a rectangle shape named R\n" +
            "shape R rectangle\n" +
            "# describes the motions of shape R, between two moments of animation:\n" +
            "# t == tick\n" +
            "# (x,y) == position\n" +
            "# (w,h) == dimensions\n" +
            "# (r,g,b) == color (with values between 0 and 255)\n" +
            "#                  start                           end\n" +
            "#        --------------------------    ----------------------------\n" +
            "#        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b\n" +
            "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n" +
            "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n" +
            "motion R 50 300 300 50 100 255 0  0    51  300 300 50 100 255 0  0\n" +
            "motion R 51 300 300 50 100 255 0  0    70  300 300 25 100 255 0  0\n" +
            "motion R 70 300 300 25 100 255 0  0    100 200 200 25 100 255 0  0\n" +
            "\n" +
            "shape C ellipse\n" +
            "motion C 6  440 70 120 60 0 0 255 # start state\n" +
            "         20 440 70 120 60 0 0 255 # end state\n" +
            "motion C 20 440 70 120 60 0 0 255      50 440 250 120 60 0 0 255\n" +
            "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 170 85\n" +
            "motion C 70 440 370 120 60 0 170 85    80 440 370 120 60 0 255 0\n" +
            "motion C 80 440 370 120 60 0 255 0     100 440 370 120 60 0 255 0";
    this.modelSimple = AnimationReader.parseFile(CharBuffer.wrap(simpleIn),
            new AnimatorModelImpl.AnimatorBuilder());
    String toh3 = "canvas 145 50 410 220\n" +
            "shape disk1 rectangle\n" +
            "shape disk2 rectangle\n" +
            "shape disk3 rectangle\n" +
            "motion disk1 1 190 180 20 30 0 49 90  1 190 180 20 30 0 49 90\n" +
            "motion disk1 1 190 180 20 30 0 49 90  25 190 180 20 30 0 49 90\n" +
            "motion disk2 1 167 210 65 30 6 247 41  1 167 210 65 30 6 247 41\n" +
            "motion disk2 1 167 210 65 30 6 247 41  57 167 210 65 30 6 247 41\n" +
            "motion disk3 1 145 240 110 30 11 45 175  1 145 240 110 30 11 45 175\n" +
            "motion disk3 1 145 240 110 30 11 45 175  121 145 240 110 30 11 45 175\n" +
            "motion disk1 25 190 180 20 30 0 49 90  35 190 50 20 30 0 49 90\n" +
            "motion disk1 35 190 50 20 30 0 49 90  36 190 50 20 30 0 49 90\n" +
            "motion disk1 36 190 50 20 30 0 49 90  46 490 50 20 30 0 49 90\n" +
            "motion disk1 46 490 50 20 30 0 49 90  47 490 50 20 30 0 49 90\n" +
            "motion disk1 47 490 50 20 30 0 49 90  57 490 240 20 30 0 49 90\n" +
            "motion disk1 57 490 240 20 30 0 49 90  89 490 240 20 30 0 49 90\n" +
            "motion disk2 57 167 210 65 30 6 247 41  67 167 50 65 30 6 247 41\n" +
            "motion disk2 67 167 50 65 30 6 247 41  68 167 50 65 30 6 247 41\n" +
            "motion disk2 68 167 50 65 30 6 247 41  78 317 50 65 30 6 247 41\n" +
            "motion disk2 78 317 50 65 30 6 247 41  79 317 50 65 30 6 247 41\n" +
            "motion disk2 79 317 50 65 30 6 247 41  89 317 240 65 30 6 247 41\n" +
            "motion disk1 89 490 240 20 30 0 49 90  99 490 50 20 30 0 49 90\n" +
            "motion disk2 89 317 240 65 30 6 247 41  185 317 240 65 30 6 247 41\n" +
            "motion disk1 99 490 50 20 30 0 49 90  100 490 50 20 30 0 49 90\n" +
            "motion disk1 100 490 50 20 30 0 49 90  110 340 50 20 30 0 49 90\n" +
            "motion disk1 110 340 50 20 30 0 49 90  111 340 50 20 30 0 49 90\n" +
            "motion disk1 111 340 50 20 30 0 49 90  121 340 210 20 30 0 49 90\n" +
            "motion disk1 121 340 210 20 30 0 49 90  153 340 210 20 30 0 49 90\n" +
            "motion disk3 121 145 240 110 30 11 45 175  131 145 50 110 30 11 45 175\n" +
            "motion disk3 131 145 50 110 30 11 45 175  132 145 50 110 30 11 45 175\n" +
            "motion disk3 132 145 50 110 30 11 45 175  142 445 50 110 30 11 45 175\n" +
            "motion disk3 142 445 50 110 30 11 45 175  143 445 50 110 30 11 45 175\n" +
            "motion disk3 143 445 50 110 30 11 45 175  153 445 240 110 30 11 45 175\n" +
            "motion disk1 153 340 210 20 30 0 49 90  163 340 50 20 30 0 49 90\n" +
            "motion disk3 153 445 240 110 30 11 45 175  161 445 240 110 30 0 255 0\n" +
            "motion disk3 161 445 240 110 30 0 255 0  302 445 240 110 30 0 255 0\n" +
            "motion disk1 163 340 50 20 30 0 49 90  164 340 50 20 30 0 49 90\n" +
            "motion disk1 164 340 50 20 30 0 49 90  174 190 50 20 30 0 49 90\n" +
            "motion disk1 174 190 50 20 30 0 49 90  175 190 50 20 30 0 49 90\n" +
            "motion disk1 175 190 50 20 30 0 49 90  185 190 240 20 30 0 49 90\n" +
            "motion disk1 185 190 240 20 30 0 49 90  217 190 240 20 30 0 49 90\n" +
            "motion disk2 185 317 240 65 30 6 247 41  195 317 50 65 30 6 247 41\n" +
            "motion disk2 195 317 50 65 30 6 247 41  196 317 50 65 30 6 247 41\n" +
            "motion disk2 196 317 50 65 30 6 247 41  206 467 50 65 30 6 247 41\n" +
            "motion disk2 206 467 50 65 30 6 247 41  207 467 50 65 30 6 247 41\n" +
            "motion disk2 207 467 50 65 30 6 247 41  217 467 210 65 30 6 247 41\n" +
            "motion disk1 217 190 240 20 30 0 49 90  227 190 50 20 30 0 49 90\n" +
            "motion disk2 217 467 210 65 30 6 247 41  225 467 210 65 30 0 255 0\n" +
            "motion disk2 225 467 210 65 30 0 255 0  302 467 210 65 30 0 255 0\n" +
            "motion disk1 227 190 50 20 30 0 49 90  228 190 50 20 30 0 49 90\n" +
            "motion disk1 228 190 50 20 30 0 49 90  238 490 50 20 30 0 49 90\n" +
            "motion disk1 238 490 50 20 30 0 49 90  239 490 50 20 30 0 49 90\n" +
            "motion disk1 239 490 50 20 30 0 49 90  249 490 180 20 30 0 49 90\n" +
            "motion disk1 249 490 180 20 30 0 49 90  257 490 180 20 30 0 255 0\n" +
            "motion disk1 257 490 180 20 30 0 255 0  302 490 180 20 30 0 255 0";
    this.modelToh = AnimationReader.parseFile(CharBuffer.wrap(toh3),
            new AnimatorModelImpl.AnimatorBuilder());
  }

  // tests that bad constructors throw errors
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstruct() {
    Appendable sb = new StringBuilder();
    IAnimatorView view = new SVGView(null, 10, sb);
  }

  // tests that bad constructors throw errors
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstruct2() {
    IAnimatorView view = new SVGView(modelSimple, 10, null);
  }

  // tests that io exception thrown by appendable causes an IllegalStateException to be thrown.
  @Test(expected = IllegalStateException.class)
  public void testBadAppendable() {
    IAnimatorView view = new SVGView(modelSimple, 10, new AppendableThrowsException());
    view.animate();
  }

  // tests that the output in the appendable is correct.
  @Test
  public void testOutput() {
    Appendable sb = new StringBuilder();
    IAnimatorView view = new SVGView(modelSimple, 10, sb);
    view.animate();
    assertEquals("<svg width=\"560\" height=\"430\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\" >\n" +
            "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" begin =\"6ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"60ms\" dur=\"240ms\" attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"60ms\" dur=\"240ms\" attributeName=\"y\" from=\"200\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"306ms\" dur=\"114ms\" attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"420ms\" dur=\"180ms\" attributeName=\"x\" from=\"300\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"420ms\" dur=\"180ms\" attributeName=\"y\" from=\"300\" to=\"200\" fill=\"freeze\" />\n" +
            "</rect><ellipse id=\"C\" cx=\"440\" cy=\"70\" rx=\"120\" ry=\"60\" fill=\"rgb(0,255,0)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" begin =\"36ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"120ms\" dur=\"180ms\" attributeName=\"cy\" from=\"70\" to=\"250\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"120ms\" attributeName=\"cy\" from=\"250\" to=\"370\" fill=\"freeze\" />\n" +
            "<animate attributeName = \"fill\" attributeType=\"xml\" begin=\"300ms\" dur=\"120ms\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\" />\n" +
            "<animate attributeName = \"fill\" attributeType=\"xml\" begin=\"420ms\" dur=\"60ms\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>", sb.toString());
  }

  // tests that the output in the appendable is correct with a different speed;
  @Test
  public void testOutput1() {
    Appendable sb = new StringBuilder();
    IAnimatorView view = new SVGView(modelSimple, 15, sb);
    view.animate();
    assertEquals("<svg width=\"560\" height=\"430\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\" >\n" +
            "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"" +
            "rgb(255,0,0)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" " +
            "begin =\"6ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"60ms\" dur=\"240ms\" attributeName=\"x\" " +
            "from=\"200\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"306ms\" dur=\"114ms\" " +
            "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"420ms\" dur=\"180ms\" attributeName=\"x\" " +
            "from=\"300\" to=\"200\" fill=\"freeze\" />\n" +
            "</rect><ellipse id=\"C\" cx=\"440\" cy=\"70\" rx=\"120\" ry=\"60\" fill=\"" +
            "rgb(0,255,0)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" " +
            "begin =\"36ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"120ms\" dur=\"180ms\" attributeName=\"cy\" " +
            "from=\"70\" to=\"250\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"120ms\" attributeName=\"cy\" " +
            "from=\"250\" to=\"370\" fill=\"freeze\" />\n" +
            "<animate attributeName = \"fill\" attributeType=\"xml\" begin=\"420ms\" " +
            "dur=\"60ms\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>", sb.toString());
  }


  // tests that the output in the appendable is correct for a different, longer file.
  @Test
  public void testOutput2() {
    Appendable sb = new StringBuilder();
    IAnimatorView view = new SVGView(modelToh, 10, sb);
    view.animate();
    assertEquals("<svg width=\"555\" height=\"270\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\" >\n" +
            "<rect id=\"disk1\" x=\"190\" y=\"180\" width=\"20\" height=\"30\" fill=\"" +
            "rgb(0,90,49)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" " +
            "begin =\"10ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"250ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"180\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"360ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"190\" to=\"490\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"470ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"240\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"890ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"240\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"490\" to=\"340\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1110ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"210\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1530ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"210\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1640ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"340\" to=\"190\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1750ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"240\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2170ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"240\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2280ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"190\" to=\"490\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2390ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"180\" fill=\"freeze\" />\n" +
            "<animate attributeName = \"fill\" attributeType=\"xml\" begin=\"2490ms\" " +
            "dur=\"80ms\" from=\"rgb(0,49,90)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n" +
            "</rect><rect id=\"disk2\" x=\"167\" y=\"210\" width=\"65\" height=\"30\" " +
            "fill=\"rgb(6,41,247)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" " +
            "begin =\"10ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"570ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"210\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"680ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"167\" to=\"317\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"790ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"240\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1850ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"240\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1960ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"317\" to=\"467\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2070ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"210\" fill=\"freeze\" />\n" +
            "<animate attributeName = \"fill\" attributeType=\"xml\" begin=\"2170ms\" " +
            "dur=\"80ms\" from=\"rgb(6,247,41)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n" +
            "</rect><rect id=\"disk3\" x=\"145\" y=\"240\" width=\"110\" height=\"30\" " +
            "fill=\"rgb(11,175,45)\" visibility=\"hidden\" >\n" +
            "<set  attributeName=\"visibility\" attributeType=\"xml\" to=\"visible\" " +
            "begin =\"10ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1210ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"240\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1320ms\" dur=\"100ms\" attributeName=\"x\" " +
            "from=\"145\" to=\"445\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1430ms\" dur=\"100ms\" attributeName=\"y\" " +
            "from=\"50\" to=\"240\" fill=\"freeze\" />\n" +
            "<animate attributeName = \"fill\" attributeType=\"xml\" begin=\"1530ms\" " +
            "dur=\"80ms\" from=\"rgb(11,45,175)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "</svg>", sb.toString());
  }
}
