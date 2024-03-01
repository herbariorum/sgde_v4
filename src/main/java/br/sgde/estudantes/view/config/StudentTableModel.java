package br.sgde.estudantes.view.config;


import br.sgde.base.util.Util;
import br.sgde.base.util.ViewAbstractTableModel;
import br.sgde.estudantes.model.entity.Students;
import java.util.List;

public class StudentTableModel extends ViewAbstractTableModel<Students> {

    public StudentTableModel(List<Students> rows) {
        super(rows);
        columns = new String[]{
                "ID",
                "NOME",
                "CPF",
                "NASCIMENTO",
                "MÃE"
                
        };

    }

    @Override
    public Object getValueAt(int row, int col) {
        Students std = this.rows.get(row);
        switch (col) {
            case 0:
                return std.getIdstudent();
            case 1:
                return std.getNome();
            case 2:
                return Util.formataCpf(std.getCpf());         
            case 3:
                return new Util().formatDate(std.getDta_nasc());
            case 4:
                return std.getNomemae();
            default:
                return null;
        }
    }
}
