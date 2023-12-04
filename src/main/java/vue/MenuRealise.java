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
import objet.Realise;
import vue.Tableau.OperationGrid;
import vue.Tableau.RealiseGrid;

/**
 *
 * @author gille
 */
public class MenuRealise extends MyVerticalLayout {
    private vuePrincipale main;
    
    private Button CreerRealise;
    private Button ListerRealiseMachine;
    private Button ListerRealiseType;
    private Button ListerRealiseAll;
    private MyHorizontalLayout Liste;
    private Button SupprimerRealiseIdMachine;
    private Button SupprimerRealiseIdType;
    private MyHorizontalLayout Suppresion;
    private IntegerField id_machine;
    private IntegerField id_type;
    private IntegerField duree;
    private MyHorizontalLayout Donnee;
    
    public MenuRealise(vuePrincipale main) {
        this.main = main;
        this.CreerRealise = new Button("Creer un lien de realisation");
        this.ListerRealiseMachine = new Button("Lister les realisation de chaque mahine (id)");
        this.ListerRealiseType = new Button("Lister les machine qui realise le type d'operation (id)");
        this.ListerRealiseAll = new Button("Lister toutes les realisations");
        this.SupprimerRealiseIdMachine = new Button("Supprimer les types de realisations d'une machine (id)");
        this.SupprimerRealiseIdType = new Button("Supprimer les types d'operations des machines");
        this.id_machine = new IntegerField("Id machine");
        this.id_type = new IntegerField("Id type");
        this.duree = new IntegerField("Duree");
        this.Donnee = new MyHorizontalLayout();
        this.Liste = new MyHorizontalLayout();
        this.Suppresion = new MyHorizontalLayout();
        this.Suppresion.add(SupprimerRealiseIdMachine,SupprimerRealiseIdType);
        this.Liste.add(ListerRealiseAll,ListerRealiseMachine,ListerRealiseType);
        this.Donnee.add(id_machine,id_type,duree);
        this.add(this.CreerRealise,Liste,Suppresion,this.Donnee);
        this.CreerRealise.addClickListener((event) -> {
            Notification.show("Creer realise cliqué");
            this.NouvelleRealisation();
        });
        this.SupprimerRealiseIdMachine.addClickListener((event) -> {
            Notification.show("Suppresion Realise machine cliqué");
            this.SuppressionRealiseIdMachine();
        });
        this.SupprimerRealiseIdType.addClickListener((event) -> {
            Notification.show("Suppresion Realise type cliqué");
            this.SuppressionRealiseIdType();
        });
        this.ListerRealiseMachine.addClickListener((event) -> {
            Notification.show("lister realisation machine");
            this.ListeRealiseIdMachine();
        });
        this.ListerRealiseType.addClickListener((event) -> {
            Notification.show("lister realisation type");
            this.ListeRealiseIdType();
        });
        this.ListerRealiseAll.addClickListener((event) -> {
            Notification.show("lister realisation all");
            this.ListeRealise();
        });
        Notification.show("Menu realise cliqué");
    }

    private void NouvelleRealisation() {
        int id_type = this.id_type.getValue();
        int id_machine = this.id_machine.getValue();
        int duree = this.duree.getValue();
        try {
                Connection con = this.main.getSessionInfo().getConBdD();
                Realise nouveau = new Realise(id_machine, id_type,duree);
                nouveau.saveInBDD1(con);
                Notification.show("Realisation " + id_type +" | " +id_machine +" | "+duree+ " créé");
            } catch (SQLException ex) {
                Notification.show("Erreur creation operation, verifier les donnée");

            }
    }

    private void SuppressionRealiseIdMachine() {
       Connection con = this.main.getSessionInfo().getConBdD();
        try {
            Realise.deleteIdMachineGraph(con, this.id_machine.getValue());
            Notification.show("Realistation" + this.id_machine.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.Menu Realise = erreur sql");
        } 
    }
    private void SuppressionRealiseIdType() {
       Connection con = this.main.getSessionInfo().getConBdD();
        try {
            Realise.deleteIdTypeOperationsGraph(con, this.id_type.getValue());
            Notification.show("Realistation" + this.id_type.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.Menu Realise = erreur sql");
        } 
    }
    public void ListeRealiseIdMachine() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<Realise> datas = Realise.toutRealiseIdMachine(con,this.id_machine.getValue());
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuRealise(this.main));
            });
            this.add(effacerTableau,new RealiseGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste realise machine");
        }
    }
    public void ListeRealiseIdType() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<Realise> datas = Realise.toutRealiseIdType(con,this.id_type.getValue());
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuRealise(this.main));
            });
            this.add(effacerTableau,new RealiseGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste realise Type");
        }
    }
    public void ListeRealise() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<Realise> datas = Realise.toutRealise(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuRealise(this.main));
            });
            this.add(effacerTableau,new RealiseGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste realise");
        }
    }
    
    
}
