/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.uqam.inf4375.tp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 *
 * @author j-c9
 */
public class DateUtils {
    
    private static String preformatDate(String unformattedDate){
        String lowerCaseString = unformattedDate.toLowerCase();
        if(lowerCaseString.startsWith("du")){
            lowerCaseString = lowerCaseString.replace("du ","");
        }
        return lowerCaseString.trim()
                .toLowerCase()
                .replaceAll("à venir", "")
                .replaceAll("à confirmer", "")
                .replaceAll(" ,",",")
                .replaceAll(" +", " ")
                .replaceAll("\\s+", " ")
                .replaceAll("1er", "1");
    }
    
    public static ArrayList<Date> getDates(String date){
        ArrayList<Date> dates = getRightDate(date);
        if(dates == null){
             if(preformatDate(date).isEmpty()) return null;
            //Si on as pas trouver le bon format, c'est peut-etre parce que c'est une combinaison de format.
            String[] datesFormatted = date.split(", ");
            dates = new ArrayList<>();
            for (String dateFormatted : datesFormatted) {
                ArrayList<Date> tempDates = getRightDate(dateFormatted);
                dates = new ArrayList<>();
                if(tempDates != null){
                    dates.addAll(tempDates);
                }
            }
        }
        return dates;
    }
    
    /***
     * La methode passe a travers tout les CustomDateFormats s'il ne trouve pas le format retourne null.
     */
    public static ArrayList<Date> getRightDate(String date){
        String formattedDate = preformatDate(date);
        if(formattedDate.isEmpty()) return null;
        Matcher matcher;
        for (CustomDateFormat value : CustomDateFormat.values()) {
            matcher = value.getPattern().matcher(formattedDate);
            if(matcher.find()){
                return value.getDates(formattedDate);
            }
        }
        return null;
    }
    
    public static boolean printDates(String stringDate){
        System.out.println("Trying to format: "+ stringDate);
        ArrayList<Date> dates = getDates(stringDate);
        if(dates == null){
            System.out.println("No date to format");
            System.out.println("----------------------------");
            return true;
        }
        if(dates.isEmpty()){
            System.out.println("Unformatted");
            System.out.println("----------------------------");
            return false;
        }
        for (Date date : dates) {
            System.out.println(date.toString());
        }
        System.out.println("----------------------------");
        return true;
    }   
    
    /**
     * @return la liste de toute les dates qui ce trouve entre les deux dates.
     */
    public static ArrayList<Date> getRangeDates(Date firstDate, Date secondDate){
        ArrayList<Date> dates = new ArrayList<>();
        if(firstDate.getTime() > secondDate.getTime())return  new ArrayList<>();
        Calendar first = Calendar.getInstance();
        first.setTime(firstDate);
        
        while(!first.getTime().equals(secondDate)){
            dates.add(first.getTime());
            first.add(Calendar.DAY_OF_YEAR, 1);
        }
        dates.add(secondDate);
        return dates;
    }
    
    public static Date getDate(String stringDate){
        SimpleDateFormat dt = new SimpleDateFormat("dd MMMM yyyy", new Locale("fr"));
        try {
            return dt.parse(stringDate);
        } catch (ParseException ex) {
            dt = new SimpleDateFormat("dd MMMM", new Locale("fr"));
            try {
                return formattedToThisYear(dt.parse(stringDate));
            } catch (ParseException ex2) {
                return null;
            }
        }
    }
    
    public static Date getFormattedDate(String stringDate, String pattern){
        SimpleDateFormat dt = new SimpleDateFormat(pattern);
        try {
            return dt.parse(stringDate);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Date formattedToThisYear(Date date){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR,year);
        return calendar.getTime();
    }
}
