/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import vue.Tableau.MachineGrid;
import com.mycompany.projet_m3.Gestion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import objet.machine;

/**
 *
 * @author gille
 */
public class MenuMachine extends MyVerticalLayout {

    private vuePrincipale main;

    private Button CreerMachine;
    private Button RechercherMachine;
    private Button SupprimerMachineRef;
    private MyHorizontalLayout Donnee;
    private TextField Ref;
    private TextField Des;
    private IntegerField Puissance;

    private Button ListerMachine;

    public MenuMachine(vuePrincipale main) {
        this.main = main;
        this.CreerMachine = new Button("Creer une machine");
        this.ListerMachine = new Button("Lister les machines");
        this.SupprimerMachineRef = new Button("Supprimer la machine avec la Reference indiqué");
        this.Ref = new TextField("References");
        this.Des = new TextField("Description");
        this.Puissance = new IntegerField("Puissance");
        this.Donnee = new MyHorizontalLayout();
        this.Donnee.add(Ref, Des, Puissance);
        this.add(this.CreerMachine, SupprimerMachineRef, this.ListerMachine, this.Donnee);
        
        this.SupprimerMachineRef.addClickListener((event) -> {
            this.DeleteMachine();
        });
        this.CreerMachine.addClickListener((event) -> {
            this.NouvelleMachine();
        });
        this.ListerMachine.addClickListener((event) -> {
            
            //event. recuperer les coordonnées etc
            
            this.ListeMachine();
        });
        Notification.show("Menu machine cliqué");
    }

    public void DeleteMachine() {
        Connection con = this.main.getSessionInfo().getConBdD();
        try {
            machine.deleteMachineRefGraph(con, this.Ref.getValue());
            Notification.show("machine" + this.Ref.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.MenuMachine.<init>() = erreur sql");
        }
        
    }

    public void NouvelleMachine() {
        String ref = this.Ref.getValue();
        String des = this.Des.getValue();
        int puissance = this.Puissance.getValue();

        if (!((ref.isBlank()) && (des.isBlank()))) {
            try {
                Connection con = this.main.getSessionInfo().getConBdD();
                machine nouveau = new machine(ref, des, puissance);
                nouveau.saveInBDD1(con);
                Notification.show("machine " + ref + " créé");
            } catch (SQLException ex) {
                Notification.show("Erreur creation de machine, verifier les donnée(ref est unique)");

            }
        } else {
            Notification.show("Veuillez entrer une description, une reference et une puissance conforme");
        }

    }
    /*
    public void ListeMachine() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<machine> datas = machine.toutesLesMachines(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuMachine(this.main));
            });
            this.add(effacerTableau,new MachineGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste machine");
        }
    }*/
    public void ListeMachine() {
        Connection con = this.main.getSessionInfo().getConBdD();

        try {
            List<machine> datas = machine.toutesLesMachines(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuMachine(this.main));
            });

            VerticalLayout machinesLayout = new VerticalLayout();

            for (machine mach : datas) {
                Dialog machineDialog = new Dialog();
                machineDialog.setWidth("300px");
                machineDialog.setHeight("200px");
                machineDialog.add(new H3("Machine: " + mach.getRef()));
                machineDialog.add(new Paragraph("Description: " + mach.getDes()));

                // Créer un bouton pour chaque machine avec le nom ou la référence
                Button machineButton = new Button(mach.getRef());
                machineButton.getElement().setAttribute("draggable", "true");
                machineButton.addClickListener(e -> machineDialog.open());

                // Définir l'élément comme étant draggable
                DragSource<Button> dragSource = DragSource.create(machineButton);
                dragSource.setDragData(mach);

                machinesLayout.add(machineButton);
            }

            this.add(effacerTableau, machinesLayout);

        } catch (SQLException ex) {
            Notification.show("Erreur liste machine");
    }
}

}
