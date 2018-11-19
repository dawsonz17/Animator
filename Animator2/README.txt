
View

Interfaces:
IAnimatorView- This is the interface for our view. It will allow the user the ability to see what a
model being run looks like by using the only method within the class, animate. Animate is intended
to run the model and output it in a way that allows the user to visualize the animation, whether
that be visually or textually.

Classes:
SVGView: This view will append svg commands to an appendage that can then be written to a file that
can then be run. To implement this we run through the shapes and commands of each shape and turn
the commands into an svg command for that movement.

TextView: Text view is very similar to the svg view in that it outputs in text but the text is in
the form that the homework specifies.

Visual View: The visual view uses the swing platform to produce images that are run in order to
create a true animation. It constantly updates the tick and repaints the animation after each tick.
 It uses the class Animator panel to draw to a Frame which allows for the animation to be seen
 in an applet.

AnimatorPanel: This is the class that iterates through shapes and commands to make the shapes that
 are used in the drawing of the animation for each individual tick.

Excellence:
Parses through given arguments to make a model and view that can be run.


Changes to Model:

The biggest change we made to the model were the addition of builders. This enables us to make
shapes and animators that have all final fields, which makes our objects immutable.
One big change to the model was that we made it so you could iterate through just commands and
not just the ticks, this increases the time efficiency greatly for textual views.
We also made a new command Command change all that makes a command that can change all of the
attributes at once, allowing for an infinite amount of commands to  be made from parsing through
text files. We also made a command that flips the visibility of the shapes.

Another important change that we made to the model was the addition of the methods that we were
missing in the previous assignment, namely adding and removing commands from a shape and adding
and removing shapes from the animator.


Our design is based off of the idea that an animation holds a list of shapes and a shape holds a
    list of commands that act upon that shape. One of our main design principles was that
    all of thechanges occurring to all shapes in our animation should occur chronologically
    rather than by shape so that we could stop the animation at any point we wanted to
     take as stills of the
    animation

This makes our interfaces:
IAnimatorModel-This interface sets up a way to control the animation by calling runAnimation on the
    list of shapes.
ICommand- An ICommand is meant to modify a Shape when implemented in a way that is normal for an
    animator(ie movement,
resizing, color change, staying still)
IShape-The IShape interface is meant to represent a shape that can be acted upon in an animation.

Our Abstract classes are:
AShape: The AShape class stores all the common methods to shapes, like being drawn and calling for
    their commands to act upon them. AShapes all store vector like structures that label things
    like the x y width height red blue and green aspects of the shape as well as the velocity of
    their change.
ACommand: ACommand can print commands and reset the velocities of the vectors of an AShape. It can
    apply its command to the shape as well to find the values before and after the command have
    acted upon the shape.

Concrete Classes:
AnimatorModelImpl: Runs the animation by iterating through the list of shapes and allowing them to
    have their commands done to them.
CommandMove: Command that moves a shape from its current x y to another x y over a set amount of
    time.
CommandRecolor: Command that changes the color of a shape to a given color over a set amount of
    time.
CommandResize: Command that changes the width and height of a shape to the given width and height
    over a certain amount of time.
CommandStay: Command that tells the shape to do nothing over a certain amount of time.
CommandStateEnum: Enum that represents the state of an ACommand, whether before it has started,
    during it, or after the command has finished.
OvalImpl: Represents an oval or ellipse.
RectangleImpl: Represents a rectangle.
Vector: represents a value and the change in the value. Enables the movement of all a shapes value
    over a certain amount of time by adding the velocity to the value over and over.