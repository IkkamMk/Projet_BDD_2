/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objet.PrecedenceOp;
import objet.Produit;
import objet.operation;
import objet.typeOperation;

/**
 *
 * @author gille
 */
public class MenuProduit extends MyVerticalLayout {

    private vuePrincipale main;

    private Button creerProduit;
    private Button listerProduit;
    private Button supprimerProduitRef;
    private MyHorizontalLayout donnee;
    private TextField ref;
    private TextField des;

    private ComboBox<typeOperation> choixTypeOperation;
    private ComboBox<operation> opPrecedente;
    private Button ListerMachine;

    public MenuProduit(vuePrincipale main) {
        this.main = main;

        this.creerProduit = new Button("Creer un produit");
        this.listerProduit = new Button("Lister les produits");
        this.supprimerProduitRef = new Button("Supprimer le produit avec la Reference indiqué");

        this.add(this.creerProduit, this.listerProduit);

       
        this.creerProduit.addClickListener((event) -> {
            this.DialogNouveauProduit();
        });
        this.listerProduit.addClickListener((event) -> {

            //event. recuperer les coordonnées etc
            this.ListeProduit();
        });
        Notification.show("Menu Produit cliqué");
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

    public void DialogNouveauProduit() { //La reference de structure est dans menu machine
        // Création du Dialog pour la création des Produits
        Dialog createProduitDialog = new Dialog();
        createProduitDialog.setModal(true);

        // Récupérer les informations de la machine depuis les champs du produit
        this.ref = new TextField("References");
        this.des = new TextField("Description");

        Button saveButton = new Button("Ajouter", e -> {
            this.NouveauProduit();

        });
        Button addOperation = new Button("Ajout Operation", e -> {

            this.ajoutOperation();

        });
        Button cancelButton = new Button("Fermer", e -> createProduitDialog.close());
        VerticalLayout textFieldLayout = new VerticalLayout(this.ref, this.des);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, addOperation, cancelButton);
        buttonLayout.setSpacing(true); // Ajouter de l'espacement entre les composants

        createProduitDialog.add(textFieldLayout, buttonLayout);
        createProduitDialog.open();

    }

    public void ajoutOperation() {
        Dialog addOperation = new Dialog();
        addOperation.setModal(true);
        Connection con = this.main.getSessionInfo().getConBdD();

        try {
            //On recupere l'operation precedente si elle existe afin de la donner en choix possible d'operation precedente
            opPrecedente = new ComboBox<>("Operation precedente");
            opPrecedente.setItems(operation.listeOpProd(con, Produit.getIdProduitAvecRef(con, this.ref.getValue())));
            opPrecedente.setItemLabelGenerator(operation::getTypeOperation);
        } catch (SQLException ex) {
            Notification.show("Erreur setup precedence operation");

        }

        try {

            choixTypeOperation = new ComboBox<>("Type operation");
            choixTypeOperation.setItems(typeOperation.toutLesTypeOperation(con));
            choixTypeOperation.setItemLabelGenerator(typeOperation::getDes);
        } catch (SQLException ex) {
            Notification.show("Erreur setup choix type operation");

        }
        Button saveButton = new Button("Ajouter", e -> {
            try {
                int id_type = choixTypeOperation.getValue().getId();
                int id_produit = Produit.getIdProduitAvecRef(con, this.ref.getValue());
                operation nouveau = new operation(id_type, id_produit);
                nouveau.saveInBDD1(con);
                Notification.show("operation " + id_type + " | " + id_produit + " créé");

                if (!(opPrecedente.isEmpty())) {
                    try {
                        int opavant = opPrecedente.getValue().getId();
                        Notification.show("opavant = " + opavant);
                        int opapres = operation.getOperation(con, id_produit, id_type);
                        Notification.show("opapres = " + opapres);
                        PrecedenceOp nouvelle = new PrecedenceOp(opavant, opapres);
                        nouvelle.saveInBDD1(con);
                        Notification.show("precedence créé");
                    } catch (SQLException ex) {
                        Notification.show("Erreur creation precedence op dans ajout operation");

                    }
                }
            } catch (SQLException ex) {
                Notification.show("Erreur ajout d'operation");

            }
            addOperation.close();
        });

        Button cancelButton = new Button("Fermer", e -> addOperation.close());
        VerticalLayout textFieldLayout = new VerticalLayout(this.opPrecedente, this.choixTypeOperation);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setSpacing(true); // Ajouter de l'espacement entre les composants

        addOperation.add(textFieldLayout, buttonLayout);
        addOperation.open();
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
                produitDialog.setWidth("600px");
                produitDialog.setHeight("800px");
                produitDialog.add(new H3("Produit: " + prod.getRef()));

                //bouton supprimé du produit
                Button deleteProd = new Button("Supprimer");
                deleteProd.addClickListener((event) -> {

                    try {
                        Produit.deleteProduitRef(con, prod.getRef());
                        Notification.show("Produit supprimé");
                    } catch (SQLException ex) {
                        Notification.show("Enlevé d'abord les operations ");
                    }

                });
                produitDialog.add(deleteProd);
                produitDialog.add(new Paragraph("Description: " + prod.getDes()));
                List<operation> datas2 = operation.listeOpProd(con, prod.getId());
                for (operation op : datas2) {
                    //bouton supprimer des OPERATION
                    Button deleteOp = new Button("Supp");
                    deleteOp.addClickListener((event) -> {
                        Notification.show("Supprimé ");
                        try {
                            operation.deleteOperationIdGraph(con, op.getId());
                            Notification.show("Operation supprimé");
                        } catch (SQLException ex) {
                        }

                    });
                    MyHorizontalLayout operationSousProd = new MyHorizontalLayout(new Paragraph(op.toString()), deleteOp);
                    produitDialog.add(operationSousProd);

                    //produitDialog.add(new Paragraph(op.toString()));
                    try {
                        List<PrecedenceOp> datas3 = PrecedenceOp.RechercheLesPrecedenceOperationsApres(con, op.getId());
                        for (PrecedenceOp precedence : datas3) {
                            
                            //Bouton supprimer les precedence
                            Button deletePrecedOp = new Button("Supp");
                            deletePrecedOp.addClickListener((event) -> {
                                Notification.show("Supprimé ");
                                try {
                                    PrecedenceOp.deletePrecedenceOpAvantGraph(con, precedence.getOpavant());
                                    Notification.show("Operation supprimé");
                                } catch (SQLException ex) {
                                }

                            });
                            MyHorizontalLayout precedenceOperation = new MyHorizontalLayout(new Paragraph("Succède l'operation : " + precedence.getOpavant()), deletePrecedOp);
                            produitDialog.add(precedenceOperation);
                            //produitDialog.add(new Paragraph("Succède l'operation : " + precedence.getOpavant()));

                        }
                    } catch (SQLException ex) {

                    }
                }
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
