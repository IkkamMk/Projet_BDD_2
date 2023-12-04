/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue.Tableau;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import java.util.List;
import objet.machine;

/**
 *
 * @author gille
 */
public class MachineGrid extends Grid<machine>{
    private List<machine> datas;
    
    public MachineGrid(List<machine> datas) {
        this.datas = datas;
        
        Column<machine> cID = this.addColumn(machine::getId)
                .setHeader("ID");
        cID.setWidth("3em");
        Column<machine> cRef = this.addColumn(machine::getRef)
                .setHeader("Ref");
        cRef.setSortable(true);
        
        Column<machine> cDes = this.addColumn(machine::getDes)
                .setHeader("Description");
        
       Column<machine> cPuissance = this.addColumn(machine::getPuissance)
                .setHeader("Puissance");
       
         this.setItems(datas);
        // pour affichage compact pour transparents
        /// this.setMaxHeight("12em");
    }
}
