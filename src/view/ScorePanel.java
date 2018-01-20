/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
/**
 * Score Panel that displays current Score and Level.
 * @author baimenov
 * @version December 11 2016
 */
public class ScorePanel extends JPanel implements Observer {
    
    private static final long serialVersionUID = 5156891741195554123L;
    
    /**
     * Color of the tetris pieces.
     */
    private static final Color DS_COLOR = new Color(254, 90, 29);
    
    /**
     * Current score.
     */
    private int myScore;
    
    /**
     * Current level.
     */
    private int myLevel;
    
    /**
     * Initializes the score panel.
     */
    public ScorePanel() {
        super();
        myScore = 0;
        myLevel = 1;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(200, 200));
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(200, 200));
        setVisible(true);
    }
    
    /**
     * Resets score and level.
     */
    public void resetScore() {
        myScore = 0;
        myLevel = 1;
        repaint();
    }
    
    /**
     * Displays Score and level.
     * @param theGraphics Graphics object.
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(DS_COLOR);
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        g2d.drawString("Score: " + myScore, 15, 30);
        g2d.drawString("Level: " + myLevel, 15, 60);
    }
    
    /**
     * Returns current level.
     * @return myLevel current Level.
     */
    public int getLevel() {
        return myLevel;
    }
    
    @Override
    public void update(final Observable theObs, final Object theObj) {
        if (theObj instanceof Object[]) {
            myScore++;
            if (myScore % 5 == 0) {
                myLevel++;
            }
            repaint();
        }
    }
}
