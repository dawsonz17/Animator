package cs3500.Animator.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.Animator.Controller.IAnimatorController;
import cs3500.Animator.Model.AnimatorModel.IAnimatorModel;
import cs3500.Animator.Model.Command.CommandChangeAll;
import cs3500.Animator.Model.Command.ICommand;
import cs3500.Animator.Model.Shape.IShape;
import cs3500.Animator.Util.UtilityFunctions;

public class EditView extends JFrame implements IInteractiveAnimatorView, KeyListener {

  private final JButton restartButton;
  private final JToggleButton loopToggleButton;
  private final JButton slowButton;
  private final JButton playPauseButton;
  private final JButton fastButton;

  private final JPanel topButtonPanel;
  private final JPanel bottomButtonPanel;
  private final JPanel interactivePanel;
  private final JPanel animationPanel;
  private final JScrollPane animationScrollPane;

  private final JList<IShape> shapeList;
  private final JList<ICommand> commandList;
  private final JTextField tickTextField;
  private final JTextField xTextField;
  private final JTextField yTextField;
  private final JTextField widthTextField;
  private final JTextField heightTextField;
  private final JTextField rTextField;
  private final JTextField gTextField;
  private final JTextField bTextField;
  private final JLabel tickLabel;
  private final JLabel xLabel;
  private final JLabel yLabel;
  private final JLabel widthLabel;
  private final JLabel heightLabel;
  private final JLabel rLabel;
  private final JLabel gLabel;
  private final JLabel bLabel;
  private final JButton addShapeButton;
  private final JButton removeShapeButton;
  private final JButton addKeyframeButton;
  private final JButton removeKeyframeButton;

  private final JPanel shapeInputPanel;
  private final JPanel shapeInputFieldsPanel;
  private final JLabel nameLabel;
  private final JTextField nameField;
  private final JPanel typeSelectorPanel;
  private final JLabel typeLabel;
  private final ButtonGroup typeSelectorGroup;
  private final JRadioButton rectangleButton;
  private final JRadioButton ovalButton;

  private final JPanel shapeButtonPanel;
  private final JPanel keyFrameButtonPanel;
  private final JPanel inputPanel;
  private final JPanel setKeyframePanel;
  private final JPanel keyframePanel;
  private final JScrollPane shapeScrollPane;
  private final JScrollPane commandScrollPane;

  private final JProgressBar progressBar;

  private final IAnimatorModel model;
  private IAnimatorController controller;

