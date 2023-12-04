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
import objet.PrecedenceOp;
import vue.Tableau.MachineGrid;
import vue.Tableau.PrecedenceOpGrid;

/**
 *
 * @author gille
 */
public class MenuPrecedence extends MyVerticalLayout {

    private vuePrincipale main;

    private Button CreerLienOp;
    private Button SupprimerOpAvant;
    private Button SupprimerOpApres;
    private MyHorizontalLayout Suppression;
    private Button ListerPrecedence;
    private Button ListerPrecedenceAvant;
    private Button ListerPrecedenceApres;
    private MyHorizontalLayout Liste;

    private MyHorizontalLayout Donnee;
    private IntegerField OpAvant;
    private IntegerField OpApres;

    public MenuPrecedence(vuePrincipale main) {
        this.main = main;
        this.CreerLienOp = new Button("Creer un lien");
        this.SupprimerOpAvant = new Button("Supp OpAvant");
        this.SupprimerOpApres = new Button("Supp OpApres");
        this.ListerPrecedence = new Button("Lister Precedence");
        this.ListerPrecedenceAvant = new Button("Lister Precedence opavant");
        this.ListerPrecedenceApres = new Button("Lister Precedence opapres");
        this.Liste = new MyHorizontalLayout();
        this.Liste.add(this.ListerPrecedence,this.ListerPrecedenceAvant,this.ListerPrecedenceApres);
        
        this.OpAvant = new IntegerField("OpAvant");
        this.OpApres = new IntegerField("OpApres");
        this.Donnee = new MyHorizontalLayout();
        this.Donnee.add(OpAvant, OpApres);
        
        this.Suppression = new MyHorizontalLayout();
        this.Suppression.add(SupprimerOpAvant, SupprimerOpApres);
        this.add(this.CreerLienOp, this.Suppression, this.Donnee, this.Liste);

        this.CreerLienOp.addClickListener((event) -> {
            this.NouveauLien();
        });
        this.SupprimerOpAvant.addClickListener((event) -> {
            this.SupprimerAvant(); 
        });
        this.SupprimerOpApres.addClickListener((event) -> {
            this.SupprimerApres();
        });
        this.ListerPrecedence.addClickListener((event) -> {
            this.ListerPrecedenceOp();
        });
        this.ListerPrecedenceAvant.addClickListener((event) -> {
            this.ListerPrecedenceOpAvant();
        });
        this.ListerPrecedenceApres.addClickListener((event) -> {
            this.ListerPrecedenceOpApres();
        });
        Notification.show("Menu precedence cliqué");
    }

    public void NouveauLien() {
        int OpAvant = this.OpAvant.getValue();
        int OpApres = this.OpApres.getValue();

        try {
            Connection con = this.main.getSessionInfo().getConBdD();
            PrecedenceOp nouveau = new PrecedenceOp(OpAvant, OpApres);
            nouveau.saveInBDD1(con);
            Notification.show("Lien " + OpAvant + " : "+ OpApres + "créé");
        } catch (SQLException ex) {
            Notification.show("Erreur creation de lien, verifier les donnée");

        }

    }
    public void SupprimerAvant() {
        Connection con = this.main.getSessionInfo().getConBdD();
        try {
            PrecedenceOp.deletePrecedenceOpAvantGraph(con, this.OpAvant.getValue());
            Notification.show("Lien avec" + this.OpAvant.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.MenuPrecedence.<init>() = erreur sql");
        }
        
    }
    public void SupprimerApres() {
        Connection con = this.main.getSessionInfo().getConBdD();
        try {
            PrecedenceOp.deletePrecedenceOpApresGraph(con, this.OpApres.getValue());
            Notification.show("Lien avec" + this.OpApres.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.MenuPrecedence.<init>() = erreur sql");
        }
        
    }
public void ListerPrecedenceOp() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<PrecedenceOp> datas = PrecedenceOp.toutesLesPrecedenceOperations(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuPrecedence(this.main));
            });
            this.add(effacerTableau,new PrecedenceOpGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste precedence operation");
        }
    }
public void ListerPrecedenceOpAvant() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<PrecedenceOp> datas = PrecedenceOp.RechercheLesPrecedenceOperationsAvant(con,this.OpAvant.getValue());
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuPrecedence(this.main));
            });
            this.add(effacerTableau,new PrecedenceOpGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste precedence operation");
        }
    }
public void ListerPrecedenceOpApres() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        try {
            List<PrecedenceOp> datas = PrecedenceOp.RechercheLesPrecedenceOperationsApres(con,this.OpApres.getValue());
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuPrecedence(this.main));
            });
            this.add(effacerTableau,new PrecedenceOpGrid(datas));
            
        } catch (SQLException ex) {
            Notification.show("Erreur liste precedence operation");
        }
    }


}
