/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;
import java.util.*;

import credentials.DBCredentials;

/**
 *
 * @author lenovo
 */
public class PersonDAO {
    private Connection connectionToDB;
    public PersonDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM Person";
        try (Statement statement = connectionToDB.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("PersonDAO : SQLException occured when calling getAllPersons");
        }
        return persons;
    }
    public int insertPerson(Person p) throws SQLException {
        String query = "INSERT INTO Person(FullName, Gender, Email, DateOfBirth, InsuranceDetails) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, p.getFullName());
        ps.setString(2, p.getGender());
        ps.setString(3, p.getEmail());
        ps.setDate(4, new java.sql.Date(p.getDateOfBirth().getTime()));
        ps.setString(5, p.getInsuranceDetails());
        
        
        int affectedRows = ps.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // return the generated ID
                }
            }
        }
        
        return -1;
        
    }
    
    
    
    public Person getPersonById(int id) throws SQLException {
        String query = "SELECT * FROM Person WHERE Id = ?";
        try (PreparedStatement ps = connectionToDB.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Person(
                        rs.getInt("Id"),
                        rs.getString("FullName"),
                        rs.getString("gender"),
                        rs.getString("Email"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("InsuranceDetails"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("PersonDAO : SQLException occured when calling getPersonById");
        }
        return null;
    }
    public boolean deletePerson(int id) {
        String query = "DELETE FROM Person WHERE Id=?";
        try (PreparedStatement ps = connectionToDB.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("PersonDAO : SQLException occured when calling deletePerson");
            return false;
        }
    }
    public boolean updatePerson(Person person) {
        String query = "UPDATE Person SET FullName=?, Gender=?, Email=?, DateOfBirth=?, InsuranceDetails=? WHERE Id=?";
        try (PreparedStatement ps = connectionToDB.prepareStatement(query)) {
            ps.setString(1, person.getFullName());
            ps.setString(2, person.getGender());
            ps.setString(3, person.getEmail());
            if (person.getDateOfBirth() != null) {
                ps.setDate(4, new java.sql.Date(person.getDateOfBirth().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setString(5, person.getInsuranceDetails());
            ps.setInt(6, person.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("PersonDAO : SQLException occured when calling updatePerson");
            return false;
        }
    }

    /**
     * Deletes all persons from the Person table.
     * @return true if successful, false otherwise
     */
    public boolean deleteAllPersons() {
        try {
            String query = "DELETE FROM Person";
            Statement stmt = connectionToDB.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("PersonDAO : SQLException occured when calling deleteAllPersons");
            return false;
        }
    }
}
