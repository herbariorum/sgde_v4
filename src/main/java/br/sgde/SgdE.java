

package br.sgde;

import br.sgde.exceptions.ExceptionDAO;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.sql.SQLException;

/**
 *
 * @author elias
 */
public class SgdE {
//
//    public static void lista(String value) throws ExceptionDAO{
//        //Lista estudantes pelo valor
//        StudentDao dao = new StudentDao(Students.class);
//        
//        List<Students> students = dao.read(value);
//        for (Students s : students){
//            System.out.println("Listagem de Estudantes "+s);
//        }
//        
//    }
//    public static void listaById(Long id) throws ExceptionDAO {
//        StudentDao dao = new StudentDao(Students.class);
//        Students student = dao.readById(id);
//        
//        EnderecoDao enderecoDao = new EnderecoDao(Endereco.class);
//        Endereco endereco = enderecoDao.readByIdFk(student.getIdStudent());
//        
//        System.out.println("Estudante "+student.toString());
//        System.out.println("Endereco "+endereco);
// 
//    }
    
    public static void main(String[] args) throws ExceptionDAO, SQLException {
        FlatIntelliJLaf.setup();
        
        new Menu().start();
    }
}
