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
        
        final Codec c = new JavaDecoder();
        PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder",
                                c.getSupportedInputFormats(),
                                c.getSupportedOutputFormats(null),
                                PlugInManager.CODEC);
        final File music = new File("sounds/ds3.wav");
        final File[] musics = {music};
        
        final MusicPlayer m = new MusicPlayer();
        m.newList(musics);
        m.play();
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TetrisGUI().start();
            }
        });
        
    }
}
