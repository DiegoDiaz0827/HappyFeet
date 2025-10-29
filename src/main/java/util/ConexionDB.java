/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author camper
 */
public abstract class ConexionDB {
    
     private static String URL = "jdbc:mysql://localhost:3306/HappyFeet";
    private static String USER = "campus2023";
    private static String PASS = "campus2023";
    
    public static Connection conectar(){
    
        try {
            return DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException e) {
            System.out.println("Error conexion DB- error al intectar conectar" + e.getMessage());
            return null;
            
            
        }
    
    
    }
    
}
