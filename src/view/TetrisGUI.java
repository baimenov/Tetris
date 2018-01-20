/**
 * 
 */
package view;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.media.Codec;
import javax.media.PlugInManager;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import com.sun.media.codec.audio.mp3.JavaDecoder;

import sound.MusicPlayer;


/**
 * Tetris GUI, with game itself and next piece panel.
 * 
 * @author baimenov
 * @version December 11 2016
 */
public class TetrisGUI extends JFrame  {
    
    private static final long serialVersionUID = 5756891741199164333L;
    
    /**
     * Frame Size of the Tetris.
     */
    private static final Dimension FRAME_SIZE = new Dimension(700, 858);
    
    /**
     * Color of the tetris pieces.
     */
    private static final Color DS_COLOR = new Color(254, 90, 29);
    
    /**
     * Tetris Panel.
     */
    private GamePanel myPanel;
    
    /**
     * Panel with the next Tetris piece.
     */
    private JPanel myNextPanel;
    
    /**
     * Menu bar with help submenu.
     */
    private JMenuBar myMenuBar;
    
    /**
     * Score Panel.
     */
    private JPanel myScorePanel;
    
    /**
     * Key Listener.
     */
    private KeyListener myKeyListener;
    
    /**
     * MusicPlayer.
     */
    private static final MusicPlayer mp = new MusicPlayer();
    
    /**
     * Side panel with next piece preview and score.
     */
    private JPanel mySidePanel;

    /**
     * Tetris GUI initialization.
     */
    public TetrisGUI() {
        super("TCSS 305 - Tetris");
        myPanel = new GamePanel();
        myMenuBar = createMenu();
        myNextPanel = myPanel.getPanel();
        mySidePanel = createPanel();
        mySidePanel.add(myNextPanel);
        myScorePanel = myPanel.getScorePanel();
        mySidePanel.add(myScorePanel);
        myKeyListener = new TetrisKeyListener();
        setupMusic();
        add(mySidePanel, BorderLayout.EAST);
        add(myMenuBar, BorderLayout.NORTH);
        add(myPanel, BorderLayout.CENTER);
        addKeyListener(myKeyListener);
    }
    
    
    
    /**
     * Starts tetris.
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Creates menu bar.
     * @return JMenuBar menu bar.
     */
    private JMenuBar createMenu() {
        final JMenuBar menu = new JMenuBar();
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem controls = new JMenuItem("Controls...");
        final JMenuItem about = new JMenuItem("About...");
        about.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(TetrisGUI.this, "Each cleared row"
                                + " increases score by 1 regardless of level.\n"
                                + "Game pace increases each 5th level.\n\n"
                                + "Song name: Soul of Cinder\n"
                                + "Music composed by Yuka Kitamura\n\n"
                                + "Music becomes more calming at around minute 2.\n\n"
                                + "I devote this song to all students who struggle"
                                + " in this class",
                                              "Some information",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
        controls.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(TetrisGUI.this, "Left Arrow - Move left\n"
                                + "Right Arrow - Move right\n"
                                + "Down Arrow - Shift down\nUp Arrow - Rotate 90° clockwise\n"
                                + "Space - Drop the piece\n"
                                + "P - Pause the game\n"
                                + "M - Mute/Unmute music (it's loud)",
                                              "How to control pieces",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(controls);
        helpMenu.add(about);
        final JMenu sizeMenu = new JMenu("Grid Size");
        final ButtonGroup bg = new ButtonGroup();
        final JRadioButtonMenuItem[] jrbmi = {new JRadioButtonMenuItem("10x20"),
                                              new JRadioButtonMenuItem("16x32"),
                                              new JRadioButtonMenuItem("20x40")};
        for (int i = 0; i < jrbmi.length; i++) {
            bg.add(jrbmi[i]);
            sizeMenu.add(jrbmi[i]);
            final Integer index = new Integer(i);
            jrbmi[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    final int x = Integer.parseInt(jrbmi[index].getText().substring(0, 2));
                    final int y = Integer.parseInt(jrbmi[index].getText().substring(3, 5));
                    myPanel.setPanelSize(x,  y);
                    myPanel.getTimer().start();
                }
            });
        }
        
        jrbmi[0].setSelected(true);
        menu.add(helpMenu);
        menu.add(sizeMenu);
        return menu;
    }
    
    /**
     * Creates Side panel with next piece panel in it.
     * @return JPanel side panel.
     */
    private JPanel createPanel() {
        final JPanel jp = new JPanel();
        final BoxLayout b = new BoxLayout(jp, BoxLayout.PAGE_AXIS);
        jp.setLayout(b);
        jp.setBackground(Color.BLACK);
        jp.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        jp.setPreferredSize(new Dimension(294, 100));
        return jp;
    }
    
    /**
     * Sets up music for Tetris.
     */
    private void setupMusic() {
        final Codec c = new JavaDecoder();
        PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder",
                                c.getSupportedInputFormats(),
                                c.getSupportedOutputFormats(null),
                                PlugInManager.CODEC);
        final File music = new File("sounds/ds3.wav");
        final File[] musics = {music};
        
        mp.newList(musics);
        mp.play();
        mp.togglePause();
    }
    
    /**
     * Inner class that handles key inputs.
     * @author baimenov
     * @version December 3 2016
     */
    private class TetrisKeyListener implements KeyListener {
        
        @Override
        public void keyTyped(final KeyEvent theEvent) {
            
        }
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            if (theEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                myPanel.getBoard().down();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                myPanel.getBoard().right();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                myPanel.getBoard().left();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_UP) {
                myPanel.getBoard().rotate();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                myPanel.getBoard().drop();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                if (myPanel.getTimer().isRunning()) {
                    myPanel.getTimer().stop();
                } else {
                    myPanel.getTimer().start();
                }
            } else if (theEvent.getKeyCode() == KeyEvent.VK_M) {
                mp.togglePause();
            }
        }

        @Override
        public void keyReleased(final KeyEvent theEvent) {
    
        }
    }

}
