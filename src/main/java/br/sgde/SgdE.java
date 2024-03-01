

package br.sgde;

import br.sgde.base.model.dao.EnderecoDao;
import br.sgde.base.model.entity.Endereco;
import br.sgde.estudantes.model.dao.StudentDao;
import br.sgde.estudantes.model.entity.Student;
import br.sgde.exceptions.ExceptionDAO;
import java.util.List;

/**
 *
 * @author elias
 */
public class SgdE {

    public static void lista(String value) throws ExceptionDAO{
        //Lista estudantes pelo valor
        StudentDao dao = new StudentDao(Student.class);
        
        List<Student> students = dao.read(value);
        for (Student s : students){
            System.out.println("Listagem de Estudantes "+s);
        }
        
    }
    public static void listaById(Long id) throws ExceptionDAO {
        StudentDao dao = new StudentDao(Student.class);
        Student student = dao.readById(id);
        
        EnderecoDao enderecoDao = new EnderecoDao(Endereco.class);
        Endereco endereco = enderecoDao.readByIdFk(student.getId());
        
        System.out.println("Estudante "+student);
        System.out.println("Endereco "+endereco);
 
    }
    
    public static void main(String[] args) throws ExceptionDAO {
//        lista("sou");
        listaById(2L);
    }
}
