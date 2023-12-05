/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.MG.testVaadin;

import com.mycompany.projet_m3.Gestion;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import vue.EnteteGeneralCustom;
import vue.MenuMachine;
import vue.MyVerticalLayout;
import java.sql.SQLException;
import com.mycompany.projet_m3.Gestion;

@Route("")
@PageTitle("Projet M3")
public class vuePrincipale extends MyVerticalLayout {

    public SessionInfo sessionInfo;
    private Div entete_custom;
    private MyVerticalLayout mainContent;

    public void setEntete(Component c) {
        this.entete_custom.removeAll();
        this.entete_custom.add(c);
    }

    public void setMainContent(Component c) {
        this.mainContent.removeAll();
        this.mainContent.add(c);
    }

    public Div getEnteteCustom() {
        return entete_custom;
    }

    public MyVerticalLayout getMainContent() {
        return mainContent;
    }

    public vuePrincipale() {
        this.sessionInfo = new SessionInfo();

        this.entete_custom = new Div();
        this.entete_custom.setWidthFull();
        this.add(this.entete_custom);

        this.mainContent = new MyVerticalLayout();
        this.mainContent.setWidthFull();
        this.mainContent.setHeightFull();
        this.add(this.mainContent);

        try {
            this.sessionInfo.setConBdD(Gestion.connectSurServeurM3());
            this.setEntete(new EnteteGeneralCustom(this)); // Remplacer l'ancien entête par EnteteGeneralCustom
            this.setMainContent(new MenuMachine(this));
        } catch (SQLException ex) {
            // Gérer l'exception
            //this.setMainContent(new BdDNonAccessible(this));
        }
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }
}
