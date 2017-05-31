/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.uqam.inf4375.tp;

import com.uqam.inf4375.tp.model.Event;
import com.uqam.inf4375.tp.utils.DateUtils;
import com.uqam.inf4375.tp.utils.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class Controller {
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Controller.class, args);
    }
    
    @RequestMapping("/hello")
            String sayHello() {
                return "Hello World!";
            }    
    
            
    @RequestMapping(value = "activites-375e", params ={"de","au"} ,method = RequestMethod.GET)
    String getEvents(@RequestParam("de") String startDate, @RequestParam("au") String endDate){
        return "You are looking for a event between "+ startDate + " and " + endDate;
    }
            
            @RequestMapping(value = "/events", method = RequestMethod.GET)
            String showEvents(){
                try {
                    getMtlEvents();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t";
            }
            
            public List<Event> getMtlEvents() throws MalformedURLException, IOException{
                URL oracle = new URL("http://donnees.ville.montreal.qc.ca/dataset/989ab100-b278-4a96-9967-59ce8116ea55/resource/12382aee-b5c8-4106-9056-f117bcab2cd5/download/programmation375.csv");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));
                
                List<Event> events = new ArrayList<>();
                String inputLine;
                String[] attributs = null;
                while ((inputLine = in.readLine()) != null){
                    if(attributs == null){
                        attributs = inputLine.split(",");
                    }else{
                        Event evt = new Event(inputLine,attributs.length);                         
                        if(evt.getId() == 184){
                            evt.generateLatLng();
                        }
                        events.add(evt);
                    }
                }
                in.close();
                
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                      return o1.getDate().compareTo(o2.getDate());
                    }
                });
                ArrayList<String> unformatted = new ArrayList<>();
                for (Event event : events) {
                    if(!DateUtils.printDates(event.getDate())){
                        unformatted.add(event.getDate());
                    }
                }
                
                System.out.println((events.size() - unformatted.size()) + " / " + events.size() + " formatted dates");
                for (String string : unformatted) {
                    System.out.println(string);
                }
                
                /*int unformattedCount = 0;
                for (Event event : events) {
                    if(DateUtils.getDate(event.getDate()).isEmpty()){
                        System.out.println(event.getDate());
                        unformattedCount ++;
                    }
                }
                System.out.println(unformattedCount +"/"+ events.size() +" unformatted date.");
                */
                
                
                return events;
            }
            
            
            
}
