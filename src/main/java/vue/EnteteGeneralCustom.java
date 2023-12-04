/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
/**
 *
 * @author dadou
 */

//@Route("tabs-badges")
public class EnteteGeneralCustom extends Div {

    public EnteteGeneralCustom() {
        Tab MenuMachine = new Tab(VaadinIcon.USER.create(), new Span("Menu Machine"), createBadge(24));
        Tab MenuOperation = new Tab(VaadinIcon.COG.create(), new Span("Menu Operation"), createBadge(439));
        Tab MenuTypeOperation = new Tab(VaadinIcon.USER.create(), new Span("Menu Type Operation"), createBadge(24));
        Tab MenuRealise = new Tab(VaadinIcon.USER.create(), new Span("Menu Realise"), createBadge(24));
        Tab MenuProduit = new Tab(VaadinIcon.USER.create(), new Span("Menu Produit"), createBadge(24));
        Tab MenuPrecedence = new Tab(VaadinIcon.USER.create(), new Span("Menu Precedence"), createBadge(24));
        Tab Disconnect = new Tab(new Span("Se déconnecter"), createBadge(5));

        Tabs tabs = new Tabs(MenuMachine, MenuOperation, MenuTypeOperation, MenuRealise, MenuProduit, MenuPrecedence, Disconnect);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        
        /*
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab == MenuMachine) {
                System.out.println("Tab 1 selected");
                // Votre logique pour le Tab 1
            } else if (selectedTab == MenuOperation) {
                System.out.println("Tab 2 selected");
                // Votre logique pour le Tab 2
            } else if (selectedTab == MenuTypeOperation) {
                System.out.println("Tab 3 selected");
                // Votre logique pour le Tab 3
            } else if (selectedTab == MenuRealise) {
                System.out.println("Tab 2 selected");
                // Votre logique pour le Tab 2
            } else if (selectedTab == MenuProduit) {
                System.out.println("Tab 2 selected");
                // Votre logique pour le Tab 2
        
            }
        });
        */
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

}
