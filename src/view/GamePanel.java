/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;


/**
 * Tetris Game panel with the actual game in it.
 * @author baimenov
 * @version December 11 2016
 */
public class GamePanel extends JPanel implements Observer {
    
    private static final long serialVersionUID = 5756891741195554333L;
    
    /**
     * Color of the tetris pieces.
     */
    private static final Color DS_COLOR = new Color(254, 90, 29);
    
    /**
     * Number used to correctly represent the board.
     */
    private int myMargin;
    
    /**
     * Number used to correctly represent the board.
     */
    private int myEdge;
    
    /**
     * Tetris board.
     */
    private Board myBoard;
    
    /**
     * Next Piece Panel.
     */
    private NextPiecePanel myNpp;
    
    /**
     * Score Panel.
     */
    private ScorePanel myScorePanel;
    
    /**
     * Timer that updates the game each second.
     */
    private final Timer myTimer;
    
    /**
     * Sets up the tetris game panel and timer.
     */
    public GamePanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(400, 800));
        myBoard = new Board();
        myBoard.addObserver(this);
        myNpp = new NextPiecePanel();
        myScorePanel = new ScorePanel();
        myBoard.addObserver(myNpp);
        myBoard.addObserver(myScorePanel);
        myBoard.newGame();
        myTimer = new Timer(1000, null);
        myTimer.addActionListener(new MoveListener());
        myTimer.start();
    }
    
    /**
     * Returns Score Panel.
     * @return ScorePanel panel.
     */
    public ScorePanel getScorePanel() {
        return myScorePanel;
    }
    
    /**
     * Sets grid size of the game.
     * @param theX width of the grid.
     * @param theY height of the grid.
     */
    public void setPanelSize(final int theX, final int theY) {
        myBoard = new Board(theX, theY);
        myBoard.addObserver(this);
        myBoard.newGame();
        myBoard.addObserver(myNpp); 
        myScorePanel.resetScore();
        myBoard.addObserver(myScorePanel);
    }
    
    /**
     * Returns Next Piece Panel.
     * @return NextPiecePanel panel.
     */
    public NextPiecePanel getPanel() {
        return myNpp;
    }
    
    /**
     * Returns timer.
     * @return Timer timer.
     */
    public Timer getTimer() {
        return myTimer;
    }
    
    /**
     * Returns tetris board.
     * @return Board - tetris board.
     */
    public Board getBoard() {
        return myBoard;
    }
    
    /**
     * Updates the tetris panel as soon as board was updated.
     */
    @Override
    public void update(final Observable theObs, final Object theObj) {
        if (theObj instanceof String) {
            int delay = 1000 - (100 * (myScorePanel.getLevel() - 1));
            if (delay != 1000) {
                if (delay <= 0) {
                    delay = 10;
                }
                myTimer.setDelay(delay);
            }
            repaint();
            
        } else if (theObj instanceof Boolean) {
            JOptionPane.showMessageDialog(GamePanel.this, "You Lost",
                                          "Game Over", JOptionPane.INFORMATION_MESSAGE);
            myTimer.stop();
        }
        
    }
    
    /**
     * Paints tetris panel.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setPaint(Color.RED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        final String board = myBoard.toString();
        if (board.length() == 336) {
            myMargin = 64;
            myEdge = 13;
        } else if (board.length() == 720) {
            myMargin = 94;
            myEdge = 19;
        } else if (board.length() == 1056) {
            myMargin = 114;
            myEdge = 23;
        }
        final int w = myBoard.getWidth();
        final int sq = 400 / w;
        int row = 0;
        for (int i = 0; i < board.length() - (myMargin + myEdge); i++) {
            final char c = board.charAt(i + myMargin);
            if (c == '\n') {
                row++;
            }
            final Rectangle2D rect = new Rectangle2D.Double(sq * ((i - 1) % myEdge),
                                                            row * sq, sq, sq);
            if (c != '|' && c != ' ' && c != '\n') {
                g2d.setPaint(DS_COLOR);
                g2d.fill(rect);
                g2d.setPaint(Color.RED);
                g2d.draw(rect);
            } else if (c == ' ') {
                g2d.setPaint(Color.BLACK);
                g2d.fill(rect);
            }
        }
    }
    
    /**
     * Updates the board each second or shows "Game Over" screen
     * if game is over.
     * @author baimenov
     * @version December 3 2016
     */
    private class MoveListener implements ActionListener {
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.down();
        }
    }
}
