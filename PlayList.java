
import java.io.DataInputStream;
import java.io.DataOutputStream;
//import net.mypapit.java.StringTokenizer;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
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
public class PlayList extends Form implements CommandListener,PlayerListener
{
    private Display display;
    private Midlet midlet;
    private Command addnew,exit,view,back,play,ok,next,pause,stop;
    private String url="http://localhost:8080/AudioServlet/DemoServlet",str,pattern,selected;
    private String[] playlistt;
    private String[] plist,songs;
    private int begin,end,i,index,read;
    private ChoiceGroup list;
    private List songList;
    private Player player;
    
    public PlayList(Midlet midlet,Display display)
    {
        super("Playlist");
        this.midlet=midlet;
        this.display=display;
        playlistt=new String[100];
        songs=new String[100];
        plist=new String[100];
       
        back=new Command("Back", Command.BACK, 0);
        exit=new Command("Exit", Command.EXIT, 0);
        addnew=new Command("Add New", Command.SCREEN, 0);
        view=new Command("View Playlist", Command.SCREEN, 0);
        play=new Command("Play Song",Command.OK,0);
        pause=new Command("Pause",Command.SCREEN,0);
        stop=new Command("Stop",Command.CANCEL,0);
        next=new Command("Next",Command.OK,0);
        ok=new Command("Play",Command.OK,0);
        
        list=new ChoiceGroup("Playlists", Choice.POPUP);
        songList=new List("Current Playlist", Choice.IMPLICIT);
        songList.addCommand(back);
        songList.addCommand(exit);
        songList.setCommandListener(new User(this.midlet,this.display));
        songList.addCommand(ok);
        songList.addCommand(next);
        songList.addCommand(pause);
        songList.addCommand(play);
        
        addCommand(exit);
        addCommand(back);
        addCommand(addnew);
        addCommand(view);
        
        setCommandListener(this);
        try
        {
            //Sending Request to the configured Servlet//
            
           HttpConnection c = (HttpConnection) Connector.open(url);
            c.setRequestProperty("User-Agent","Profile/MIDP-1.0, Configuration/CLDC-1.0");
            c.setRequestProperty("Content-Language","en-US");
            c.setRequestMethod(HttpConnection.POST); 
            DataOutputStream op=(DataOutputStream)c.openDataOutputStream();
            String type="1";
            op.writeUTF(type);
            op.flush();
            op.close();
            
            //Finish Sending Request to the server//
            
            //Open InputStream//
            DataInputStream ip=(DataInputStream)c.openDataInputStream();
            System.out.println("Sent Succesfully");
            String temp;
            
            byte[] a=new byte[500000];
            read = ip.read(a);
            str=new String(a);
            str=str.trim();
            pattern="\n";
            playlistt=token(str,pattern,0);
            i=0;
            while(playlistt[i]!=null)
            {
                System.out.println(playlistt[i]);
                index=playlistt[i].indexOf(" ");
                temp=playlistt[i].substring(0, index);
                temp=temp.trim();
                list.append(temp, null);
                songs[i]=playlistt[i].substring(index+1);
                System.out.println("songs"+songs[i]);
                i++;
            }
         }
        catch(Exception e)
        {
            System.out.println("Caught");
        }
        append(list); 
    }
    
    public String[] token(String str,String pattern,int incre)
    {
        String[] play=new String[100];
        i=0;
            while(str.length() > 0)
            {
                index=str.indexOf(pattern);
                System.out.println("In index:"+index);
                if(index == -1)
                    index=str.length();
                String t=str.substring(0,index);
                System.out.println("Grep "+t);
                play[i++]=t;
                if((index == str.length()) || (index+1+incre >= str.length()))
                    break;
                str=str.substring(index+1+incre);
            }
            if(incre == 3)
            {
                i=0;
                while(play[i]!= null)
                    System.out.println("play"+play[i++]);
            }
        return play;
    }
    
    public void commandAction(Command c,Displayable d)
    {
        if( c == exit)
        {
            midlet.destroyApp(true);
        }
        else if(c == back)
        {
            display.setCurrent(midlet.list);
        }
        else if(c == view)
        {
            //playsong=null;
            songList.deleteAll();
            System.out.println("Database is not Queried Still");
            index=list.getSelectedIndex();
            System.out.println("index"+index);
            String t=".mp3";
            plist=token(songs[index],t,3);
            i=0;
            while(plist[i]!=null)
            {
                System.out.println("plist"+plist[i]);
                String t1=plist[i];
                songList.append(t1, null);
                System.out.println("Over");
                i++;
            }
            display.setCurrent(songList);
        }
        else if(c == addnew)
        {
            System.out.println("Redirect to Add new Window");
            display.setCurrent(midlet.addnew);
        }
        else if(c == play)
        {
            index=songList.getSelectedIndex();
            selected=songList.getString(index);
            System.out.println(selected);
            playAudio(selected);
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
        song="file:///root1/music/"+song+".mp3";
      
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
       
    }
    
    private class User implements CommandListener
    {
        private Midlet midlet;
        private Display display;
        public User(Midlet midlet,Display display)
        {
            this.midlet=midlet;
            this.display=display;
        }
        public void commandAction(Command c,Displayable d)
        {
             if(c == play)
            {
            int index=songList.getSelectedIndex();
            selected=songList.getString(index);
            System.out.println(selected);
            playAudio(selected);
            }
            else if(c == ok)
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
                display.setCurrent(midlet.playlist);
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
    }
}


