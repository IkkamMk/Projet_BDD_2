/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue.Tableau;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;
import objet.operation;
import objet.typeOperation;

/**
 *
 * @author gille
 */
public class TypeOperationGrid extends Grid<typeOperation>{
    private List<typeOperation> datas;
    
    public TypeOperationGrid(List<typeOperation> datas) {
        this.datas = datas;
        
        Column<typeOperation> cID = this.addColumn(typeOperation::getId)
                .setHeader("ID");
        cID.setWidth("3em");
        cID.setSortable(true);
        
        Column<typeOperation> cId_type = this.addColumn(typeOperation::getDes)
                .setHeader("Description");
        
         this.setItems(datas);
        // pour affichage compact pour transparents
        /// this.setMaxHeight("12em");
    }
}
