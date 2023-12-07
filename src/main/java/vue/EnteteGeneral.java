/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.mycompany.projet_m3.Gestion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.SQLException;

public class EnteteGeneral extends MyHorizontalLayout {

    private vuePrincipale main;
    public Button MenuMachine;
    public Button MenuOperation;
    public Button MenuTypeOperation;
    public Button MenuRealise;
    public Button MenuPrecedence;
    public Button MenuProduit;

    
    
    
    public EnteteGeneral(vuePrincipale main) {
        this.main = main;
        this.setWidth("100%");
        this.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        this.MenuMachine = new Button("Menu Machine");
        this.MenuMachine.getStyle().set("fontWeight", "bold");
        this.MenuMachine.getStyle().set("font-size", "1em");
        this.MenuMachine.setHeight("100px");
        this.MenuMachine.setWidth("5cm");
        

        this.MenuMachine.addClickListener((event) -> {
            this.main.setMainContent(new MenuMachine(this.main));
            this.MenuRealise.setEnabled(true);
            this.MenuOperation.setEnabled(true);
            this.MenuTypeOperation.setEnabled(true);
            this.MenuMachine.setEnabled(false);
            this.MenuPrecedence.setEnabled(true);
            this.MenuProduit.setEnabled(true);
            // changer dans les menu eux meme lors de leur creation la taille/couleur du bouton de l'entete pour comprendre ou l'utilisateur se trouve
            
        });
        this.MenuTypeOperation = new Button("Menu Type Operation");
        this.MenuTypeOperation.getStyle().set("fontWeight", "bold");
        this.MenuTypeOperation.getStyle().set("font-size", "1em");
        this.MenuTypeOperation.setHeight("100px");
        this.MenuTypeOperation.setWidth("6cm");
       
        this.MenuTypeOperation.addClickListener((event) -> {
            this.main.setMainContent(new MenuTypeOperation(this.main));
            this.MenuRealise.setEnabled(true);
            this.MenuOperation.setEnabled(true);
            this.MenuTypeOperation.setEnabled(false);
            this.MenuMachine.setEnabled(true);
            this.MenuPrecedence.setEnabled(true);
            this.MenuProduit.setEnabled(true);
            
        });
        this.MenuOperation = new Button("Menu Operation");
        this.MenuOperation.getStyle().set("fontWeight", "bold");
        this.MenuOperation.getStyle().set("font-size", "1em");
        this.MenuOperation.setHeight("100px");
        this.MenuOperation.setWidth("5cm");
        
        this.MenuOperation.addClickListener((event) -> {
            this.main.setMainContent(new MenuOperation(this.main));
            this.MenuRealise.setEnabled(true);
            this.MenuOperation.setEnabled(false);
            this.MenuTypeOperation.setEnabled(true);
            this.MenuMachine.setEnabled(true);
            this.MenuPrecedence.setEnabled(true);
            this.MenuProduit.setEnabled(true);
            
        });
        this.MenuRealise = new Button("Menu Realise");
        this.MenuRealise.getStyle().set("fontWeight", "bold");
        this.MenuRealise.getStyle().set("font-size", "1em");
        this.MenuRealise.setHeight("100px");
        this.MenuRealise.setWidth("5cm");
        
        this.MenuRealise.addClickListener((event) -> {
            this.main.setMainContent(new MenuRealise(this.main));
            this.MenuRealise.setEnabled(false);
            this.MenuOperation.setEnabled(true);
            this.MenuTypeOperation.setEnabled(true);
            this.MenuMachine.setEnabled(true);
            this.MenuPrecedence.setEnabled(true);
            this.MenuProduit.setEnabled(true);
            
        });
        this.MenuPrecedence = new Button("Menu Precedence");
        this.MenuPrecedence.getStyle().set("fontWeight", "bold");
        this.MenuPrecedence.getStyle().set("font-size", "1em");
        this.MenuPrecedence.setHeight("100px");
        this.MenuPrecedence.setWidth("5cm");
        
        this.MenuPrecedence.addClickListener((event) -> {
            this.main.setMainContent(new MenuPrecedence(this.main));
            this.MenuRealise.setEnabled(true);
            this.MenuOperation.setEnabled(true);
            this.MenuTypeOperation.setEnabled(true);
            this.MenuMachine.setEnabled(true);
            this.MenuPrecedence.setEnabled(false);
            this.MenuProduit.setEnabled(true);
            
        });
        this.MenuProduit = new Button("Menu Produit");
        this.MenuProduit.getStyle().set("fontWeight", "bold");
        this.MenuProduit.getStyle().set("font-size", "1em");
        this.MenuProduit.setHeight("100px");
        this.MenuProduit.setWidth("5cm");
        
        this.MenuProduit.addClickListener((event) -> {
            //this.main.setMainContent(new MenuProduit(this.main));
            this.MenuRealise.setEnabled(true);
            this.MenuOperation.setEnabled(true);
            this.MenuTypeOperation.setEnabled(true);
            this.MenuMachine.setEnabled(true);
            this.MenuPrecedence.setEnabled(true);
            this.MenuProduit.setEnabled(false);
            
        });
        
        this.add(this.MenuMachine,this.MenuOperation,this.MenuTypeOperation,this.MenuRealise,this.MenuPrecedence,this.MenuProduit);
        
    }
}
