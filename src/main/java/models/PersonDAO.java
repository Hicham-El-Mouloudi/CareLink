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
    public boolean deletePerson(int id) throws SQLException {
        String query = "DELETE FROM Person WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        return rows > 0;
    }
    public boolean updatePerson(Person person) throws SQLException {
        String query = "UPDATE Person SET FullName=?, Gender=?, Email=?, DateOfBirth=?, InsuranceDetails=? WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, person.getFullName());
        ps.setString(2, person.getGender());
        ps.setString(3, person.getEmail());
        // If you have a date picker or date field, set it here. For now, set as null if not used:
        if (person.getDateOfBirth() != null) {
            ps.setDate(4, new java.sql.Date(person.getDateOfBirth().getTime()));
        } else {
            ps.setNull(4, java.sql.Types.DATE);
        }
        ps.setString(5, person.getInsuranceDetails());
        ps.setInt(6, person.getId());
        int rows = ps.executeUpdate();
        return rows > 0;
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
            return false;
        }
    }
}
