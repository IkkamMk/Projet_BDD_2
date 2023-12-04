/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.IntegerField;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objet.operation;
import vue.Tableau.OperationGrid;

/**
 *
 * @author gille
 */
public class MenuOperation extends MyVerticalLayout {
    
    private vuePrincipale main;
    
    private Button CreerOperation;
    private Button ListerOperation; 
    private Button SupprimerOperationId;
    private IntegerField id;
    private IntegerField id_type;
    private IntegerField id_produit;
    private MyHorizontalLayout Donnee;
    
            
    public MenuOperation(vuePrincipale main) {
        this.main = main;
        this.CreerOperation = new Button("Creer une operation");
        this.ListerOperation = new Button("Lister les operation");
        this.SupprimerOperationId = new Button("Supprimer l'operation avec l'id indiqué");
        this.id = new IntegerField("Id (pour suppression)");
        this.id_type = new IntegerField("Id type");
        this.id_produit = new IntegerField("Id produit");
        this.Donnee = new MyHorizontalLayout();
        this.Donnee.add(id_type,id_produit);
        this.add(this.CreerOperation,this.Donnee, SupprimerOperationId,this.id, this.ListerOperation);
        this.SupprimerOperationId.addClickListener((event) -> {
            Notification.show("supprimé operation cliqué");
            this.DeleteOperationId();
        });
        this.CreerOperation.addClickListener((event) -> {
            Notification.show("creer operation cliqué");
            this.NouvelleOperation();
        });
        this.ListerOperation.addClickListener((event) -> {
            Notification.show("lister operation cliqué");
            this.ListeOperation();
        });
        Notification.show("Menu operation cliqué");
    }

    private void DeleteOperationId() {
        Connection con = this.main.getSessionInfo().getConBdD();
        try {
            operation.deleteOperationIdGraph(con, this.id.getValue());
            Notification.show("operation" + this.id.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.MenuOperation = erreur sql");
        }
    }
    public void NouvelleOperation() {
        int id_type = this.id_type.getValue();
        int id_produit = this.id_produit.getValue();
            try {
                Connection con = this.main.getSessionInfo().getConBdD();
                operation nouveau = new operation(id_type, id_produit);
                nouveau.saveInBDD1(con);
                Notification.show("operation " + id_type +" | " +id_produit+ " créé");
            } catch (SQLException ex) {
                Notification.show("Erreur creation operation, verifier les donnée");

            }
        
    }
    public void ListeOperation() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<operation> datas = operation.toutesLesOperations(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuOperation(this.main));
            });
            this.add(effacerTableau,new OperationGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste operation");
        }
    }
    
}
