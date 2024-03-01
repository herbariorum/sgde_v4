
package br.sgde.base.model.dao;


import br.sgde.base.model.entity.Endereco;
import br.sgde.exceptions.ExceptionDAO;


public class EnderecoDao  extends GenericDao<Endereco>{
    
    public EnderecoDao(Class<Endereco> entityClass) {
        super(entityClass);
    }
    
    public Endereco readByIdFk(Long id) throws ExceptionDAO{
        String listSQL = "select * from endereco where student_id = ?";
        return findById(listSQL, id);
    }
}
