/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
class StringTokenizer 
{
    private String str,c;
    public String[] pattern;
    private int begin,end,i,index;

    public StringTokenizer(String s, String c) 
    {
        this.str=s;
        this.c=c;
        begin=0;
        i=0;
        pattern=new String[100];
    }
    
    public String[] token()
    {
        while(str.length() > 0)
        {
            System.out.println(str);
            index=str.indexOf(c);
            end=index-1;
            String temp=str.substring(begin,end);
            if(end+1 > str.length())
                return pattern;
            str=str.substring(end+1);
            pattern[i++]=temp;
        }
        return pattern;
    }
    
}
