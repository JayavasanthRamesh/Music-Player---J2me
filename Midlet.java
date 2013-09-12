/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Stack;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author dell
 */
public class Midlet extends MIDlet {
    
    private Display display;
    public Audio audio;
    public Welcome welcome;
    public TPList list;
    public TrackList tracklist;
    public PlayList playlist;
    public AddNew addnew;

    public void startApp() {
        display=Display.getDisplay(this);
        welcome=new Welcome(this,display);
        list=new TPList(this, display);
        tracklist=new TrackList(this, display);
        playlist=new PlayList(this,display);
        addnew=new AddNew(this,display);
        display.setCurrent(welcome);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
}
