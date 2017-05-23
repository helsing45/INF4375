/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.uqam.inf4375.tp.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author j-c9
 */
public class CSVUtils {
    
    public static String[] read(String s){
        String utf8 = toUTF8(s);
        ArrayList<String> columns = new ArrayList<>();
        boolean isInString = false;
        String currentString = "";
        
        for (char caract : utf8.toCharArray()) {
            if(caract == '"'){
                isInString = !isInString;
            }else if(caract == ',' && !isInString){
                columns.add(currentString);
                currentString = "";
            }else {
                currentString += caract;
            }
        }
        columns.add(currentString);
        
       String[] stockArr = new String[columns.size()];
       stockArr = columns.toArray(stockArr);
       return stockArr;        
    }   
    
    private static String toUTF8(String s){
        if(s.isEmpty()) return "";
        try {
            String utf8 = new String(s.getBytes(), "utf8");
            String capitalize = utf8.substring(0, 1).toUpperCase() + utf8.substring(1);
            return capitalize;
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
    }
    
}
