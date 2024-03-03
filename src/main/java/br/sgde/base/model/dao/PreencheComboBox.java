
package br.sgde.base.model.dao;


import br.sgde.base.model.entity.Estados;
import br.sgde.base.util.ComboBoxList;
import br.sgde.exceptions.ExceptionDAO;
import java.util.ArrayList;
import java.util.List;


public class PreencheComboBox extends GenericDao<Estados>{
    
    private ArrayList<ComboBoxList> list;
    
   
    public PreencheComboBox(Class<Estados> entityClass) {
        super(entityClass);
    }

    public ArrayList<ComboBoxList> getList() {
        return list;
    }

    public void setList(ArrayList<ComboBoxList> list) {
        this.list = list;
    }

    
    public List<Estados> lista() throws ExceptionDAO{
        String listSQL = "SELECT id, nome FROM estados";
        return readAll(listSQL);
    }    
    
    public void comboBoxEstado() throws ExceptionDAO {        
        this.setList(new ArrayList<>());
        List<Estados> estadosList = lista();
        for (Estados e : estadosList){
            this.getList().add(new ComboBoxList(e.getId(), e.getNome()));
        }
    }
    
    
}
