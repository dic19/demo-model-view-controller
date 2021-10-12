/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.controller;

import example.mvc.model.IFabricantesModel;
import example.mvc.view.IFabricantesView;

/**
 * @author Delcio Amarillo
 */
public interface IFabricantesController {
    
    public void agregarFabricante(IFabricantesView view);
    
    public void modificarFabricante(IFabricantesView view);
    
    public void eliminarFabricantes(IFabricantesView view);
    
    public void getListaFabricantes(IFabricantesView view);
    
    public void setFabricantesModel(IFabricantesModel model);
}
