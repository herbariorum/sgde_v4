
package br.sgde.estudantes.model.dao;

import br.sgde.base.model.dao.GenericDao;
import br.sgde.estudantes.model.entity.Student;
import br.sgde.exceptions.ExceptionDAO;
import java.util.List;


public class StudentDao extends GenericDao<Student>{

    public StudentDao(Class<Student> entityClass) {
        super(entityClass);
    }
    
    
    public int salvar(Student student) throws ExceptionDAO{
        String insertSQL = "INSERT INTO student(nome, cpf, dta_nasc, "
            + "sexo, status, nomeMae, nomePai, telefoneMae, telefonePai, nacionalidade,"
            + "ufNascimento, cidadeNascimento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        return create(insertSQL, student.getNome());
    }
    
    public List<Student> read(String value) throws ExceptionDAO{
        String listSQL = "select * from student where LOWER(nome) LIKE ?";
        return read(listSQL, value);
    }
    
    public Student readById(Long id) throws ExceptionDAO{
        String listSQL = "select * from student where id = ?";
        return findById(listSQL, id);
    }
}