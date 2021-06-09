package view.viewHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import model.Maze;
import model.Room;
import view.PokemonGUI;
import view.PokemonPanel;

/**
 * Has the multiple choice question for the room
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class QuestionRoomGUI extends AbstractQuestionPanel {

	/**
	 * constants that make the components
	 */
	private static final long serialVersionUID = 1L;
	private final Color TRANSPARENT = new Color(1f, 0f, 0f, .5f);
	private final int NUM_CHOICES = 4;
	private final int W = 350;
	private final int H = 500;
	private final Dimension PANEL_DIM = new Dimension(W, H);
	private final int COMP_H = 75;
	private final Dimension COMP_DIM = new Dimension(W, COMP_H);


	/*
	 * Maze
	 */
	private Maze myMaze;

	/*
	 * Panel that says "Who's that Pokemon?"
	 */
	private JPanel myTitlePanel;
	/*
	 * Panel that holds multiple choice option
	 */
	private JPanel myMCPanel;
	
	/*
	 * Label that hols the title
	 */
	private JLabel myTitleLabel;

	/*
	 * Multiple choice buttons
	 */
	private final ButtonGroup myButtonGroup;

	/**
	 * Create the panel.
	 */
	public QuestionRoomGUI(PokemonPanel thePP) {
		super(thePP);
		// myCurrRoom = theRoom;
		// myChoices = theRoom.getChoices();

		myMaze = Maze.getInstance();
		myButtonGroup = new ButtonGroup();

		// make components for the panel
		setupGUI();
		myTitlePanel = makeTitlePanel();
		myTitleLabel = makeQuestionLabel();
		myTitlePanel.add(myTitleLabel);
		setupQuestions();

		// positioning
		this.add(myTitlePanel, BorderLayout.NORTH);
		this.add(myMCPanel, BorderLayout.CENTER);

	}

	/*
	 * Initializes this JPanel settings
	 */
	@SuppressWarnings("static-access")
	private void setupGUI() {
		setBorder(new LineBorder(Color.BLACK, 5, true));
		setBackground(Color.WHITE);
		setPreferredSize(PANEL_DIM);
		setMaximumSize(PANEL_DIM);
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		// Scrollpane in case the user can't see all choices
		JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 0, W, H);

	}

	/**
	 * 
	 * @return JTextPane formatted for text
	 */
	private JLabel makeQuestionLabel() {
		JLabel lbl = new JLabel("Who's that Pokemon?", SwingConstants.CENTER);
		lbl.setRequestFocusEnabled(false);
		lbl.setOpaque(false);
		lbl.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 19));
		lbl.setFocusable(false);
		lbl.setBorder(null);
		lbl.setSize(COMP_DIM);
		lbl.setForeground(Color.BLACK);

		return lbl;
	}

	/**
	 * 
	 * @return JPanel formatted for TitlePanel
	 */
	private JPanel makeTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel = new JPanel();
		titlePanel.setLayout(new GridBagLayout()); // to center text
		titlePanel.setSize(COMP_DIM);
		titlePanel.setForeground(Color.WHITE);
		titlePanel.setBackground(Color.LIGHT_GRAY);
		// titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

		return titlePanel;

	}

	/*
	 * Make the questions the panel uses
	 */
	@SuppressWarnings("static-access")
	private void setupQuestions() {

		//make mc panel
		myMCPanel = new JPanel();
		myMCPanel.setLayout(new BorderLayout());
		myMCPanel.setSize(W, H - COMP_H);
		myMCPanel.setForeground(TRANSPARENT);
		myMCPanel.setBackground(Color.WHITE);
		myMCPanel.setLayout(new GridLayout(NUM_CHOICES, 1));

		//make buttons
		for (int i = 1; i <= NUM_CHOICES; i++) {
			char mnemonic = Character.forDigit(i, 10);
			JRadioButton choice = buildButton(mnemonic);
			myButtonGroup.add(choice);
		}

		//enable the text on the buttons
		setButtons();
	}

	/**
	 * Builder for a multiple choice radio button
	 * 
	 * @return multiple choice button
	 */
	private JRadioButton buildButton(final char theMnemonic) {
		final JRadioButton mc = new JRadioButton("");
		mc.setSize(COMP_DIM);
		mc.setOpaque(false);
		mc.setMnemonic(theMnemonic);
		mc.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		mc.addActionListener(new AnswerDisplay());
		myMCPanel.add(mc, BorderLayout.CENTER);
		return mc;

	}

	/**
	 * Put multiple choice text on the buttons
	 */
	@Override
	public void setButtons() {
		// set answers if its a room already visited
		myButtonGroup.clearSelection();
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		final Enumeration<AbstractButton> buttons = myButtonGroup.getElements();
		int i = 0;
		while (buttons.hasMoreElements()) {
			final JRadioButton temp = (JRadioButton) buttons.nextElement();
			temp.setText(choices.get(i));
			temp.setForeground(Color.BLACK);
			i++;

		}
	}

	/*
	 * Set the color of the text after the user answers Green = correct, red =
	 * incorrect
	 */
	public void setButtonsAnswer() {
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		final Enumeration<AbstractButton> buttons = myButtonGroup.getElements();
		int answerIndex = maze.getAttemptRoom().getAnswerIndex();
		int i = 0;
		while (buttons.hasMoreElements()) {
			final JRadioButton temp = (JRadioButton) buttons.nextElement();
			temp.setText(choices.get(i));
			if (i == answerIndex) {
				temp.setForeground(Color.GREEN);
			} else {
				temp.setForeground(Color.RED);
			}
			i++;
		}
	}

	/**
	 * Pop up an option pane when the user answers and tell result
	 */
	public void answerPopUp() {
		String userAns = "";
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		int answerIndex = maze.getAttemptRoom().getAnswerIndex();
		for (Enumeration<AbstractButton> buttons = myButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				userAns = button.getText();
			}
		}

		verifyAnswer(userAns);

	}

	/**
	 * Set all multiple choice buttons state on or off
	 * 
	 * @boolean the state of buttons
	 */
	@Override
	public void enableButtons(Boolean theBool) {
		final Enumeration<AbstractButton> buttons = myButtonGroup.getElements();
		while (buttons.hasMoreElements()) {
			JRadioButton temp = (JRadioButton) buttons.nextElement();
			if (theBool) {
				temp.setEnabled(true);
			} else {
				temp.setEnabled(false);
			}

		}

	}

	// inner class called when user clicks an answer button
	class AnswerDisplay implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setButtonsAnswer();
			answerPopUp();
		}
	}

}
