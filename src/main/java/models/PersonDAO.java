/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class PersonDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public PersonDAO() {
        url = "jdbc:mysql://localhost:3306/ApplicationDeSuiviDesTraitementsMedicaux";
        user = "root";
        password = "";
        try {
            connectionToDB = DriverManager.getConnection(url, user, password);
            if (connectionToDB == null) {
                System.out.println("Connection to DB returned null!");
            }
        } catch (SQLException e) {
            System.out.println("Error in connection to DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM Person";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            persons.add(new Person(
                rs.getInt("Id"),
                rs.getString("FullName"),
                rs.getString("Gender"),
                rs.getString("Email"),
                rs.getDate("DateOfBirth"),
                rs.getString("InsuranceDetails")
            ));
        }
        return persons;
    }
    public void insertPerson(Person p) throws SQLException {
        String query = "INSERT INTO Person(FullName, Gender, Email, DateOfBirth, InsuranceDetails) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, p.getFullName());
        ps.setString(2, p.getGender());
        ps.setString(3, p.getEmail());
        ps.setDate(4, new java.sql.Date(p.getDateOfBirth().getTime()));
        ps.setString(5, p.getInsuranceDetails());
        ps.executeUpdate();
    }
    public Person getPersonById(int id) throws SQLException {
        String query = "SELECT * FROM Person WHERE Id = ?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Person(
                rs.getInt("Id"),
                rs.getString("FullName"),
                rs.getString("gender"),
                rs.getString("Email"),
                rs.getDate("DateOfBirth"),
                rs.getString("InsuranceDetails"));
        }
        return null;
    }
}
