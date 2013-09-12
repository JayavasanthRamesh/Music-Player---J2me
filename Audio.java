/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */

import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

public class Audio
{
   /* private Midlet midlet;
    private Display display;
    private Command play,exit,stop,next,back;
    private Player player;
    private String[] songs;
    private int index;
    private FileConnection fc;
    Object ob;
    
    public Audio(Midlet midlet,Display display)
    {
        super("");
        this.midlet=midlet;
        this.display=display;
        index=0;
        play=new Command("Play", Command.OK, 0);
        exit=new Command("Exit", Command.EXIT, 0);
        stop=new Command("Stop", Command.STOP, 0);
        next=new Command("Next", Command.ITEM, 0);
        back=new Command("Back", Command.BACK,0);
        songs=new String[300];
        try
        {
            fc=(FileConnection)Connector.open("file:///root1/music");
            Enumeration files=fc.list();
            while(files.hasMoreElements())
            {
                String name=(String)files.nextElement();
                //System.out.println((String)files.nextElement());
                songs[index]=fc.getURL()+name;
                index++;
            }
            index=0;
        }
        catch(Exception e)
        {
            System.out.println("Exception Occured");
        }
        

        addCommand(play);
        addCommand(exit);
        addCommand(stop);
        addCommand(next);
        addCommand(back);

        setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d)
    {
        if(c == play)
        {
            try
            {
                if(player == null)
                    playAudio(songs[index]);
            }
            catch(Exception e)
            {

            }
        }
        else if(c == stop)
        {
            try
            {
                player.stop();
                player=null;
            }
            catch(Exception e)
            {
                
            }
        }
        else if(c == exit)
        {
            if(player!=null)
               player.close();
            midlet.destroyApp(false);
        }
        else if(c == next)
        {
            index++;
            player.close();
            if(songs[index] == null)
                index=0;
            playAudio(songs[index]);
        }
        else if(c == back)
        {
            display.setCurrent(midlet.welcome);
        }
    }

    public void playerUpdate(Player player,String event,Object eventdata)
    {
        if(event == PlayerListener.END_OF_MEDIA)
        {
            try
            {
                player.stop();
                index++;
                if(songs[index] == null)
                    index=0;
                playAudio(songs[index]);
            }
            catch(Exception e)
            {
                System.out.println("An Exception caught at end of media");
            }
            
            
        }
    }
    
    public void playAudio(String song)
    {
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
    }*/

}
