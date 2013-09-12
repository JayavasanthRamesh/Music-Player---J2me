
import java.io.DataOutputStream;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class AddNew extends Form implements CommandListener
{
    private Display display;
    private Midlet midlet;
    private ChoiceGroup songs;
    private Command back,exit,ok,cancel;
    private FileConnection fc;
    private TextField playlistname;
    private String url="http://localhost:8080/AudioServlet/DemoServlet";
    
    public AddNew(Midlet midlet,Display display)
    {
        super("Welcome to AddNew Page");
        this.midlet=midlet;
        this.display=display;
        
        playlistname=new TextField("Enter a name", null, 30, TextField.ANY);
        songs=new ChoiceGroup("Songs List", Choice.MULTIPLE);
        back=new Command("Back", Command.BACK, 0);
        exit=new Command("Exit", Command.EXIT, 0);
        cancel=new Command("Cancel", Command.BACK, 0);
        ok=new Command("Ok",Command.OK,0);
        
        addCommand(back);
        addCommand(ok);
        addCommand(exit);
        addCommand(cancel);
        
        setCommandListener(this);
        
        try
        {
            fc=(FileConnection)Connector.open("file:///root1/music");
            Enumeration files = fc.list();
            
            while(files.hasMoreElements())
            {
                String temp=(String)files.nextElement();
                songs.append(temp, null);
            }
        }
        catch(Exception e)
        {
            System.out.println("An UnCaught Exception Has Occured");
        }
        
        append(playlistname);
        append(songs);
    }
    
    public void commandAction(Command c,Displayable d)
    {
        if(c == back)
        {
            display.setCurrent(midlet.playlist);
        }
        else if(c == exit)
        {
            midlet.destroyApp(true);
        }
        else if(c == cancel)
        {
             display.setCurrent(midlet.playlist);
        }
        else if( c == ok )
        {
            boolean get[]=new boolean[songs.size()];
            songs.getSelectedFlags(get);
            String name=playlistname.getString();
            String msg="";
            for(int i=0;i<songs.size();i++)
            {
                if(get[i])
                {
                    msg+=songs.getString(i);
                }
            }
            msg.trim();
            System.out.println("New Playlist"+msg);
            //Creating HTTP Connection//
            try
            {
                HttpConnection hc = (HttpConnection) Connector.open(url);
                hc.setRequestProperty("User-Agent","Profile/MIDP-1.0, Configuration/CLDC-1.0");
                hc.setRequestProperty("Content-Language","en-US");
                hc.setRequestMethod(HttpConnection.POST); 
                DataOutputStream op=(DataOutputStream)hc.openDataOutputStream();
                String type="2";
                op.writeUTF(type);
                op.writeUTF(name);
                op.writeUTF(msg);
                op.flush();
                op.close();
            }
            catch(Exception e)
            {
                System.out.println("An exception is caught while updating table");
            }
        }
    }
}
