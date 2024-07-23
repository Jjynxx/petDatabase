package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Model;
import model.Owner;
import model.Pet;
import java.awt.*;
import java.util.List;

public class View extends JFrame {

    private JTextArea textArea = new JTextArea();
    private JTable table;
    private JButton btnPets, btnOwners, btnPetsId, btnOwnersId, btnInsertPet, btnInsertOwner, btnDeletePet, btnDeleteOwner, btnUpdatePet, btnUpdateOwner;
    private JPanel buttonPanel1, buttonPanel2, buttonPanel3, buttonPanel4;
    private Model model;

    // Define column names for pets and owners
    private String[] petColumnNames = {"Pet ID", "Type", "Breed", "Name", "Owner ID"};
    private String[] ownerColumnNames = {"Owner ID", "First Name", "Last Name", "Email", "Phone"};

    public View(String title) {
        super(title);
        initComponents();
    }

    private void initComponents() {
        // Layout settings
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
    
        // Initialize text area 
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
    
        // JPanel NORTH (Table area)
        contentPane.add(scrollPane, BorderLayout.NORTH);
    
        // table with initial column names (pet column names)
        DefaultTableModel tableModel = new DefaultTableModel(petColumnNames, 0);
        table = new JTable(tableModel);
    
        // Add table to scroll pane
        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
    
        // I had to create a container to group all of the buttons together
        JPanel buttonPanelContainer = new JPanel(new GridLayout(2, 2));
    
        // Adding panels to create buttons in the GUI
        buttonPanel1 = new JPanel(new GridLayout(1, 1));
        buttonPanel2 = new JPanel(new GridLayout(1, 1));
        buttonPanel3 = new JPanel(new GridLayout(1, 1));
        buttonPanel4 = new JPanel(new GridLayout(1, 1));
    
        // Buttons for the GUI
        btnPets = new JButton("List Pets");
        btnOwners = new JButton("List Owners");
        btnPetsId = new JButton("ID Pet Search");
        btnOwnersId = new JButton("ID Owner Search");
        btnInsertPet = new JButton("Add Pet");
        btnInsertOwner = new JButton("Add Owner");
        btnDeletePet = new JButton("Delete Pet");
        btnDeleteOwner = new JButton("Delete Owner");
        btnUpdatePet = new JButton("Update Pet");
        btnUpdateOwner = new JButton("Update Owner");
    
        // I had to create 4 panels to create space for all the buttons in the look I was hoping for
        // findAll() pets button panel
        buttonPanel1.add(btnPets);
    
        // findAll() owners button panel
        buttonPanel2.add(btnOwners);
    
        // button panel for pet function buttons using the pet table
        buttonPanel3.add(btnPetsId);  // pets by id 
        buttonPanel3.add(btnInsertPet); //add pet
        buttonPanel3.add(btnDeletePet); // delete pet 
        buttonPanel3.add(btnUpdatePet); // update pet 
    
        // button panel to include all the functions for the owner table
        buttonPanel4.add(btnOwnersId); // owner by id 
        buttonPanel4.add(btnInsertOwner); // add owner
        buttonPanel4.add(btnDeleteOwner); // delete owner
        buttonPanel4.add(btnUpdateOwner); // update owner table
    
        // Inserting the button panels into the container
        buttonPanelContainer.add(buttonPanel1);
        buttonPanelContainer.add(buttonPanel2);
        buttonPanelContainer.add(buttonPanel3);
        buttonPanelContainer.add(buttonPanel4);
    
        // Adding button panel container to the SOUTH position of contentPane
        contentPane.add(buttonPanelContainer, BorderLayout.SOUTH);

        // Add action listeners to buttons
        // Functions to find all of each list, also added a function to ensure table
        // headers are correct for the individual tables
        btnPets.addActionListener(e -> {
            // Handle "Find Pets" button click
            List<Pet> pets = model.getPets(); // Call getPets() on the model object
            displayPets(pets);
            setTableColumnNames(petColumnNames);
        });
    
        btnOwners.addActionListener(e -> {
            // Handle "Find Owners" button click
            List<Owner> owners = model.getOwners();
            displayOwners(owners);
            setTableColumnNames(ownerColumnNames);
        });
    
        // Buttons to find by id, specific query can be found in petDAO & ownerDAO
        btnPetsId.addActionListener(e -> {
            // Get pets by IDs
            List<Pet> petsById = model.getPetsById(model.findIds);
    
            // Display the pets with specific IDs in the GUI
            displayPets(petsById);
            setTableColumnNames(petColumnNames);
        });
    
        btnOwnersId.addActionListener(e -> {
            // Get owners by IDs
            List<Owner> ownersById = model.getOwnersById(model.findIds);
    
            // Display the owners with specific ID in the UI
            displayOwners(ownersById);
            setTableColumnNames(ownerColumnNames);
        });

        // action listener for the insert pets button
        btnInsertPet.addActionListener(e -> {
            // Use hardcoded data test the table
            Model.insertHardcodedPets();
            // Display all pets included new insertion
            List<Pet> pets = model.getPets(); 
            displayPets(pets);
            setTableColumnNames(petColumnNames);
        });

        // insert owner action listener
        btnInsertOwner.addActionListener(e -> {
            // Use hardcoded data test the table
            Model.insertHardcodedOwners();
            // Display owners including new insertion
            List<Owner> owners = model.getOwners(); 
            displayOwners(owners);
            setTableColumnNames(ownerColumnNames);
        });

        // buttons to update existing pet records
        btnUpdatePet.addActionListener(e -> {
            // Use hardcoded data test the table
            Model.updatePetHardcode();;
            // Display all pets included new insertion
            List<Pet> pets = model.getPets(); 
            displayPets(pets);
            setTableColumnNames(petColumnNames);
        });

        // buttons to update existing owner records
        btnUpdateOwner.addActionListener(e -> {
            // Use hardcoded data test the table
            Model.updateOwnerHardcode();
            // Display owners including new insertion
            List<Owner> owners = model.getOwners(); 
            displayOwners(owners);
            setTableColumnNames(ownerColumnNames);
        });

        // action listener to the delete pet button
        btnDeletePet.addActionListener(e -> {
            // Delete pets by IDs
            for (Integer id : model.deleteIds) {
                Pet deletedPet = model.deletePetById(id);
                if (deletedPet != null) {
                    System.out.println("Pet with ID " + id + " deleted successfully.");
                } else {
                    System.out.println("Pet with ID " + id + " not found in the database.");
                }
            }

            // Display list after pets have been deleted
            List<Pet> pets = model.getPets(); 
            displayPets(pets);
            setTableColumnNames(petColumnNames);
        });

        // button to delete owner by ID
        btnDeleteOwner.addActionListener(e -> {
            // Delete owners by IDs
            for (Integer id : model.deleteIds) {
                Owner deletedOwner = model.deleteOwnerById(id);
                if (deletedOwner != null) {
                    System.out.println("Owner with ID " + id + " deleted successfully.");
                } else {
                    System.out.println("Owner with ID " + id + " not found in the database.");
                }
            }

            // Display list after owner has been deleted
            List<Owner> owners = model.getOwners(); 
            displayOwners(owners);
            setTableColumnNames(ownerColumnNames);
        });
    }


    // function used to retrieve and display all of the pet data
    public void displayPets(List<Pet> pets) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear previous data

        for (Pet pet : pets) {
            Object[] rowData = {
                    pet.getPetId(),
                    pet.getPetType(),
                    pet.getPetBreed(),
                    pet.getPetName(),
                    pet.getOwnerId()
            };
            model.addRow(rowData);
        }
    }

    // function used to retrieve and display all the owner data
    public void displayOwners(List<Owner> owners) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear previous data

        for (Owner owner : owners) {
            Object[] rowData = {
                    owner.getOwnerId(),
                    owner.getFirstName(),
                    owner.getLastName(),
                    owner.getEmail(),
                    owner.getPhone()
            };
            model.addRow(rowData);
        }
    }

    // ensure table headers is consistant with the data being queried
    private void setTableColumnNames(String[] columnNames) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
    }

    // getters and setters
    public JButton getBtnPets() {
        return btnPets;
    }

    public JButton getBtnOwners() {
        return btnOwners;
    }

    public void displayText(String text) {
        textArea.setText(text);
    }

    public void setModel(Model model) {
        this.model = model;
    }


}