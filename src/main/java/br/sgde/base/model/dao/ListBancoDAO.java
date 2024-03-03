package br.sgde.base.model.dao;

import br.sgde.base.model.entity.ListBancos;
import br.sgde.exceptions.ExceptionDAO;
import java.util.List;

public class ListBancoDAO extends GenericDao<ListBancos>{

    public ListBancoDAO(Class<ListBancos> entityClass) {
        super(entityClass);
    }
    
    public List<ListBancos> list() throws ExceptionDAO{
        String listSQL = "SELECT cod, banco FROM lista_bancos ORDER BY banco";
        return read(listSQL, "");
    }
}
