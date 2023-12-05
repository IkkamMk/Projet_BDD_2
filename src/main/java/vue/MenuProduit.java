/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objet.Produit;
import objet.machine;

/**
 *
 * @author gille
 */
public class MenuProduit extends MyVerticalLayout{

   private vuePrincipale main;

    private Button creerProduit;
    private Button listerProduit;
    private Button supprimerProduitRef;
    private MyHorizontalLayout donnee;
    private TextField ref;
    private TextField des;
    

    private Button ListerMachine;

    public MenuProduit(vuePrincipale main) {
        this.main = main;
        this.creerProduit = new Button("Creer un produit");
        this.listerProduit = new Button("Lister les produits");
        this.supprimerProduitRef = new Button("Supprimer le produit avec la Reference indiqué");
        this.ref = new TextField("References");
        this.des = new TextField("Description");
        this.donnee = new MyHorizontalLayout();
        this.donnee.add(ref, des);
        this.add(this.creerProduit, supprimerProduitRef, this.listerProduit, this.donnee);
        
        this.supprimerProduitRef.addClickListener((event) -> {
            this.DeleteProduitRef();
        });
        this.creerProduit.addClickListener((event) -> {
            this.NouveauProduit();
        });
        this.listerProduit.addClickListener((event) -> {
            
            //event. recuperer les coordonnées etc
            
            this.ListeProduit();
        });
        Notification.show("Menu machine cliqué");
    }
 public void DeleteProduitRef() {
        Connection con = this.main.getSessionInfo().getConBdD();
        try {
            Produit.deleteProduitRef(con, this.ref.getValue());
            Notification.show("produit" + this.ref.getValue() + " supprimé");
        } catch (SQLException ex) {
            Notification.show("vue.deleteProduitRef.<init>() = erreur sql");
        }
        
    }
 public void NouveauProduit() {
        String ref = this.ref.getValue();
        String des = this.des.getValue();
        

        if (!((ref.isBlank()) && (des.isBlank()))) {
            try {
                Connection con = this.main.getSessionInfo().getConBdD();
                Produit nouveau = new Produit(ref, des);
                nouveau.saveInBDD1(con);
                Notification.show("produit " + ref + " créé");
            } catch (SQLException ex) {
                Notification.show("Erreur creation de machine, verifier les donnée(ref est unique)");

            }
        } else {
            Notification.show("Veuillez entrer une description, une reference et une puissance conforme");
        }

    }
 public void ListeProduit() {
        Connection con = this.main.getSessionInfo().getConBdD();

        try {
            List<Produit> datas = Produit.tousLesProduits(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuProduit(this.main));
            });

            VerticalLayout produitLayout = new VerticalLayout();

            for (Produit prod : datas) {
                Dialog produitDialog = new Dialog();
                produitDialog.setWidth("300px");
                produitDialog.setHeight("200px");
                produitDialog.add(new H3("Machine: " + prod.getRef()));
                produitDialog.add(new Paragraph("Description: " + prod.getDes()));
                produitDialog.setDraggable(true);
                // Créer un bouton pour chaque machine avec le nom ou la référence
                Button machineButton = new Button(prod.getRef());
                machineButton.getElement().setAttribute("draggable", "true");
                machineButton.addClickListener(e -> produitDialog.open());
                
                // Définir l'élément comme étant draggable
                DragSource<Button> dragSource = DragSource.create(machineButton);
                dragSource.setDragData(prod);

                produitLayout.add(machineButton);
            }

            this.add(effacerTableau, produitLayout);

        } catch (SQLException ex) {
            Notification.show("Erreur liste produit");
    }
}
}
