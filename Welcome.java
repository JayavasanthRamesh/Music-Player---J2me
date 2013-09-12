
import javax.microedition.lcdui.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class Welcome extends Form implements CommandListener
{
    private Command back,next,exit;
    private Display display;
    private String msg;
    private Midlet midlet;
    private int flag;
    
    public Welcome(Midlet midlet, Display display)
    {
        super("Welcome");
        flag=0;
        this.display=display;
        this.midlet=midlet;
        
        //Buttons //
        back=new Command("Back", Command.BACK, 0);
        next=new Command("Next",Command.SCREEN,0);
        exit=new  Command("Exit", Command.EXIT, 0);
        
        addCommand(back);
        addCommand(next);
        addCommand(exit);
        setCommandListener(this);
        
        msg="Welcome to the World of Music";
        append(msg);
    }
    
    public void commandAction(Command c,Displayable d)
    {
        if(c == next)
        {
            display.setCurrent(midlet.list);
        }
        else if(c == exit)
        {
            midlet.destroyApp(true);
        }
    }
}
