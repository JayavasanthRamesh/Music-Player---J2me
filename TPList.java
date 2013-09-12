
import javax.microedition.lcdui.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class TPList extends Form  implements ItemCommandListener, CommandListener
{
    private Display display;
    private Midlet midlet;
    private StringItem track,play;
    private Command next,next1,back;
    
    public TPList(Midlet midlet,Display display)
    {
        super("Track List and Playlist");
        this.display=display;
        this.midlet=midlet;
        track=new StringItem("Track List", null);
        play=new StringItem("PlayList", null);
        next=new Command("Track List",Command.OK,0);
        next1=new Command("Play List", Command.OK, 0);
        back=new Command("Back", Command.BACK, 0);
        track.setDefaultCommand(next);
        play.setDefaultCommand(next1);
        track.setItemCommandListener(this);
        play.setItemCommandListener(this);
        
        
        append(track);
        append(play);
        addCommand(back);
        
        setCommandListener(this);
    }
    
    public void commandAction(Command c, Item t)
    {
        if( c == next )
        {
            System.out.println("Redirect to Track List");
            display.setCurrent(midlet.tracklist.songList);
        }
        else if(c == next1)
        {
            System.out.println("Redirect to Play List");
            display.setCurrent(midlet.playlist);
        }
    }
    
    public void commandAction(Command c,Displayable d)
    {
        if( c == back)
        {
            display.setCurrent(midlet.welcome);
        }
    }
    
}
