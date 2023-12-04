/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.MG.testVaadin;

import com.mycompany.projet_m3.Gestion;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.sql.SQLException;
import vue.EnteteGeneral;
import vue.MenuMachine;
import vue.MyHorizontalLayout;
import vue.MyVerticalLayout;

@Route("")
@PageTitle("Projet M3")

public class vuePrincipale extends MyVerticalLayout {
    

    public SessionInfo sessionInfo;

    private MyHorizontalLayout entete;
    private MyVerticalLayout mainContent;

    public void setEntete(Component c) {
        this.entete.removeAll();
        this.entete.add(c);
    }

    public void setMainContent(Component c) {
        this.mainContent.removeAll();
        this.mainContent.add(c);
    }

    public MyHorizontalLayout getEntete() {
        return entete;
    }

    public MyVerticalLayout getMainContent() {
        return mainContent;
    }
    
    

    public vuePrincipale() {
        this.sessionInfo = new SessionInfo();
        this.entete = new MyHorizontalLayout();
        this.entete.setWidthFull();
        this.add(this.entete);

        this.mainContent = new MyVerticalLayout();
        this.mainContent.setWidthFull();
        this.mainContent.setHeightFull();
        this.add(this.mainContent);
        
        
        try {
            this.sessionInfo.setConBdD(Gestion.connectSurServeurM3());
            this.setEntete(new EnteteGeneral(this));
            this.setMainContent(new MenuMachine(this));
        } catch (SQLException ex) {
            //this.setMainContent(new BdDNonAccessible(this));
        }

    }

    
    /**
     * @return the sessionInfo
     */
    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

}
