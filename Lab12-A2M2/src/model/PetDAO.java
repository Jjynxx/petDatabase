package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PetDAO implements DAO<Pet> {
    @Override
    public List<Pet> findAll() {
        // will retrieve all pets from the pet table
        String query = "SELECT * FROM Pet";

        List<Pet> petList = null;

        try (
                Connection connection = Database.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);) {

            petList = new ArrayList<>();
            while (resultSet.next()) {
                Pet pet = createPetFromResult(resultSet);
                petList.add(pet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return petList;
    }

    private Pet createPetFromResult(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.petId = resultSet.getInt(1); // getInt(1);
        pet.petType = resultSet.getString(2);
        pet.petBreed = resultSet.getString(3);
        pet.petName = resultSet.getString(4);
        pet.ownerId = resultSet.getInt(5);
        return pet;
    }

    @Override
    public Pet findById(Integer id) {
        String query = "SELECT * FROM Pet WHERE pet_Id = " + id;
        try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                return createPetFromResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public Pet insert(Pet t) {
        // Define the SQL query to check if the pet already exists
        String checkQuery = "SELECT COUNT(*) FROM pet WHERE pet_Name = ? AND pet_owner = ?";
        
        // Define the SQL query to insert a new pet record into the "pet" table
        String insertQuery = "INSERT INTO pet (pet_Type, pet_Breed, pet_Name, pet_owner) VALUES (?, ?, ?, ?)";
    
        try (
            Connection connection = Database.getConnection();
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            // Set the parameters for the check query
            checkStatement.setString(1, t.getPetName());
            checkStatement.setInt(2, t.getOwnerId());
    
            // Execute the check query to see if the pet already exists
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int petCount = resultSet.getInt(1);
            
            // If the pet already exists for the same owner, return null
            if (petCount > 0) {
                System.out.println("Pet with name " + t.getPetName() + " already exists for owner ID " + t.getOwnerId());
                return null;
            }
            
            // If the pet doesn't exist, proceed with insertion
            insertStatement.setString(1, t.getPetType());
            insertStatement.setString(2, t.getPetBreed());
            insertStatement.setString(3, t.getPetName());
            insertStatement.setInt(4, t.getOwnerId());
    
            // Execute the SQL query to insert the pet record
            int rowsAffected = insertStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                // Retrieve the auto-generated key (pet_id) if insertion was successful
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int petId = generatedKeys.getInt(1);
                    t.setPetId(petId); 
                    System.out.println("Pet record inserted successfully with ID: " + petId);
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Failed to insert pet record.");
        return null; 
    }

    @Override
    public Pet update(Integer id, Pet pet) {
        String query = "UPDATE Pet SET pet_Type = ?, pet_Breed = ?, pet_Name = ?, pet_owner = ? WHERE pet_Id = ?";
        try (
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            // parameters for prep statement
            preparedStatement.setString(1, pet.getPetType());
            preparedStatement.setString(2, pet.getPetBreed());
            preparedStatement.setString(3, pet.getPetName());
            preparedStatement.setInt(4, pet.getOwnerId());
            preparedStatement.setInt(5, id);
            int rowsAffected = preparedStatement.executeUpdate();
            // check to see if pet was updated
            if (rowsAffected > 0)
                return pet;
            else 
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    

    @Override
    public boolean delete(Integer id) {

        String query = "DELETE FROM PET WHERE pet_ID = " + id;
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
