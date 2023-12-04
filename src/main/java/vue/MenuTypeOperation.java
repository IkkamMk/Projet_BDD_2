/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objet.typeOperation;
import vue.Tableau.TypeOperationGrid;

/**
 *
 * @author gille
 */
public class MenuTypeOperation extends MyVerticalLayout  {
    private vuePrincipale main;
    
    private Button CreerTypeOperation;
    private Button ListerTypeOperation; 
    private Button SupprimerTypeOperationId;
    private IntegerField id;
    private TextField description;
    private MyHorizontalLayout Donnee;
    
    public MenuTypeOperation(vuePrincipale main) {
        
        this.main = main;
        this.CreerTypeOperation = new Button("Creer un type d'operation");
        this.ListerTypeOperation = new Button("Lister les Type d'operation");
        this.SupprimerTypeOperationId = new Button("Supprimer le type d'operation avec l'id indiqué");
        this.id = new IntegerField("Id (pour suppression)");
        this.description = new TextField("Description");
        this.Donnee = new MyHorizontalLayout();
        this.Donnee.add(id,description);
        this.add(this.CreerTypeOperation, SupprimerTypeOperationId,this.Donnee, this.ListerTypeOperation);
        this.SupprimerTypeOperationId.addClickListener((event) -> {
            Notification.show("supprimé type operation cliqué");
            this.DeleteTypeOperationId();
        });
        this.CreerTypeOperation.addClickListener((event) -> {
            Notification.show("creer type operation cliqué");
            this.NouveauTypeOperation();
        });
        this.ListerTypeOperation.addClickListener((event) -> {
            Notification.show("lister type operation cliqué");
            this.ListeTypeOperation();
        });
        Notification.show("Menu type operation cliqué");
        
    }
    private void DeleteTypeOperationId() {
        Connection con = this.main.getSessionInfo().getConBdD();
        try {
            typeOperation.deleteTypeOperationIdGraph(con, this.id.getValue());
            Notification.show("type operation" + this.id.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("Menu type Operation effacer = erreur sql");
        }
    }
    public void NouveauTypeOperation() {
        
        String des = this.description.getValue();
            try {
                Connection con = this.main.getSessionInfo().getConBdD();
                typeOperation nouveau = new typeOperation(des);
                nouveau.saveInBDD1(con);
                Notification.show("type operation " + nouveau.getId() + " créé");
            } catch (SQLException ex) {
                Notification.show("Erreur creation type operation, verifier les donnée");

            }
        
    }
    public void ListeTypeOperation() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<typeOperation> datas = typeOperation.toutLesTypeOperation(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuTypeOperation(this.main));
            });
            this.add(effacerTableau,new TypeOperationGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste type operation");
        }
    }
    
}
