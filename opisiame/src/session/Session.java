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
    private static String type = null;// admin ou anim 
    private static Integer anim_id = null;// anim

    public static Integer getAnim_id() {
        return anim_id;
    }

    public static void setAnim_id(Integer anim_id) {
        Session.anim_id = anim_id;
    }
    
    public Session (){
        
    }

    public Session(String LOG, String TYPE) {
        if (log == null) {
            log = LOG;
            type = TYPE;
        }
    }

    public static String getLog()
    {
        return log;
    }
    
    
    public static String getType()
    {
        return type;
    }
    
    public static void setType(String t)
    {
        type = t;
    }
    
    //fermer une section
    public static void Logout()
    {
        log = null;
        type = null;
    }
}
