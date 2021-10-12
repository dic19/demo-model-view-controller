/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.model;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Delcio Amarillo
 */
public interface IFabricantesModel {
    
    public static final int EXITO = 0;
    
    public static final int FALLO = -1;
    
    public Fabricante getFabricantePorId(BigInteger id);
    
    public List<Fabricante> getFabricantes();
    
    public int insertar(Fabricante fabricante);
    
    public int modificar(Fabricante fabricante);
    
    public int eliminar(Fabricante fabricante);
    
    public int eliminar(List<Fabricante> fabricantes);    
}
