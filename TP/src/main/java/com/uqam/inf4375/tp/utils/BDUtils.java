/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.uqam.inf4375.tp.utils;

import com.uqam.inf4375.tp.model.Event;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import java.sql.*;
import org.apache.commons.dbcp.*;

/**
 *
 * @author j-c9
 */
public class BDUtils {
    public static final String URL_FORMAT = "jdbc:postgresql://%1$s:%2$s/%3$s?sslmode=require&user=%4$s&password=%5$s";
    public static final String HOST = "ec2-107-21-99-176.compute-1.amazonaws.com";
    public static final String PORT = "5432";
    public static final String DB_NAME = "d4p8r9mm3rlo1i";
    public static final String USERNAME = "aafwyfbneahqve";
    public static final String PASSWORD = "5c65c07391f6ad20e2a77f9f00f27bed30628000b2dd6d6ac1a285f820a0089a";
    public static final String URL = "postgres://aafwyfbneahqve:5c65c07391f6ad20e2a77f9f00f27bed30628000b2dd6d6ac1a285f820a0089a@ec2-107-21-99-176.compute-1.amazonaws.com:5432/d4p8r9mm3rlo1i";
    
    private BasicDataSource connectionPool;
    
    public BDUtils() {
        try {
            init();
        } catch (SQLException|URISyntaxException ex) {
            Logger.getLogger(BDUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init() throws URISyntaxException, SQLException {
        URI dbUri = new URI(URL);
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
        connectionPool = new BasicDataSource();
        
        if (dbUri.getUserInfo() != null) {
            connectionPool.setUsername(dbUri.getUserInfo().split(":")[0]);
            connectionPool.setPassword(dbUri.getUserInfo().split(":")[1]);
        }
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(dbUrl);
        connectionPool.setInitialSize(1);
    }
    
    
    @Bean
    public static BasicDataSource getDataSource() throws URISyntaxException {
        /*URI dbUri = new URI(HOST);
        
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();*/
        String url = String.format(URL_FORMAT,HOST,PORT,DB_NAME,USERNAME,PASSWORD);
        
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);
        
        return basicDataSource;
    }
    
    public List<Event> getAllEvents(){
        try {
            Connection connection = connectionPool.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("SELECT * FROM public.\"Event\" ORDER BY id ASC ");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
            while (rs.next()) {
                boolean b = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*public static List<Event> getAllEvents(){
        try {
            List<Event> events = new ArrayList<>();
            Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM public.\"Event\" ORDER BY id ASC ");
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                boolean b = true;
            }
            return events;
        } catch (URISyntaxException | SQLException ex) {
            Logger.getLogger(BDUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }*/
}
