/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.MovableTetrisPiece;

/**
 * Panel that shows the next tetris piece.
 * @author baimenov
 * @version December 3 2016
 */
public class NextPiecePanel extends JPanel implements Observer {
    
    private static final long serialVersionUID = 1236891741195554333L;
    
    /**
     * Tetris Piece that will be drawn on next piece panel.
     */
    private MovableTetrisPiece myMtp;
    
    /**
     * Color of the tetris pieces.
     */
    private static final Color DS_COLOR = new Color(254, 90, 29);
    
    /**
     * Row number where square of a piece
     * should be drawn.
     */
    private int myRow;
    
    /**
     * String representation of a tetris piece.
     */
    private String myToPrint = "";
    
    /**
     * Column number where square of a piece
     * should be drawn on a panel.
     */
    private int myCol;
    
    /**
     * Paint color of a next piece.
     */
    private Color myPaintColor;
    
    /**
     * Initializes the panel where next piece will be drawn.
     */
    public NextPiecePanel() {
        super();
        myPaintColor = Color.GREEN;
        //setBackground(Color.YELLOW);
        setPreferredSize(new Dimension(200, 200));
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(200, 200));
        setVisible(true);
    }
    
    /**
     * Paints tetris piece that will appear next
     * on a side panel.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(myPaintColor);
        for (int i = 0; i < myToPrint.length(); i++) {
            myCol = i % 5;
            if (i % 5 == 0 && i != 0) {
                myRow++;
            }
            final Rectangle2D rect = new Rectangle2D.Double(myCol * 50, myRow * 50, 50, 50);
            
            if (myToPrint.charAt(i) == ' ') {
                myPaintColor = Color.BLACK;
                g2d.setPaint(Color.BLACK);
                g2d.fill(rect);
            } else if (myToPrint.charAt(i) != '\n') {
                g2d.setPaint(DS_COLOR);
                g2d.fill(rect);
                g2d.setPaint(Color.RED);
                g2d.draw(rect);
            }
        }
        myCol = 0;
        myRow = 0;
    }
    
    @Override
    public void update(final Observable theObs, final Object theObj) {
        if (theObj instanceof MovableTetrisPiece) {
            myMtp = (MovableTetrisPiece) theObj;
            myToPrint = myMtp.toString();
            repaint();
        }
    }
}
