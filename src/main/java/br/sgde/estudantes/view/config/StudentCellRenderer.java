package br.sgde.estudantes.view.config;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class StudentCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelect, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelect, hasFocus, row, column);

        if (row % 2 == 0) {
            setBackground(new Color(255, 255, 255));
            setForeground(Color.BLACK);
        } else {
            setBackground(new Color(220, 220, 220));
            setForeground(Color.BLACK);
        }

        if (isSelect) {
            setBackground(new Color(3, 167, 154));
            setForeground(Color.WHITE);
        }


        TableColumn hide = table.getColumnModel().getColumn(0); // idStudent
        hide.setMinWidth(0);
        hide.setMaxWidth(0);
        hide.setPreferredWidth(0);

        table.getColumnModel().getColumn(1).setMaxWidth(800); // Nome
        table.getColumnModel().getColumn(2).setMaxWidth(250); // CPF
        table.getColumnModel().getColumn(3).setMaxWidth(200); // ANIVERSÁRIO
        table.getColumnModel().getColumn(4).setMaxWidth(700); // MÃE
        


        return this;

    }
}
