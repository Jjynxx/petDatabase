package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Model;
import model.Owner;
import model.Pet;
import view.View;

public class Controller implements ActionListener {

    Model model;
    View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        // Assign the model object to the view
        this.view.setModel(model);
        // Action listeners to determine which buttons are being used
        view.getBtnPets().addActionListener(this);
        view.getBtnOwners().addActionListener(this);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        // ensure the pets buttons work for the pet table
        if (e.getSource() == view.getBtnPets()) {
            List<Pet> pets = model.getPets();
            view.displayPets(pets); // list the pets
            // if the owner related buttons are pressed it takes from theowner table
        } else if (e.getSource() == view.getBtnOwners()) {
            List<Owner> owners = model.getOwners();
            view.displayOwners(owners);
        } 
}
}


    