  public EditView(IAnimatorModel model) {
    super();
    this.model = model;

    this.setTitle("Animating with Editing Capabilities!");
    //this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // overall layout
    this.setLayout(new BorderLayout());

    // ----------- INIT INTERACTIVE PANEL -----------------------------

    // interactivePanel - has all of the buttons that control the animation on it & also the
    // animation itself.
    this.interactivePanel = new JPanel();
    this.interactivePanel.setLayout(new BorderLayout());
    this.add(interactivePanel, BorderLayout.CENTER);

    // add animation itself:
    // NOTE TO SELF - ADD READ-ONLY MODEL IN LATER!!!
    this.animationPanel = new AnimatorPanel(this.model);
    this.animationPanel.setPreferredSize(new Dimension(model.getLeftX() + model.getWidth(),
            model.getUpperY() + model.getHeight()));
    this.animationScrollPane = new JScrollPane(animationPanel);
    this.interactivePanel.add(this.animationScrollPane, BorderLayout.EAST);

    // top button panel, directly above the animation
    this.topButtonPanel = new JPanel();
    this.topButtonPanel.setLayout(new FlowLayout());
    this.interactivePanel.add(this.topButtonPanel, BorderLayout.NORTH);

    // restart button, on top button panel
    this.restartButton = new JButton("Restart");
    this.restartButton.addActionListener(
            (ActionEvent e) -> this.controller.restartAnimation());
    topButtonPanel.add(this.restartButton);

    // loop toggle button, on top button panel
    this.loopToggleButton = new JToggleButton("Loop");
    this.loopToggleButton.addActionListener(
            (ActionEvent e) -> this.controller.toggleAnimationLoop());
    this.topButtonPanel.add(this.loopToggleButton);

    // button button panel, directly below the animation
    this.bottomButtonPanel = new JPanel();
    this.bottomButtonPanel.setLayout(new FlowLayout());
    this.interactivePanel.add(this.bottomButtonPanel, BorderLayout.SOUTH);

    // slow down button, on bottom button panel
    this.slowButton = new JButton("Slow Down");
    this.slowButton.addActionListener(
            (ActionEvent e) -> this.controller.slowDownAnimation());
    this.bottomButtonPanel.add(this.slowButton);

    // play pause button, on bottom button panel
    this.playPauseButton = new JButton("Play / Pause");
    this.playPauseButton.addActionListener(
            (ActionEvent e) -> this.controller.playPauseAnimation());
    this.bottomButtonPanel.add(this.playPauseButton);

    // speed up button, on bottom button panel
    this.fastButton = new JButton("Speed Up");
    this.fastButton.addActionListener(
            (ActionEvent e) -> this.controller.speedUpAnimation());
    this.bottomButtonPanel.add(this.fastButton);

    // ----------- INIT KEYFRAME PANEL -----------------------------

    // Keyframe panel that the user can use to add / remove keyframes from an animation
    this.keyframePanel = new JPanel();
    this.keyframePanel.setLayout(new BorderLayout());
    this.add(this.keyframePanel, BorderLayout.WEST);

    // ---- CENTER ------

    // JList of all commands of the selected IShape in shapeList
    this.commandList = new JList<ICommand>();
    this.commandList.addListSelectionListener(
            (ListSelectionEvent e) -> {
              ICommand command = this.commandList.getSelectedValue();
              if (command != null) {
                this.setFieldsIfPossible(command);
              }
            });
    this.commandScrollPane = new JScrollPane(this.commandList);
    this.keyframePanel.add(this.commandScrollPane, BorderLayout.EAST);

    // JList of all shapes
    this.shapeList = new JList<IShape>(this.model.getShapes().toArray(new IShape[0]));
    this.shapeList.addListSelectionListener(
            (ListSelectionEvent e) -> {
              IShape selection = this.shapeList.getSelectedValue();
              if (selection != null) {
                // set list of ICommands to that of chosen list
                this.commandList.setListData(
                        selection.getCommands().toArray(new ICommand[0]));
              } else {
                // reset list of ICommands so that it is empty if there is no selection
                this.commandList.setListData(new ICommand[0]);
              }
            });
    this.shapeScrollPane = new JScrollPane(this.shapeList);
    this.keyframePanel.add(this.shapeScrollPane, BorderLayout.WEST);

    // ---- NORTH ------

    // panel for adding or removing shapes from the shape list.
    this.shapeInputPanel = new JPanel();
    this.shapeInputPanel.setLayout(new BorderLayout());
    this.keyframePanel.add(this.shapeInputPanel, BorderLayout.NORTH);

    // panel for defining fields for new shapes
    this.shapeInputFieldsPanel = new JPanel();
    this.shapeInputFieldsPanel.setLayout(new FlowLayout());
    this.shapeInputPanel.add(this.shapeInputFieldsPanel, BorderLayout.SOUTH);

    // label so the user knows what the buttons are for.
    this.typeLabel = new JLabel("Choose Shape:");
    this.shapeInputFieldsPanel.add(this.typeLabel);

    // panel for displaying radio buttons on top of each other.
    this.typeSelectorPanel = new JPanel();
    this.typeSelectorPanel.setLayout(new BoxLayout(this.typeSelectorPanel, BoxLayout.PAGE_AXIS));
    this.shapeInputFieldsPanel.add(this.typeSelectorPanel);

    // button group so that only one of the radio buttons can be selected at once.
    this.typeSelectorGroup = new ButtonGroup();

    // radio button for if the user wants to make a rectangle shape
    this.rectangleButton = new JRadioButton("Rectangle", true);
    this.typeSelectorGroup.add(this.rectangleButton);
    this.typeSelectorPanel.add(this.rectangleButton);

    // radio button for if the user wants to make an oval shape
    this.ovalButton = new JRadioButton("Ellipse");
    this.typeSelectorGroup.add(this.ovalButton);
    this.typeSelectorPanel.add(this.ovalButton);

    // label so the user knows what the next field is for.
    this.nameLabel = new JLabel("Name:");
    this.shapeInputFieldsPanel.add(this.nameLabel);

    // text field so that the user can enter a name for their shape.
    this.nameField = new JTextField(8);
    this.shapeInputFieldsPanel.add(this.nameField);

    // shape button panel for adding a new shape or removing a selected shape
    this.shapeButtonPanel = new JPanel();
    this.shapeButtonPanel.setLayout(new FlowLayout());
    this.shapeInputPanel.add(this.shapeButtonPanel, BorderLayout.NORTH);

    // add shape button for adding a shape
    this.addShapeButton = new JButton("Add Shape");
    this.addShapeButton.addActionListener(
            (ActionEvent e) -> {
              String type = "";
              if (this.rectangleButton.isSelected()) {
                type = "rectangle";
              } else if (this.ovalButton.isSelected()) {
                type = "ellipse";
              }
              this.controller.addShape(this.nameField.getText(), type);
              this.shapeList.setListData(this.model.getShapes().toArray(new IShape[0]));
            });
    this.shapeButtonPanel.add(this.addShapeButton);

    // remove selected shape button
    this.removeShapeButton = new JButton("Remove Shape");
    this.removeShapeButton.addActionListener(
            (ActionEvent e) -> {
              IShape shape = this.shapeList.getSelectedValue();
              if (shape != null) {
                this.controller.removeShape(shape);
                this.shapeList.setListData(this.model.getShapes().toArray(new IShape[0]));
              } else {
                this.throwException("You need to choose a shape to remove.");
              }
            });
    this.shapeButtonPanel.add(this.removeShapeButton);


    // --- SOUTH ----

    // set keyframe panel for the ability to set a keyframe's values and add or remove keyframes
    this.setKeyframePanel = new JPanel();
    this.setKeyframePanel.setLayout(new BorderLayout());
    this.keyframePanel.add(this.setKeyframePanel, BorderLayout.SOUTH);

    // input panel so the user can enter all of the correct fields for a new keyframe
    this.inputPanel = new JPanel();
    this.inputPanel.setLayout(new GridLayout(2, 8));
    this.setKeyframePanel.add(this.inputPanel, BorderLayout.NORTH);

    // label so the user knows what each field does
    this.tickLabel = new JLabel("Tick:", SwingConstants.RIGHT);
    this.inputPanel.add(this.tickLabel);

    // tick textField for the user to enter some value
    this.tickTextField = new JTextField();
    this.inputPanel.add(this.tickTextField);

    // label so the user knows what each field does
    this.xLabel = new JLabel("X:", SwingConstants.RIGHT);
    this.inputPanel.add(this.xLabel);

    // x textField for the user to enter some value
    this.xTextField = new JTextField();
    this.inputPanel.add(this.xTextField);

    // label so the user knows what each field does
    this.yLabel = new JLabel("Y:", SwingConstants.RIGHT);
    this.inputPanel.add(this.yLabel);

    // y textField for the user to enter some value
    this.yTextField = new JTextField();
    this.inputPanel.add(this.yTextField);

    // label so the user knows what each field does
    this.widthLabel = new JLabel("Width:", SwingConstants.RIGHT);
    this.inputPanel.add(this.widthLabel);

    // width textField for the user to enter some value
    this.widthTextField = new JTextField();
    this.inputPanel.add(this.widthTextField);

    // label so the user knows what each field does
    this.heightLabel = new JLabel("Height:", SwingConstants.RIGHT);
    this.inputPanel.add(this.heightLabel);

    // height textField for the user to enter some value
    this.heightTextField = new JTextField();
    this.inputPanel.add(this.heightTextField);

    // label so the user knows what each field does
    this.rLabel = new JLabel("R:", SwingConstants.RIGHT);
    this.inputPanel.add(this.rLabel);

    // red textField for the user to enter some value
    this.rTextField = new JTextField();
    this.rTextField.addKeyListener(this);
    //this.rTextField.addActionListener((ActionEvent e) -> this.updateSelectorColor());
    this.inputPanel.add(this.rTextField);

    // label so the user knows what each field does
    this.gLabel = new JLabel("G:", SwingConstants.RIGHT);
    this.inputPanel.add(this.gLabel);

    // green textField for the user to enter some value
    this.gTextField = new JTextField();
    this.gTextField.addKeyListener(this);
    //this.gTextField.addActionListener((ActionEvent e) -> this.updateSelectorColor());
    this.inputPanel.add(this.gTextField);

    // label so the user knows what each field does
    this.bLabel = new JLabel("B:", SwingConstants.RIGHT);
    this.inputPanel.add(this.bLabel);

    // tick textField for the user to enter some value
    this.bTextField = new JTextField();
    this.bTextField.addKeyListener(this);
    //this.bTextField.addActionListener((ActionEvent e) -> this.updateSelectorColor());
    this.inputPanel.add(this.bTextField);

    // keyframe button panel so that the user can add and remove keyframes
    this.keyFrameButtonPanel = new JPanel();
    this.keyFrameButtonPanel.setLayout(new FlowLayout());
    this.setKeyframePanel.add(keyFrameButtonPanel, BorderLayout.SOUTH);

    // add a keyframe button
    this.addKeyframeButton = new JButton("Add Keyframe");
    this.addKeyframeButton.addActionListener(
            (ActionEvent e) -> {
              IShape shape = this.shapeList.getSelectedValue();
              int shapeIdx = this.shapeList.getSelectedIndex();
              if (shape != null) {
                this.controller.addKeyframe(shape, this.getTickField(), this.getXField(),
                        this.getYField(), this.getWidthField(), this.getHeightField(),
                        this.getRedField(), this.getGreenField(), this.getBlueField());
                this.shapeList.setListData(this.model.getShapes().toArray(new IShape[0]));
                this.shapeList.setSelectedIndex(shapeIdx);
              } else {
                this.throwException("You need to choose a shape to add the keyframe to.");
              }
            });
    this.keyFrameButtonPanel.add(this.addKeyframeButton);

    // remove the selected keyframe button
    this.removeKeyframeButton = new JButton("Remove Keyframe");
    this.removeKeyframeButton.addActionListener(
            (ActionEvent e) -> {
              IShape shape = this.shapeList.getSelectedValue();
              int shapeIdx = this.shapeList.getSelectedIndex();
              ICommand keyframe = this.commandList.getSelectedValue();
              if (shape != null && keyframe != null) {
                this.controller.removeKeyframe(shape, keyframe);
                this.shapeList.setListData(this.model.getShapes().toArray(new IShape[0]));
                this.shapeList.setSelectedIndex(shapeIdx);
              } else {
                this.throwException("You need to choose a shape and keyframe to remove.");
              }
            });
    this.keyFrameButtonPanel.add(this.removeKeyframeButton);

    // ----------- INIT OTHER --------------------------------------

    // progress bar which should display the total progress throughout the animation
    this.progressBar = new JProgressBar();
    this.progressBar.setMinimum(0);
    this.progressBar.setMaximum(this.model.getAnimationLength());
    this.add(this.progressBar, BorderLayout.SOUTH);


    // ----------- DONE WITH INIT ----------------------------------

    //this.animationPanel.repaint();

    this.pack();

    this.setVisible(true);

  }

