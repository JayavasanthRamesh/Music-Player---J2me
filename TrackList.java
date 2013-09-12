
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class TrackList extends Form implements CommandListener,PlayerListener
{
    private Midlet midlet;
    private Display display;
    public List songList;
    private FileConnection fc;
    private Command ok,back,exit,stop,pause,play,next;
    private String selected;
    private int index,size;
    private Player player;
    
    public TrackList(Midlet midlet,Display display)
    {
        super("Track List");
        this.midlet=midlet;
        this.display=display;
        
        songList=new List("Songs",Choice.IMPLICIT);
        ok=new Command("Play Song", Command.OK, 0);
        back=new Command("Back",Command.BACK,0);
        exit=new Command("Exit", Command.EXIT, 0);
        stop=new Command("Stop", Command.SCREEN, 0);
        pause=new Command("Pause", Command.SCREEN, 0);
        play=new Command("play", Command.SCREEN, 0);
        next=new Command("next",Command.SCREEN,0);
        
        System.out.println("Right");
        
        try
        {
            fc=(FileConnection)Connector.open("file:///root1/music");
            Enumeration files=fc.list();
            while(files.hasMoreElements())
            {
                String temp=(String)files.nextElement();
                songList.append(temp, null);
            }
        }
        catch(Exception e)
        {
            System.out.println("An Exception Occurred during File Connection");
        }
        
        songList.addCommand(ok);
        songList.setCommandListener(this);
        display.setCurrent(this);
        
        addCommand(back);
        addCommand(exit);
        addCommand(pause);
        addCommand(play);
        addCommand(stop);
        addCommand(next);
        
        setCommandListener(this);
    }
    
    public void commandAction(Command c, Displayable d)
    {
        if(c == ok)
        {
           int index=songList.getSelectedIndex();
           selected=songList.getString(index);
           System.out.println(selected);
           playAudio(selected);
        }
        else if(c == play)
        {
            if(PlayerListener.STOPPED == "stopped" || player==null)               
            {
                playAudio(selected);
            }
        }
        else if(c == pause)
        {
            try
            {
                player.stop();
            }
            catch(Exception e)
            {
                
            }
        }
        else if(c == stop)
        {
            player.close();
        }
        else if(c == back)
        {
            player.close();
            display.setCurrent(songList);
        }
        else if(c == exit)
        {
            midlet.destroyApp(true);
        }
        else if(c == next)
        {
            try
            {
                player.stop();
                index++;
                if(index >= songList.size())
                    index=0;
                selected=songList.getString(index);
                playAudio(selected);
            }
            catch(Exception e)
            {
                System.out.println("An Exception caught at end of media");
            }
        }
            
    }

    public void playerUpdate(Player player, String event, Object eventData) 
    {
        if(event == PlayerListener.END_OF_MEDIA)
        {
            try
            {
                player.stop();
                index++;
                if(index >= songList.size())
                    index=0;
                selected=songList.getString(index);
                playAudio(selected);
            }
            catch(Exception e)
            {
                System.out.println("An Exception caught at end of media");
            }
        }
    }
    
    public void playAudio(String song)
    {
        song=fc.getURL()+song;
      
        try 
        {
                System.out.println(song);
                player = Manager.createPlayer(song);
                player.addPlayerListener(this);
                player.prefetch();
                player.realize();
                player.start();
        } 
        catch (Exception e) 
        {
        }
        display.setCurrent(this);
    }
    
}
