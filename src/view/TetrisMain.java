/**
 * 
 */
package view;

import com.sun.media.codec.audio.mp3.JavaDecoder;
import java.awt.EventQueue;
import java.io.File;

import javax.media.Codec;
import javax.media.PlugInManager;

import sound.MusicPlayer;


/**
 * Runs tetris.
 * @author baimenov
 * @version December 11 2016
 */
public final class TetrisMain {
    
    /**
     * Private constructor to prevent class instantiation.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /** 
     * Runs Tetris.
     * @param theArgs main String.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TetrisGUI().start();
            }
        });
        
    }
}
