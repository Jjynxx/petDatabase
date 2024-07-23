package model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Pet> pets;
    private List<Owner> owners;
    private PetDAO petDAO;
    private OwnerDAO ownerDAO;
    public List<Integer> findIds = new ArrayList<>();
    public List<Integer> deleteIds = new ArrayList<>();

    // Constructor
    public Model() {
        super();
        // Initialize DAOs
        petDAO = new PetDAO();
        ownerDAO = new OwnerDAO();

        /* For Testing
         * 
         * I used this class to create an array list of values
         * to be used in the 2 methods searching for id, and 
         * below I have hardcoded functions where I create new
         * and updated data for the database so that I can keep the 
         * different DAO classes as modular as possible for future use
         * and to keep the DAO stuff dedicated to the database 
         * interactions, I hope that is okay.
         * 
         */

        // Hardcoded IDs for testing the find by ID query
        findIds.add(1);
        findIds.add(15);
        findIds.add(100);

        // Hardcoded IDs for testing the delete by ID query

        deleteIds.add(21); // should delete new pet
        deleteIds.add(45); // should delete new owner
        deleteIds.add(100); // for testing
    }

    // Method to retrieve all pets
    public List<Pet> getPets() {
        pets = petDAO.findAll();
        return pets;
    }

    // Method to retrieve pets by IDs
    public List<Pet> getPetsById(List<Integer> ids) {
        List<Pet> petsById = new ArrayList<>();
        
        for (Integer id : ids) {
            Pet pet = petDAO.findById(id);
            if (pet != null) {
                petsById.add(pet);
            } else {
                System.out.println("Pet with ID " + id + " not found.");
            }
        }
        
        return petsById;
    }

    // Method to retrieve all owners
    public List<Owner> getOwners() {
        owners = ownerDAO.findAll();
        return owners;
    }

    // Method to retrieve owners by IDs
    public List<Owner> getOwnersById(List<Integer> ids) {
        List<Owner> ownersById = new ArrayList<>();
        
        for (Integer id : ids) {
            Owner owner = ownerDAO.findById(id);
            if (owner != null) {
                ownersById.add(owner);
            } else {
                System.out.println("Owner with ID " + id + " not found.");
            }
        }
        return ownersById;
    }

    // Methods to find by ID 
    // pet by ID
    public Pet getPetById(Integer id) {
        return petDAO.findById(id);
    }

    // owner by ID
    public Owner getOwnerById(Integer id) {
        return ownerDAO.findById(id);
    }

    // Hardcodeing methods for testing following functions
    // Method to insert hardcoded pets into the database
    public static void insertHardcodedPets() {
       
        Pet newPet = new Pet();
        newPet.setPetId(1001); 
        newPet.setPetType("Cat");         
        newPet.setPetBreed("Siamese"); 
        newPet.setPetName("Whiskers"); 
        newPet.setOwnerId(1); 

        // Instantiate the PetDAO
        PetDAO petDAO = new PetDAO();

        // Call the insert() method to add the new pet record
        Pet insertedPet = petDAO.insert(newPet);

        // Check if the insertion was successful
        if (insertedPet != null) {
            System.out.println("Pet record inserted successfully.");
        } else {
            System.out.println("Failed to insert pet record.");
        }
    }

    // method to insert hardcoded owner into table
    public static void insertHardcodedOwners() {
        
        Owner newOwner = new Owner();
        newOwner.setOwnerId(21);
        newOwner.setFirstName("Sara");
        newOwner.setLastName("McHattie"); 
        newOwner.setEmail("email@google.com");
        newOwner.setPhone("123-456-7890");

        // Instantiate the PetDAO
        OwnerDAO ownerDAO = new OwnerDAO();

        // Call the insert() method to add the new pet record
        Owner insertedOwner = ownerDAO.insert(newOwner);

        // Check if the insertion was successful
        if (insertedOwner != null) {
            System.out.println("Owner record inserted successfully.");
        } else {
            System.out.println("Failed to insert owner record.");
        }
    }

    // hardcoded method for the UPDATE pets query
    public static void updatePetHardcode() {
      
        Pet updatedPet = new Pet();
        updatedPet.setPetId(8); 
        updatedPet.setPetType("Cat"); 
        updatedPet.setPetBreed("Maine Coon");
        updatedPet.setPetName("Winton"); 
        updatedPet.setOwnerId(8); 
    
        // origional record:  ('Cat', 'Maine Coon', 'Mittens', 8)
        // updated the cat's name from Mittens to Winton

        // Instantiate the OwnerDAO
        PetDAO petDAO = new PetDAO();
    
        // Call the update() method to update the existing pet record
        Pet updatedPetRecord = petDAO.update(updatedPet.getPetId(), updatedPet);
    
        // Check if the update was successful
        if (updatedPetRecord != null) {
            System.out.println("Pet " + updatedPet.getPetId() + " updated successfully.");
        } else {
            System.out.println("Failed to update pet " + updatedPet.getPetId());
        }
    }

    // hardcoded method for the UPDATE owners query
    public static void updateOwnerHardcode() {
        
        Owner updatedOwner = new Owner();
        updatedOwner.setOwnerId(7); 
        updatedOwner.setFirstName("Emily"); 
        updatedOwner.setLastName("Phoenix"); 
        updatedOwner.setEmail("just.married@example.com");
        updatedOwner.setPhone("901-234-5678");
    
        // Instantiate the OwnerDAO
        OwnerDAO ownerDAO = new OwnerDAO();
    
        // Call the update() method to update the existing owner record
        Owner updatedOwnerRecord = ownerDAO.update(updatedOwner.getOwnerId(), updatedOwner);
    
        // Check if the update was successful
        if (updatedOwnerRecord != null) {
            System.out.println("Owner " + updatedOwner.getOwnerId() + " updated successfully.");
        } else {
            System.out.println("Failed to update owner " + updatedOwner.getOwnerId());
        }
    }

    // methods to delete pets by ID using values above
    public Pet deletePetById(Integer id) {
        Pet deletedPet = petDAO.findById(id);
        boolean deletionSuccessful = petDAO.delete(id); 
    
        if (deletionSuccessful) {
            return deletedPet;
        } else {
            return null; 
        }
    }

    // method to delete owner by ID 
    public Owner deleteOwnerById(Integer id) {
        Owner deletedOwner = ownerDAO.findById(id); 
        boolean deletionSuccessful = ownerDAO.delete(id); 
    
        if (deletionSuccessful) {
            return deletedOwner; 
        } else {
            return null;
        }
    }
}