package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO implements DAO<Owner> {
    @Override
    public List<Owner> findAll() {

        String query = "SELECT * FROM Owner";

        List<Owner> ownerList = null;

        try (
                Connection connection = Database.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);) {

            ownerList = new ArrayList<>();
            while (resultSet.next()) {
                Owner owner = createOwnerFromResult(resultSet);
                ownerList.add(owner);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ownerList;
    }

    private Owner createOwnerFromResult(ResultSet resultSet) throws SQLException {
        Owner owner = new Owner();
        owner.ownerId = resultSet.getInt(1); // getInt(1);
        owner.firstName = resultSet.getString(2);
        owner.lastName = resultSet.getString(3);
        owner.email = resultSet.getString(4);
        owner.phone = resultSet.getString(5);
        return owner;
    }

    @Override
    public Owner findById(Integer id) {
        // select all values from the database that have the ID input
        String query = "SELECT * FROM owner WHERE owner_Id = " + id;
        try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                return createOwnerFromResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public Owner insert(Owner t) {
        // Define the SQL query to check if an owner with the same first and last name already exists
        String checkQuery = "SELECT COUNT(*) FROM owner WHERE first_Name = ? AND last_Name = ?";
        
        // Define the SQL query to insert a new owner record into the "owner" table
        String insertQuery = "INSERT INTO owner (first_Name, last_Name, email, phone) VALUES (?, ?, ?, ?)";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            // Set the parameters for the check query
            checkStatement.setString(1, t.getFirstName());
            checkStatement.setString(2, t.getLastName());

            // Execute the check query to see if an owner with the same first and last name already exists
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int ownerCount = resultSet.getInt(1);
            
            // If an owner with the same first and last name already exists, return null
            if (ownerCount > 0) {
                System.out.println("Owner with name " + t.getFirstName() + " " + t.getLastName() + " already exists.");
                return null;
            }
            
            // If the owner doesn't exist, proceed with insertion
            insertStatement.setString(1, t.getFirstName());
            insertStatement.setString(2, t.getLastName());
            insertStatement.setString(3, t.getEmail());
            insertStatement.setString(4, t.getPhone());

            // Execute the SQL query to insert the owner record
            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the auto-generated key (owner_id) if insertion was successful
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int ownerId = generatedKeys.getInt(1);
                    t.setOwnerId(ownerId); // Set the generated owner_id in the Owner object
                    System.out.println("Owner record inserted successfully with ID: " + ownerId);
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Failed to insert owner record.");
        return null;
    }

    @Override
    public Owner update(Integer id, Owner owner) {
        String query = "UPDATE owner SET first_Name = ?, last_Name = ?, email = ?, phone = ? WHERE owner_Id = ?";
        try (
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            // parameters for prep statement
            preparedStatement.setString(1, owner.getFirstName());
            preparedStatement.setString(2, owner.getLastName());
            preparedStatement.setString(3, owner.getEmail());
            preparedStatement.setString(4, owner.getPhone());
            preparedStatement.setInt(5, id);
            int rowsAffected = preparedStatement.executeUpdate();
            // check to see if pet was updated
            if (rowsAffected > 0)
                return owner;
            else 
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    

        @Override
        public boolean delete(Integer id) {
            String query = "DELETE FROM OWNER WHERE owner_ID = " + id;
            try (
                Connection connection = Database.getConnection();
                Statement statement = connection.createStatement();
            ) {
                int rowsAffected = statement.executeUpdate(query);
                // ensure rows deleted should be > 0
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false; 
            }
        }
}
