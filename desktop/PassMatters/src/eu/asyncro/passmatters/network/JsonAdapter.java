/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import org.json.JSONObject;

/**
 *
 * @author Milan
 */
public class JsonAdapter {
    
    private static final String END = "\n";
    private static final String CODE = "code";
    private static final String MESSAGE = "message";
        
    public static String getToken(String jsonString) {
        jsonString = jsonString.replace(END,"");
        JSONObject object = new JSONObject(jsonString);
        if(object.getInt(CODE) == 1 && object.getString(MESSAGE).equals("Success")) {
            System.out.println(object.getString("token"));
            return object.getString("token");
        }
        else return null;
    }
    
    public static String getAuthJsonString(String token) {
        JSONObject object = new JSONObject();
        object.put("step", 1);
        object.put("token", token);
        return object.toString();
    }
}
