/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author PC
 */

public class RenderTabla extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        if (o instanceof JButton) {
            JButton boton = (JButton) o;
            // Configura el tamaño de fuente y el tamaño del botón
            boton.setFont(new Font("Arial", Font.PLAIN, 10)); // Puedes ajustar el tamaño de la fuente según tus preferencias

            // Ajusta el tamaño del botón
            boton.setPreferredSize(new Dimension(225, 45)); // Puedes ajustar el tamaño del botón según tus preferencias

            return boton;
        }
        return super.getTableCellRendererComponent(table, o, isSelected, hasFocus, row, column);
    }
}
