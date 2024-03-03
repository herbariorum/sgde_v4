package br.sgde.base.model.dao;


import br.sgde.base.model.entity.Estados;
import br.sgde.base.util.ComboBoxList;
import br.sgde.exceptions.ExceptionDAO;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO extends GenericDao<Estados> {

    private ArrayList<ComboBoxList> list;

    public ArrayList<ComboBoxList> getList() {
        return list;
    }

    public void setList(ArrayList<ComboBoxList> list) {
        this.list = list;
    }

    public EstadoDAO(Class<Estados> entityClass) {
        super(entityClass);
    }



    public List<Estados> list(String value) throws ExceptionDAO{
        String listSQL = "SELECT id, nome FROM estados WHERE nome = ?";
        return read(listSQL, value);
    }
 

    public void comboBoxEstado() throws ExceptionDAO {
        this.setList(new ArrayList<>());
        List<Estados> estadosList = list("");
        for (Estados e : estadosList){
            this.getList().add(new ComboBoxList(e.getId(), e.getNome()));
        }
    }


}
