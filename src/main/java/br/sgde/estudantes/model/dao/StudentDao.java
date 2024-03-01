
package br.sgde.estudantes.model.dao;

import br.sgde.base.model.dao.GenericDao;
import br.sgde.estudantes.model.entity.Students;
import br.sgde.exceptions.ExceptionDAO;
import java.util.List;


public class StudentDao extends GenericDao<Students>{

    public StudentDao(Class<Students> entityClass) {
        super(entityClass);
    }
    
    
    public int salvar(Students student) throws ExceptionDAO{
        String insertSQL = "INSERT INTO students(nome, cpf, dta_nasc, "
            + "sexo, status, nomeMae, nomePai, telefoneMae, telefonePai, nacionalidade,"
            + "ufNascimento, cidadeNascimento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        return create(insertSQL, student.getNome());    

    }
    
    public List<Students> read(String value) throws ExceptionDAO{
        String listSQL = "select student.* FROM students student WHERE LOWER(student.nome) LIKE ?";
        return read(listSQL, value);
    }
    
    public Students readById(Long id) throws ExceptionDAO{
        String listSQL = "select * from students where idstudent = ?";
        return findById(listSQL, id);
    }
}