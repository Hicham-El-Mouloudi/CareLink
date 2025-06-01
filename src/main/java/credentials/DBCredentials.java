/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package credentials;

import java.sql.*;

/**
 * @author Hicham El Mouloudi
 * @brief This class provides an iterface for changeing the DB credentials for connexion
 * @note Default URL credentials :  jdbc, mysql, 3306, ApplicationDeSuiviDesTraitementsMedicaux
 * @note Default user = "root"
 * @note Default  password = ""
 */
public class DBCredentials {
    // 'Connection' credentials
    static private Connection theConnection;
    private String user;
    private String password;
    // url components
    String protocol;
    String subProtocol;
    String host;
    long port;
    String dbName;
    // 
    public String getuser(){
        return user;
    }
    public String getPasswd(){
        return password;
    }

    private static DBCredentials dbc;
    public DBCredentials(){
        user = "root";
        password = "";
        // 
        protocol = "jdbc";
        subProtocol = "mysql";
        host = "localhost";
        port = 3306;
        dbName = "ApplicationDeSuiviDesTraitementsMedicaux";
    }
    // The core Credential Object
    static public DBCredentials getCredentials(){
        if (dbc == null) {
            return new DBCredentials();
        }
        return dbc;
    }
    // Return new Connection
    public Connection getConnection() {
        try {
            theConnection = DriverManager.getConnection(getURL(), user, password);
            if (theConnection == null) {
                System.out.println("DBCredentials : Connection to DB returned null!");
                return null;
            }
            return theConnection;
        }catch(SQLException e) {
            System.err.println("DBCredentials : Error in connection to DB '" + dbName + " using user = " + user + " and password = " + password);
            e.printStackTrace();
            return null;
        }
    }

    // 
    public String getURL() {
        return protocol + ":" + subProtocol + "://" + host + ":" + port + "/" + dbName ;
    }
    
    // ------------- Setters For url components -> @note : They are Only called once !------------
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public void setSubProtocol(SupportedDBTypes subProtocol) {
        this.subProtocol = SupportedDBTypes.getDBTypeString(subProtocol);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    // --------------------- Setters for : 'Connection' credentials --------------
    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
