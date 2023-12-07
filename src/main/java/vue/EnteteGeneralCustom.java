/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.mycompany.projet_m3.Gestion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import fr.insa.MG.testVaadin.SessionInfo;
import fr.insa.MG.testVaadin.vuePrincipale;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objet.Produit;
import objet.machine;
/**
 *
 * @author dadou
 */

//@Route("tabs-badges")
public class EnteteGeneralCustom extends Div {
    private vuePrincipale main;
    
    private int nbrMachines;
    private int nbrProduits;
    private int nbrOperateurs;
    
    private Tab MenuMachine;
    private Tab MenuProduit;
    private Tab MenuOperateur;
    private Tab Disconnect;
    private Tabs tabs;

    public EnteteGeneralCustom(vuePrincipale main) {
        this.main = main;
        //Connection con = this.main.getSessionInfo().getConBdD();
        
        this.MaJ_Badges();
        MenuMachine = new Tab(VaadinIcon.USER.create(), new Span("Machines"), createBadge(nbrMachines)); //machine.toutesLesMachines(this.main.getSessionInfo().getConBdD()).size())
        //Tab MenuOperation = new Tab(VaadinIcon.COG.create(), new Span("Menu Operation"), createBadge(439));
        //Tab MenuTypeOperation = new Tab(VaadinIcon.USER.create(), new Span("Menu Type Operation"), createBadge(24));
        //Tab MenuRealise = new Tab(VaadinIcon.USER.create(), new Span("Menu Realise"), createBadge(24));
        MenuProduit = new Tab(VaadinIcon.USER.create(), new Span("Produits"), createBadge(24));
        MenuOperateur = new Tab(VaadinIcon.USER.create(), new Span("Opérateurs"), createBadge(24));
        //Tab MenuPrecedence = new Tab(VaadinIcon.USER.create(), new Span("Menu Precedence"), createBadge(24));
        Disconnect = new Tab(new Span("Se déconnecter"), createBadge(5));

        tabs = new Tabs(MenuMachine, MenuProduit, MenuOperateur, Disconnect);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        
        
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab == MenuMachine) {
                System.out.println("Tab 1 selected");
                this.main.setMainContent(new MenuMachine(this.main));
                this.MaJ_Badges();
                // Votre logique pour le Tab 1
            } /*else if (selectedTab == MenuOperation) {
                System.out.println("Tab 2 selected");
                //this.main.setMainContent(new MenuTypeOperation(this.main));
                this.MaJ_Badges();
                // Votre logique pour le Tab 2
            } else if (selectedTab == MenuTypeOperation) {
                System.out.println("Tab 3 selected");
                this.MaJ_Badges();
                // Votre logique pour le Tab 3
            } else if (selectedTab == MenuRealise) {
                System.out.println("Tab 2 selected");
                this.MaJ_Badges();
                // Votre logique pour le Tab 2
            }*/ else if (selectedTab == MenuProduit) {
                System.out.println("Tab 2 selected");
                this.main.setMainContent(new MenuProduit(this.main));
                this.MaJ_Badges();
                // Votre logique pour le Tab 2
        
            } else if (selectedTab == MenuOperateur) {
                System.out.println("Menu Opérateurs selectionné");
                //this.main.setMainContent(new MenuTypeOperation(this.main));
                //this.main.setMainContent(new MenuOperateur(this.main));
                this.MaJ_Badges();
                // Votre logique pour le Tab 2
            } else if (selectedTab == Disconnect) {
                System.out.println("Deconnexion");
                //this.main.setMainContent(new MenuTypeOperation(this.main));
                this.MaJ_Badges();
                // Votre logique pour le Tab 2
            }
        });
        
        add(tabs);
    }

    /**
     * Helper method for creating a badge.
     */
    private Span createBadge(int value) {
        Span badge = new Span(String.valueOf(value));
        badge.getElement().getThemeList().add("badge small contrast");
        badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        return badge;
    }
    
    private void MaJ_Badges() {
        Connection con = this.main.getSessionInfo().getConBdD();

        try {
            nbrMachines = machine.toutesLesMachines(con).size();
            
            // Créez un nouvel onglet avec le nouveau libellé et badge
            Tab newMenuMachine = new Tab(VaadinIcon.USER.create(), new Span("Machines"), createBadge(nbrMachines));
            /*
            //this.MenuMachine.getElement().removeAllChildren();
            Tabs tabs = (Tabs) getParent();
            tabs.replace(MenuMachine, newMenuMachine);

            // Mettre à jour la référence vers le nouvel onglet
            MenuMachine = newMenuMachine;*/
            /*
            // Remplace l'ancien onglet par le nouvel onglet dans les Tabs
            tabs.replace(MenuMachine, newMenuMachine);

            // Mettre à jour la référence vers le nouvel onglet
            this.MenuMachine = newMenuMachine;
            */
            //MenuMachine.setLabel(new Span("Machines"), createBadge(nbrMachines));
            //nbrOperateurs = operateur.tousLesOperateurs(con).size();
            //MenuOperateur.setLabel(new Span("Operateur"), createBadge(nbrOperateurs));
            nbrProduits = Produit.tousLesProduits(con).size();
            //MenuProduit.setLabel(new Span("Produit"), createBadge(nbrProduits));


        } catch (SQLException ex) {
            Notification.show("Erreur màj des badges machine, produits ou operateurs");
        }
    }
}
