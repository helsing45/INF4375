/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uqam.inf4375.tp.model;

import com.uqam.inf4375.tp.utils.CSVUtils;
import com.uqam.inf4375.tp.utils.JsonReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Event(String attributesCSV, int attributeCount) {
       String[] attributes = CSVUtils.read(attributesCSV);
        for (int i = 0; i < attributes.length; i++) {
            switch(i){
                case 0:
                    this.id = Integer.valueOf(attributes[i]);
                    break;
                case 1:                    
                    this.name = attributes[i];
                    break;
                case 2:
                    this.descriptions = attributes[i];
                    break;
                case 3:
                    this.arrondissement = attributes[i];
                    break;
                case 4:
                    this.isFree = attributes[i];
                    break;
                case 5:
                    this.eventType = attributes[i];
                    break;
                case 6:
                    this.isOutside = attributes[i];
                    break;
                case 7:
                    this.date = attributes[i];
                    break;
                case 8:
                    this.address = attributes[i];
                    break;
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public String getIsFree() {
        return isFree;
    }

    public String getEventType() {
        return eventType;
    }

    public String getIsOutside() {
        return isOutside;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
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
