/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

/**
 *
 * @author clement
 */
public class Session {

    private static String log = null;
    
    public Session (){
        
    }

    public Session(String LOG) {
        if (log == null) {
            log = LOG;
        }
    }

    public static String getLog()
    {
        return log;
    }
    
}
