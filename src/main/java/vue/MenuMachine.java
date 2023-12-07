/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import vue.Tableau.MachineGrid;
import com.mycompany.projet_m3.Gestion;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import elemental.json.Json;
import elemental.json.JsonObject;
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
    private IntegerField X;
    private IntegerField Y;
       
    private Div zoneMachines;
    private Button ListerMachine;

    public MenuMachine(vuePrincipale main) {
        this.main = main;
        //this.DeleteMachine(); //A n'executer qu'1 fois !!!!!!!!!
            
        this.setSizeFull(); // Ajuste la taille pour occuper tout l'espace disponible
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Centre horizontalement
        this.setAlignItems(FlexComponent.Alignment.CENTER); // Centre verticalement

        // Création du bouton pour ouvrir le Dialog
        this.CreerMachine = new Button("Creer une machine");
        
        CreerMachine.addClickListener(event -> {
            this.DialogCreerMachine();
        });
        
        /*this.ListerMachine = new Button("Lister les machines");
        this.SupprimerMachineRef = new Button("Supprimer la machine avec la Reference indiqué");
        /*this.Ref = new TextField("References");
        this.Des = new TextField("Description");
        this.Puissance = new IntegerField("Puissance");*/
        //this.Donnee = new MyHorizontalLayout();
        //this.Donnee.add(Ref, Des, Puissance);
        this.add(this.CreerMachine/*, SupprimerMachineRef, this.Donnee*/);
        
        /*this.SupprimerMachineRef.addClickListener((event) -> {
            this.DeleteMachine();
        });
        this.CreerMachine.addClickListener((event) -> {
            this.NouvelleMachine();
        });
        /*this.ListerMachine.addClickListener((event) -> {
            
            //event. recuperer les coordonnées etc
            
            this.ListeMachine();
        });*/
        
        this.ZoneMachines();
        Notification.show("Menu Machine cliqué");
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

    public void DialogCreerMachine() {
        // Création du Dialog pour la création de la machine
        Dialog createMachineDialog = new Dialog();
        createMachineDialog.setModal(true);

        // Récupérer les informations de la machine depuis les champs du Dialog
        this.Ref = new TextField("Reference de la machine");
        this.Des = new TextField("Description");
        this.Puissance = new IntegerField("Puissance");
        this.X = new IntegerField("Coordonnée X");
        this.Y = new IntegerField("Coordonnee Y");

        // Ajout des boutons pour valider ou annuler la création
        Button saveButton = new Button("Enregistrer", e -> {

            // Effectuez les opérations nécessaires pour créer la machine dans la base de données
            this.NouvelleMachine();
            // Fermez le Dialog après la création de la machine
            createMachineDialog.close();
            this.ListeMachine();
            
        });
        Button cancelButton = new Button("Annuler", e -> createMachineDialog.close());
        
        HorizontalLayout coord = new HorizontalLayout(this.X, this.Y);
        // Création d'un layout vertical pour organiser les composants
        VerticalLayout textFieldLayout = new VerticalLayout(this.Ref, this.Des, this.Puissance, coord);
        //textFieldLayout.setSpacing(true); // Ajouter de l'espacement entre les composants
        
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setSpacing(true); // Ajouter de l'espacement entre les composants
        
        // Ajout des composants au Dialog
        createMachineDialog.add(textFieldLayout, buttonLayout);
        createMachineDialog.open();
    }
    
    
    public void NouvelleMachine() {
        String ref = this.Ref.getValue();
        String des = this.Des.getValue();
        int puissance = this.Puissance.getValue();
        int X = this.X.getValue();
        int Y = this.Y.getValue();

        if (!((ref.isBlank()) && (des.isBlank()))) {
            try {
                Connection con = this.main.getSessionInfo().getConBdD();
                machine nouveau = new machine(ref, des, puissance, X, Y);
                nouveau.saveInBDD1(con);
                Notification.show("machine " + ref + " créé");
            } catch (SQLException ex) {
                Notification.show("Erreur creation de machine, verifier les donnée(ref est unique)");

            }
        } else {
            Notification.show("Veuillez entrer une description, une reference et une puissance conforme");
        }

    }
    
    public void ZoneMachines() {
        this.zoneMachines = new Div(); // La zone pour afficher les machines
        zoneMachines.getStyle()
                .set("border", "4px solid orange") // Style du cadre orange
                .set("width", "100%") // Largeur à 100% de l'espace disponible
                .set("height", "calc(100vh - 200px)") // Hauteur adaptée (par exemple 50px moins que la hauteur de la page)
                .set("position", "relative"); // Rend la position absolue des éléments internes relative à ce conteneur
                
        H1 titreAtelier = new H1("Atelier");
        titreAtelier.getStyle()
                .set("color", "orange")
                .set("position", "absolute") // Position absolue pour placer le titre
                .set("top", "10px") // Décalage depuis le haut
                .set("left", "50%")
                .set("transform", "translateX(-50%)"); // Pour centrer horizontalement

        zoneMachines.add(titreAtelier);
        
        this.ListeMachineTemp();
        add( zoneMachines);
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
    

    public void ListeMachineTemp() {
        Connection con = this.main.getSessionInfo().getConBdD();
        
        Dialog machineDialog = new Dialog();
        machineDialog.setModal(true);

        Button boutonOuvrirMachine = new Button("Machine : " /*+ m.getRef()*/);
        
        boutonOuvrirMachine.getStyle().set("position", "absolute");
        boutonOuvrirMachine.getStyle().set("left", "200px"); //min 50, max 1250
        boutonOuvrirMachine.getStyle().set("top", "50px"); //min 50, max 
        
        //machineDialog.add(new Text("Test dialogue machine"));
        machineDialog.add(
                new H3("Machine: " /*+ m.getRef()*/),
                new Paragraph("Description: " /*+ m.getDes()*/),
                new Span("Puissance: " /*+ m.getPuissance()*/)
                // Ajoutez d'autres composants pour afficher d'autres détails de la machine si nécessaire
            );
            
        
        // Ajouter un bouton dans le dialogue
        Button boutonSupprimer = new Button("Supprimer cette machine");
        boutonSupprimer.addClickListener(event -> {
            Notification.show("Suppression de cette machine");
        });
        Button boutonDeplacer = new Button("Déplacer cette machine");
        boutonDeplacer.addClickListener(event -> {
            Notification.show("Veuillez clicker sur le plan pour déplacer cette machine");
        });
        // Conteneur pour organiser les boutons verticalement
        HorizontalLayout boutonsLayout = new HorizontalLayout(boutonSupprimer, boutonDeplacer);
        machineDialog.add(boutonsLayout);
        
        boutonOuvrirMachine.addClickListener(event -> {
            machineDialog.open();
        });

        this.zoneMachines.add(boutonOuvrirMachine);

    }
    

public void ListeMachine() {
    // Récupérer la liste des machines depuis la base de données (à adapter selon votre structure de données)
    Connection con = this.main.getSessionInfo().getConBdD();
    try {
        List<machine> machines = machine.toutesLesMachines(con);

        for (machine m : machines) {
            Dialog machineDialog = new Dialog();
            machineDialog.setModal(true);

            Button boutonOuvrirMachine = new Button("Machine : " + m.getRef());

            boutonOuvrirMachine.getStyle().set("position", "absolute");
            boutonOuvrirMachine.getStyle().set("left", m.getX() + "px"); //min 50, max 1250
            boutonOuvrirMachine.getStyle().set("top", m.getY() + "px"); //min 50, max 

            //machineDialog.add(new Text("Test dialogue machine"));
            machineDialog.add(
                    new H3("Machine: " + m.getRef()),
                    new Paragraph("Description: " + m.getDes()),
                    new Span("Puissance: " + m.getPuissance())
                    // Ajoutez d'autres composants pour afficher d'autres détails de la machine si nécessaire
                );


            // Ajouter un bouton dans le dialogue
            Button boutonSupprimer = new Button("Supprimer cette machine");
            boutonSupprimer.addClickListener(event -> {
                Notification.show("Suppression de cette machine");
            });
            Button boutonDeplacer = new Button("Déplacer cette machine");
            boutonDeplacer.addClickListener(event -> {
                Notification.show("Veuillez clicker sur le plan pour déplacer cette machine");
            });
            // Conteneur pour organiser les boutons verticalement
            HorizontalLayout boutonsLayout = new HorizontalLayout(boutonSupprimer, boutonDeplacer);
            machineDialog.add(boutonsLayout);

            boutonOuvrirMachine.addClickListener(event -> {
                machineDialog.open();
            });

            this.zoneMachines.add(boutonOuvrirMachine);
        }
    } catch (SQLException ex) {
        Notification.show("Erreur lors de la récupération des machines depuis la base de données");
    }

        
        
        /*Connection con = this.main.getSessionInfo().getConBdD();
       
        
        try {
            
            List<machine> datas = machine.toutesLesMachines(con);
            Button effacerTableau = new Button("Effacer le tableau");
            effacerTableau.addClickListener((event) -> {
                this.main.setMainContent(new MenuMachine(this.main));
            });
            
            // ... Création du composant représentant le plan (par exemple, un Div)
            Div plan = new Div();
            plan.getStyle().set("width", "800px");
            plan.getStyle().set("height", "600px");
            plan.getStyle().set("border", "1px solid black");

            // ... Ajout des éléments représentant les machines sur le plan (par exemple, Dialogs)
            for (machine Machine : datas) {
                Div machineDiv = new Div();
                machineDiv.setText(Machine.getDes());
                machineDiv.getStyle().set("position", "absolute");
                machineDiv.getStyle().set("left", Machine.getX() + "px");
                machineDiv.getStyle().set("top", Machine.getY() + "px");

                // Ajoutez le Div au plan

                machineDiv.getElement().executeJs(
                    "let div = $0; " +
                    "let x = " + Machine.getX() + "; " +
                    "let y = " + Machine.getY() + "; " +
                    "div.addEventListener('mousedown', e => {" +
                    "   x = e.clientX - div.getBoundingClientRect().left;" +
                    "   y = e.clientY - div.getBoundingClientRect().top;" +
                    "   document.addEventListener('mousemove', move);" +
                    "});" +
                    "document.addEventListener('mouseup', () => {" +
                    "   document.removeEventListener('mousemove', move);" +
                    "});" +
                    "function move(e) {" +
                    "   div.style.left = (e.clientX - x) + 'px';" +
                    "   div.style.top = (e.clientY - y) + 'px';" +
                    "}"
                );

            }
            */
            
            
            
            /*
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
                machineDialog.setDraggable(true);
                // Créer un bouton pour chaque machine avec le nom ou la référence
                Button machineButton = new Button(mach.getRef());
                machineButton.getElement().setAttribute("draggable", "true");
                //machineButton.addClickListener(e -> machineDialog.open());
                
                machineButton.getElement().addEventListener("dragstart", event -> {
                    // Récupérer les données à transférer lors du début du glisser
                    JsonObject dataTransfer = Json.createObject();
                    dataTransfer.put("machineRef", mach.getRef());
                    
                    // Exécuter du code JavaScript pour définir les données à transférer
                    String jsCode = "event.dataTransfer.setData('text/plain', '" + dataTransfer.toJson() + "');";
                    UI.getCurrent().getPage().executeJavaScript(jsCode);

                });
                 /*       
                // Définir l'élément comme étant draggable
                DragSource<Button> dragSource = DragSource.create(machineButton);
                dragSource.setDragData(mach);

                // Ajout de gestionnaire d'événement pour le glisser-déposer
                machineButton.addDragStartListener(event -> {
                    // Récupérer les données de l'événement de démarrage du glisser-déposer
                    event.setDragData(mach);
                    // Indiquer le type de données que vous souhaitez transférer
                    event.setDragImage(new Image("frontend/img.png", "icon"));
                });

                machineButton.addDropListener(event -> {
                    // Empêcher le navigateur d'ouvrir le bouton dans un nouvel onglet
                    event.preventDefault();
                    // Récupérer les données déposées
                    machine data = event.getDragData();
                    // Insérer ici la logique pour gérer l'élément déposé
                    // Par exemple, mettre à jour la position ou effectuer une action spécifique
                });*/
                /*
                machinesLayout.add(machineButton);
            }

            this.add(effacerTableau, machinesLayout);
        */
        /*} catch (SQLException ex) {
            Notification.show("Erreur liste machine");
    }*/
}

}
