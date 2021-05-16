/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author Kenneth Ahrens
 * @author AJ Downey
 *
 */
public class PokemonGUI {
        
        /* constants */
        
        /**
         * Aspect Ratio of JFrame
         */
        final int WIDTH = 1920, HEIGHT = 1080;
        
        /*Fields*/
        
        /**
         * Icon of the program
         */
        private final ImageIcon myImageIcon = new ImageIcon("./src/images/other/TriviaIcon.png");
        
        /**
         * Window for GUI
         */
        private final JFrame myFrame;
        
        
        /**
         * Panel for the gameplay
         */
        
        private final PokemonPanel myPanel;
        
        /**
         * Menubar
         */
        private final PokemonMenuBar myMB;

        /**
         * Array holding actions for controls
         */
        private final ArrayList<Action> myControlActions;
        
        /**
         * ButtonGroup of movement controls
         */
        private final ButtonGroup myControlButtons;
        
        
        
        /**
         * Constructor to initialize GUI components
         */
        public PokemonGUI() {
                myFrame = new JFrame("Pokemon Trivia");
                myPanel = new PokemonPanel();
                myMB = new PokemonMenuBar(this);
                myControlActions = new ArrayList<Action>();
                myControlButtons = new ButtonGroup();
                
                setupGUI();
                setupControlButtons();
        }
        
        /**
         * Helper to initialize GUI components
         */
        private void setupGUI() {
                //set name and icon
                myFrame.setIconImage(myImageIcon.getImage());
                
                //set size
                myFrame.setSize(WIDTH, HEIGHT);
                
                //setup game panel and add to frame
                myFrame.getContentPane().add(myPanel);
                
                //add menubar
                myFrame.getContentPane().add(myMB, BorderLayout.NORTH);
                
                
                
                
                //finalize frame
                myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                myFrame.pack();
                myFrame.setLocationRelativeTo(null);
                myFrame.setVisible(true);
                myFrame.setResizable(false);
        }
        
        /**
         * Sets up the Control Buttons for the maze
         */
        private void setupControlButtons() {
//                myControlActions.add(new LeftAction());
//                myControlActions.add(new RightAction());
//                myControlActions.add(new UpAction());
//                myControlActions.add(new DownAction());
//                
//                for (final Action act : myControlActions) {
//                        final JToggleButton tb = new JToggleButton(act);
//                        myControlButtons.add(tb);
//
//                }
                
        }

        /**
         * @return the myPanel
         */
        public JFrame getFrame() {
                return myFrame;
        }
        
        /**
         * @return the myPanel
         */
        public PokemonPanel getPanel() {
                return myPanel;
        }
        
        
        


}
