/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue.Tableau;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;
import objet.PrecedenceOp;

/**
 *
 * @author gille
 */
public class PrecedenceOpGrid extends Grid<PrecedenceOp>{
    private List<PrecedenceOp> datas;
    public PrecedenceOpGrid(List<PrecedenceOp> datas) {
        this.datas = datas;
        
        Column<PrecedenceOp> cID = this.addColumn(PrecedenceOp::getOpavant)
                .setHeader("Opavant");
        cID.setWidth("3em");
        Column<PrecedenceOp> cRef = this.addColumn(PrecedenceOp::getOpapres)
                .setHeader("Opapres");
        cRef.setSortable(true);
        
         this.setItems(datas);
        // pour affichage compact pour transparents
        /// this.setMaxHeight("12em");
    }
}
