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
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.math.NumberUtils;
import static com.uqam.inf4375.tp.utils.DateUtils.getRangeDates;
import static com.uqam.inf4375.tp.utils.DateUtils.getDate;
import static com.uqam.inf4375.tp.utils.DateUtils.formattedToThisYear;

/**
 *
 * @author j-c9
 */
public enum CustomDateFormat {
    SIMPLE("^([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            ArrayList<Date> dates = new ArrayList<>();
            dates.add(getDate(stringDate));
            return dates;
        }
        
    },
    SIMPLE_HOUR("^([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{1,2}(h| h)([0-9]{1,2})?)$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            SimpleDateFormat dt;
            ArrayList<Date> dates = new ArrayList<>();
            try {
                dt = new SimpleDateFormat("dd MMMM HH:mm", new Locale("fr"));
                dates.add(formattedToThisYear(dt.parse(stringDate.replaceAll(" h", "h").replaceAll("h",":"))));
                return dates;
            } catch (ParseException ex) {
                try{
                    dt = new SimpleDateFormat("dd MMMM HH", new Locale("fr"));
                    dates.add(formattedToThisYear(dt.parse(stringDate.replaceAll(" h", "h"))));
                    return dates;
                } catch (ParseException ex2) {
                    return new ArrayList<>();
                }
            }
        }
    },
    TWO_DATE_SEPARATE_WITH_AND("^([0-9]{1,2}) et ([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            ArrayList<Date> dates = new ArrayList<>();
            String[] splitedDate = stringDate.split(" ");
            String year = splitedDate.length == 5 ? " " + splitedDate[4] :"";
            dates.add(getDate(splitedDate[0]+" "+splitedDate[3] + year));
            dates.add(getDate(splitedDate[2]+" "+splitedDate[3] + year));
            return dates;
        }
        
    },
    DATE_SEPARATE_WITH_AND_LONG("^([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre) et ([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            ArrayList<Date> dates = new ArrayList<>();
            String[] splitedDate = stringDate.split(" et ");
            dates.add(getDate(splitedDate[0]));
            dates.add(getDate(splitedDate[1]));
            return dates;
        }
        
    },
    DATE_SEPARATE_WITH_TO("^([0-9]{1,2}) au ([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] splitedDate = stringDate.split(" ");
            String year = splitedDate.length == 5 ?" " + splitedDate[4] :"";
            return getRangeDates(getDate(splitedDate[0]+" "+splitedDate[3]+year), getDate(splitedDate[2]+" "+splitedDate[3]+year));
        }
        
    },
    DATE_SEPARATE_WITH_TO_LONG("^([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre) ([0-9]{4} )?au ([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] splitedDate = stringDate.split(" au ");
            return getRangeDates(getDate(splitedDate[0]), getDate(splitedDate[1]));
        }
        
    },
    DATE_SEPARATE_WITH_HYPHEN("^([0-9]{1,2})-([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] splitedDate = stringDate.replace("-", " ").split(" ");
            String year = splitedDate.length == 4 ?" " + splitedDate[3] :"";
            return getRangeDates(getDate(splitedDate[0]+" "+splitedDate[2] + year), getDate(splitedDate[1]+" "+splitedDate[2] + year));
        }
        
    },
    DATE_SEPARATE_WITH_COMMA("([0-9]{1,2}, )+(([0-9]{1,2}) et ([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre))"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] commaDates = stringDate.replaceAll(" et ",",").replaceAll(", ", ",").split(",");
            String[] words = commaDates[commaDates.length - 1].split(" ");
            String month = words[words.length - 1];
            
            for (int i = 0; i < commaDates.length - 1; i++) {
                commaDates[i] = commaDates[i] + " " + month;
            }
            
            ArrayList<Date> dates = new ArrayList<>();
            for (String commaDate : commaDates) {
                dates.add(getDate(commaDate));
            }
            
            return dates;
        }
        
    },
    DATE_SEPARATE_WITH_COMMA_LONG("([0-9]{1,2}, )+(([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre) et ([0-9]{1,2}) (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre))"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] commaDates = stringDate.replaceAll(" et ",",").replaceAll(", ", ",").split(",");
            String[] words;
            String month;
            if(commaDates[commaDates.length - 1].split(" ").length == 2){                
                words = commaDates[commaDates.length - 1].split(" ");
                month = words[words.length - 1];
            }else{
                words = commaDates[commaDates.length - 1].split(" ");
                month = words[words.length - 1];
            }
            
            for (int i = 0; i < commaDates.length - 1; i++) {
                commaDates[i] = commaDates[i] + " " + month;
            }
            
            ArrayList<Date> dates = new ArrayList<>();
            for (String commaDate : commaDates) {
                dates.add(getDate(commaDate));
            }
            
            return dates;
        }
    },
    MONTH("^(janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            Date startDate = getDate("01 " + stringDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date endDate = calendar.getTime();
            return getRangeDates(startDate, endDate);
        }
        
    },
    MONTH_TO_MONTH("^([0-9]{1,2} )?(janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})? à (janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] month = stringDate.split(" à ");
            String startDateString = NumberUtils.isDigits(month[0].split(" ")[0]) ? month[0] : "01 " + month[0];
            Date startDate = getDate(startDateString);
            Date endDate = getDate("01 " + month[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);            
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            endDate = calendar.getTime();            
            return getRangeDates(startDate, endDate);
        }
        
    },
    SINCE("^(dès le|(à partir (de|du))) (([0-9]{1,2}) )?(janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( [0-9]{4})?$") {
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String formattedDate = stringDate
                    .replaceAll("dès le ", "")
                    .replaceAll("à partir de", "")
                    .replaceAll("à partir du", "")
                    .trim();
            ArrayList<Date> dates = new ArrayList<>();
            Matcher matcher = Pattern.compile("^(janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)( 2017)?$").matcher(formattedDate);
            if(matcher.find()){
                dates.add(getDate("01 " + formattedDate));
            }else{
                dates.add(getDate(formattedDate));
            }
            Calendar maxgc = Calendar.getInstance();
            maxgc.setTime(new Date(Long.MAX_VALUE));
            dates.add(maxgc.getTime());
            return dates;
        }
    },
    SEASON("^(été|printemps|automne|hiver)( [0-9]{4})?"){
        @Override
        public ArrayList<Date> getDates(String stringDate) {
            String[] words = stringDate.split(" ");
            Date startDate = null,endDate = null;
            if(words[0].equals("été")){
                Calendar s = Calendar.getInstance();
                int year = words.length == 2 ? Integer.valueOf(words[1]) : s.get(Calendar.YEAR);
                s.set(year,6,21);
                Calendar e = Calendar.getInstance();
                e.set(year,9,21);
                startDate = s.getTime();
                endDate = e.getTime();
            }else if(words[0].equals("printemps")){
                Calendar s = Calendar.getInstance();
                int year = words.length == 2 ? Integer.valueOf(words[1]) : s.get(Calendar.YEAR);
                s.set(year,3,21);
                Calendar e = Calendar.getInstance();
                e.set(year,6,21);
                startDate = s.getTime();
                endDate = e.getTime();
            }else if(words[0].equals("automne")){
                Calendar s = Calendar.getInstance();
                int year = words.length == 2 ? Integer.valueOf(words[1]) : s.get(Calendar.YEAR);
                s.set(year,9,21);
                Calendar e = Calendar.getInstance();
                e.set(year,12,21);
                startDate = s.getTime();
                endDate = e.getTime();
            }else if(words[0].equals("hiver")){
                Calendar s = Calendar.getInstance();
                int year = words.length == 2 ? Integer.valueOf(words[1]) : s.get(Calendar.YEAR);
                s.set(year,12,21);
                Calendar e = Calendar.getInstance();
                e.set(year + 1,3,21);
                startDate = s.getTime();
                endDate = e.getTime();
            }
            return getRangeDates(startDate, endDate);
        }
        
    };    
    
    private String pattern;
    
    private CustomDateFormat(String pattern) {
        this.pattern = pattern;
    }   
    
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }
    
    public abstract ArrayList<Date> getDates(String stringDate);
}
