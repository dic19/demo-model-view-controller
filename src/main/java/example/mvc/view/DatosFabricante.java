/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

/**
 * @author Delcio Amarillo
 */
public class DatosFabricante {
    
    private JTextField nombreTextField;
    private JSpinner fechaFundacionSpinner;
    private JPanel panel;
    
    public DatosFabricante(String nombre, Date fechaFundacion) {
        initComponents(nombre, fechaFundacion);
    }
    
    private void initComponents(String nombre, Date fechaFundacion) {
        nombreTextField = new JTextField(nombre, 30);
        
        Date fecha = fechaFundacion != null ? new Date(fechaFundacion.getTime())
                                            : new Date();
        
        fechaFundacionSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(fechaFundacionSpinner, "dd/MM/yyyy");

        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        
        fechaFundacionSpinner.setEditor(editor);
        fechaFundacionSpinner.setValue(fecha);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(nombreTextField, gbc);

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Fecha de fundación:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(fechaFundacionSpinner, gbc);
    }
    
    public Date getFechaFundacion() {
        Date fechaFundacion = (Date)fechaFundacionSpinner.getValue();
        return fechaFundacion != null ? new Date(fechaFundacion.getTime()) 
                                      : fechaFundacion;
    }
    
    public String getNombreFabricante() {
        return nombreTextField.getText();
    }
    
    public JComponent getUIComponent() {
        return panel;
    }
}