  private void updateSelectorColor() {
    int r = this.getRedField();
    if (r == -1) {
      r = 0;
    }
    int g = this.getGreenField();
    if (g == -1) {
      g = 0;
    }
    int b = this.getBlueField();
    if (b == -1) {
      b = 0;
    }
    Color c = new Color(r, g, b);
    Color text;

    if (((c.getRed() + c.getGreen() + c.getBlue()) / 3) < 128) {
      text = new Color(255,255,255);
    } else {
      text = new Color(0, 0, 0);
    }

    // change background color
    this.inputPanel.setBackground(c);

    // change label text as required for readability
    this.tickLabel.setForeground(text);
    this.xLabel.setForeground(text);
    this.yLabel.setForeground(text);
    this.widthLabel.setForeground(text);
    this.heightLabel.setForeground(text);
    this.rLabel.setForeground(text);
    this.gLabel.setForeground(text);
    this.bLabel.setForeground(text);
  }

  private void setFieldsIfPossible(ICommand cmd) {
    // instance of justification - I know we're not supposed to use
    if (cmd instanceof CommandChangeAll) {
      String[] arr = cmd.toString().replace(",", "").split(" ");
      this.tickTextField.setText(arr[3]);
      this.xTextField.setText(arr[5]);
      this.yTextField.setText(arr[7]);
      this.widthTextField.setText(arr[9]);
      this.heightTextField.setText(arr[11]);
      this.rTextField.setText(arr[13]);
      this.gTextField.setText(arr[15]);
      this.bTextField.setText(arr[17]);
      this.updateSelectorColor();
    }
  }

