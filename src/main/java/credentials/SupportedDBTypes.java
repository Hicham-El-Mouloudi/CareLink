/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package credentials;

/**
 * @author Hicham El Mouloudi
 * @brief This enum defines supported DB Systems
 * @note This is different from database name
 */
public enum SupportedDBTypes {
    MySQL;
    // Currently we only support MySQL
    static String getDBTypeString(SupportedDBTypes type) {
        switch (type) {
            case MySQL:
                return "mysql";
        
            default:
                System.err.println("SupportedDBTypes : Unsupported type of database !");
                return "";
        }
    }
};
