/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue.Tableau;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import java.util.List;
import objet.operation;

/**
 *
 * @author gille
 */
public class OperationGrid extends Grid<operation>{
    private List<operation> datas;
    
    public OperationGrid(List<operation> datas) {
        this.datas = datas;
        
        Column<operation> cID = this.addColumn(operation::getId)
                .setHeader("ID");
        cID.setWidth("3em");
        cID.setSortable(true);
        
        Column<operation> cId_type = this.addColumn(operation::getId_type)
                .setHeader("Id Type");
        cId_type.setSortable(true);
        
        Column<operation> cId_produit = this.addColumn(operation::getId_produit)
                .setHeader("Id Produit");
        cId_produit.setSortable(true);
       
         this.setItems(datas);
        // pour affichage compact pour transparents
        /// this.setMaxHeight("12em");
    }
}