  private int convertString(String str) {
    if (str.length() == 0) {
      // if nothing entered, return 0 for ease of use (although this may seem like a bit
      // weird of action.
      return 0;
    }
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) {
        // string cannot be cleanly converted
        //throw new IllegalArgumentException("String: " + str + " cannot be converted to an integer");
        this.throwException(str + " cannot be converted to a number");
        return -1;
      }
    }
    return Integer.parseInt(str);
  }

  private int getTickField() {
    return convertString(this.tickTextField.getText());
  }

  private int getXField() {
    return convertString(this.xTextField.getText());
  }

  private int getYField() {
    return convertString(this.yTextField.getText());
  }

  private int getWidthField() {
    return convertString(this.widthTextField.getText());
  }

  private int getHeightField() {
    return convertString(this.heightTextField.getText());
  }

  // I don't think throwing an error here is probably the best plan be idk what to do right now

  private int getRedField() {
    int color = convertString(this.rTextField.getText());
    if (UtilityFunctions.isValidColor(color)) {
      return color;
    }
    //throw new IllegalArgumentException(color + " is not a valid RGB value");
    return 0;
  }

  private int getGreenField() {
    int color = convertString(this.gTextField.getText());
    if (UtilityFunctions.isValidColor(color)) {
      return color;
    }
    //throw new IllegalArgumentException(color + " is not a valid RGB value");
    return 0;
  }

  private int getBlueField() {
    int color = convertString(this.bTextField.getText());
    if (UtilityFunctions.isValidColor(color)) {
      return color;
    }
    //throw new IllegalArgumentException(color + " is not a valid RGB value");
    return 0;
  }

  public void throwException(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void updateDisplay(int tick) {
    this.progressBar.setValue(tick);
    this.animationPanel.repaint();
  }

  @Override
  public void addController(IAnimatorController controller) {
    this.controller = controller;
  }


  private boolean isTextFieldFocus() {
    return this.tickTextField.hasFocus() || this.xTextField.hasFocus() || this.yTextField.hasFocus()
            || this.widthTextField.hasFocus() || this.heightTextField.hasFocus()
            || this.rTextField.hasFocus() || this.gTextField.hasFocus()
            || this.bTextField.hasFocus();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // does nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // does nothing
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (this.isTextFieldFocus()) {
      this.updateSelectorColor();
    }
  }
}
