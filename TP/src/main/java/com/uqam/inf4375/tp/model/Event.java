/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uqam.inf4375.tp.model;

import com.uqam.inf4375.tp.utils.CSVUtils;
import com.uqam.inf4375.tp.utils.DateUtils;
import com.uqam.inf4375.tp.utils.JsonReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Event {
    private int id;
    private String name;
    private String descriptions;
    private String arrondissement;
    private String isFree;
    private String eventType;
    private String isOutside;
    private String date;
    private String address;
    private double lat;
    private double lng;
    private ArrayList<Date> dates;

    public Event(String attributesCSV, int attributeCount) {
       String[] attributes = CSVUtils.read(attributesCSV);
        for (int i = 0; i < attributes.length; i++) {
            switch(i){
                case 0:
                    setId(Integer.valueOf(attributes[i]));
                    break;
                case 1:                    
                    setName(attributes[i]);
                    break;
                case 2:
                    setDescriptions(attributes[i]);
                    break;
                case 3:
                    setArrondissement(attributes[i]);
                    break;
                case 4:
                    setIsFree(attributes[i]);
                    break;
                case 5:
                    setEventType(attributes[i]);
                    break;
                case 6:
                    setIsOutside(attributes[i]);
                    break;
                case 7:
                    setDate(attributes[i]);
                    break;
                case 8:
                    setAddress(attributes[i]);
                    break;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(String isOutside) {
        this.isOutside = isOutside;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        this.dates = DateUtils.getRightDate(date);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }   
    
    public void generateLatLng(){
        try {
            JSONObject location = JsonReader.getLocationFromAddress(getAddress());
            this.lat = Double.valueOf(location.get("lat").toString());
            this.lng = Double.valueOf(location.get("lng").toString());
        } catch (ParseException | IOException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean inRange(Date startDate, Date endDate){
        ArrayList<Date> rangeDates = DateUtils.getRangeDates(startDate, endDate);
        for (Date rangeDate : rangeDates) {
            if(dates == null) return false;
            for (Date eventDate : dates) {
                if(org.apache.commons.lang.time.DateUtils.isSameDay(rangeDate, eventDate)){
                    return true;
                }
            }            
        }
        return false;
    }
    
    @Override
    public String toString(){
       String value = "----------------------------------\n";
        value += "id :"+id+"\n";
        value += "name :" + name + "\n";
        value += "descriptions :" + descriptions + "\n";
        value += "arrondissement :" + arrondissement + "\n";
        value += "isFree :" + isFree + "\n";
        value += "eventType :" + eventType + "\n";
        value += "isOutside :" + isOutside + "\n";
        value += "date :" + date + "\n";
        value += "address :" + address + "\n";
        value += "lat/lng :(" +lat + " ; "+lng+" )\n";
       value += "----------------------------------\n";
       return value;       
    }
    
    
}
