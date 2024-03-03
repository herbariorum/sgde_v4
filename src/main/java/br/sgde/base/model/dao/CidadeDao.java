
package br.sgde.base.model.dao;

import br.sgde.base.model.entity.Cidades;
import br.sgde.exceptions.ExceptionDAO;
import java.util.List;


public class CidadeDao  extends GenericDao<Cidades>{
    
    public CidadeDao(Class<Cidades> entityClass) {
        super(entityClass);
    }
    
    public List<Cidades> list(Long value) throws ExceptionDAO{
        String listSQL = "SELECT id, nome, estado_id FROM cidades WHERE estado_id = ? ORDER BY nome";
        return listById(listSQL, value);
    }

 
}
