package br.sgde.estudantes.model.dao;

import br.sgde.base.model.dao.IDAO;
import br.sgde.base.model.entity.Endereco;
import br.sgde.database.DB;
import br.sgde.estudantes.model.entity.Students;
import br.sgde.exceptions.ExceptionDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class StudentDao extends GenericDao<Students> {
//    public StudentDao(Class<Students> entityClass) {
//        super(entityClass);
//    }
//
//    public void salvar(Students student) throws ExceptionDAO {
//        String insertSQL = "INSERT INTO students(nome, cpf, dta_nasc, "
//                + "sexo, status, nomeMae, nomePai, telefoneMae, telefonePai, nacionalidade,"
//                + "ufNascimento, cidadeNascimento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//        executeUpdate(insertSQL, student);
//    }
//    
// 
//    public List<Students> read(String value) throws ExceptionDAO {
//        String listSQL = "select student.* FROM students student WHERE LOWER(student.nome) LIKE ?";
//        return read(listSQL, value);
//    }
//
//    public Students readById(Long id) throws ExceptionDAO {
//        String listSQL = "select * from students where idstudent = ?";
//        return findById(listSQL, id);
//    }
public class StudentDao implements IDAO<Students> {

    public StudentDao() {
        this.connection = DB.getConexao();
    }

    @Override
    public int save(Students entity) throws ExceptionDAO {
        var insertStudent = 0;
        var insertEndereco = 0;
        try (var stmt = this.connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);) {
            List<Students> lista = listByCpf(entity.getCpf());
            if (lista.isEmpty()) {
                stmt.setString(1, entity.getNome());
                stmt.setString(2, entity.getCpf());
                stmt.setDate(3, Date.valueOf(entity.getDta_nasc()));
                stmt.setString(4, entity.getSexo());
                stmt.setBoolean(5, entity.isStatus());
                stmt.setString(6, entity.getNomemae());
                stmt.setString(7, entity.getNomepai());
                stmt.setString(8, entity.getTelefonemae());
                stmt.setString(9, entity.getTelefonepai());
                stmt.setString(10, entity.getNacionalidade());
                stmt.setString(11, entity.getUfnascimento());
                stmt.setString(12, entity.getCidadenascimento());
                insertStudent = stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long idStudent = generatedKeys.getLong(1);
                        try (var stmtend = connection.prepareStatement(sqlInsertEndereco, Statement.RETURN_GENERATED_KEYS);) {
                            Endereco endereco = entity.getEndereco();
                            stmtend.setString(1, endereco.getLogradouro());
                            stmtend.setString(2, endereco.getNumero());
                            stmtend.setString(3, endereco.getComplemento());
                            stmtend.setString(4, endereco.getBairro());
                            stmtend.setString(5, endereco.getEstado());
                            stmtend.setString(6, endereco.getCidade());
                            stmtend.setString(7, endereco.getCep());
                            stmtend.setLong(8, idStudent);
                            insertEndereco = stmtend.executeUpdate();
                        }
                    }
                    if ((insertStudent > 0) && (insertEndereco > 0)) {
                        return 1;
                    }
                }
            } else {
                throw new ExceptionDAO("Este cpf já está cadastrado na base de dados.");
            }

        } catch (SQLException ex) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao salvar estudantes\n" + ex.getMessage());
        }
        return -1;
    }

    @Override
    public int update(Students entity) throws ExceptionDAO {
        int updateStudent;
        int updateEndereco;
        Long idStudent = entity.getIdStudent();
        try (var stmt = this.connection.prepareStatement(sqlUpdate);) {
            stmt.setString(1, entity.getNome());
            stmt.setString(2, entity.getCpf());
            stmt.setDate(3, Date.valueOf(entity.getDta_nasc()));
            stmt.setString(4, entity.getSexo());
            stmt.setBoolean(5, entity.isStatus());
            stmt.setString(6, entity.getNomemae());
            stmt.setString(7, entity.getNomepai());
            stmt.setString(8, entity.getTelefonemae());
            stmt.setString(9, entity.getTelefonepai());
            stmt.setString(10, entity.getNacionalidade());
            stmt.setString(11, entity.getUfnascimento());
            stmt.setString(12, entity.getCidadenascimento());
            stmt.setLong(13, idStudent);
            updateStudent = stmt.executeUpdate();

            try (var stmtend = connection.prepareStatement(sqlUpdateEndereco);) {
                Endereco endereco = entity.getEndereco();
                stmtend.setString(1, endereco.getLogradouro());
                stmtend.setString(2, endereco.getNumero());
                stmtend.setString(3, endereco.getComplemento());
                stmtend.setString(4, endereco.getBairro());
                stmtend.setString(5, endereco.getEstado());
                stmtend.setString(6, endereco.getCidade());
                stmtend.setString(7, endereco.getCep());
                stmtend.setLong(8, idStudent);
                stmtend.setLong(9, endereco.getIdaddress());
                updateEndereco = stmtend.executeUpdate();
            }
            if ((updateStudent > 0) && (updateEndereco > 0)) {

                return updateStudent;
            }

        } catch (SQLException ex) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao atualizar o estudante\n" + ex.getSQLState() + ex.getMessage());
        }

        return -1;
    }

    @Override
    public int delete(Students entity) throws ExceptionDAO {
        try (var stmt = this.connection.prepareStatement(sqlDelete);) {
            stmt.setLong(1, entity.getIdStudent());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao tentar excluir o estudante\n" + ex.getMessage());
        }
    }

    @Override
    public List<Students> list() throws ExceptionDAO {
        List<Students> students = new ArrayList<>();
        Map<Integer, Endereco> map = new HashMap<>();
        try (var stmt = this.connection.createStatement();) {
            try (var rs = stmt.executeQuery(listAllSQL)) {
                while (rs.next()) {
                    Endereco end = map.get(rs.getInt("student_id"));
                    if (end == null) {
                        end = instancializaEndereco(rs);
                        map.put(rs.getInt("student_id"), end);
                    }
                    Students student = instancializaStudent(rs, end);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao listar os registros\n" + e.getMessage());
        }
        return students;
    }

    @Override
    public Students findById(Long id) throws ExceptionDAO {
        Students student = null;
        Endereco endereco;
        try (var stmt = this.connection.prepareStatement(listByIdSQL)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    endereco = instancializaEndereco(rs);
                    student = instancializaStudent(rs, endereco);
                }
            }
        } catch (SQLException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao ler os registros pelo ID\n" + e.getMessage());
        }
        return student;

    }

    @Override
    public List<Students> listByValue(String value) throws ExceptionDAO {
        try (var stmt = this.connection.prepareStatement(listByNameSQL);) {
            stmt.setString(1, "%" + value.toLowerCase() + "%");
            try (var rs = stmt.executeQuery()) {
                List<Students> students = new ArrayList<>();
                Map<Integer, Endereco> map = new HashMap<>();
                while (rs.next()) {
                    Endereco end = map.get(rs.getInt("student_id"));
                    if (end == null) {
                        end = instancializaEndereco(rs);
                        map.put(rs.getInt("student_id"), end);
                    }
                    Students student = instancializaStudent(rs, end);
                    students.add(student);
                }
                return students;
            }
        } catch (SQLException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao buscar pelo nome.\n" + e.getMessage());
        }
    }

    public List<Students> listByCpf(final String cpf) throws SQLException, ExceptionDAO {
        List<Students> students = new ArrayList<>();
        Map<Integer, Endereco> map = new HashMap<>();
        try (var stmt = this.connection.prepareStatement(sqlFindToCpf);) {
            stmt.setString(1, cpf);
            try (var rs = stmt.executeQuery()) {
//                students = convertToList(rs); 
                while (rs.next()) {
                    Endereco end = map.get(rs.getInt("student_id"));
                    if (end == null) {
                        end = instancializaEndereco(rs);
                        map.put(rs.getInt("student_id"), end);
                    }
                    Students student = instancializaStudent(rs, end);

                    students.add(student);
                }
            }
        } catch (SQLException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao buscar pelo nome.\n" + e.getMessage());
        }
        return students;
    }

    private Students instancializaStudent(ResultSet rs, Endereco endereco) throws SQLException {
        Students student = new Students();
        student.setIdStudent(rs.getLong("idstudent"));
        student.setNome(rs.getString("nome"));
        student.setCpf(rs.getString("cpf"));
        student.setDta_nasc(rs.getDate("dta_nasc").toLocalDate());
        student.setSexo(rs.getString("sexo"));
        student.setStatus(rs.getBoolean("status"));
        student.setNomemae(rs.getString("nomemae"));
        student.setTelefonemae(rs.getString("telefonemae"));
        student.setNomepai(rs.getString("nomePai"));
        student.setTelefonepai(rs.getString("telefonepai"));
        student.setNacionalidade(rs.getString("nacionalidade"));
        student.setUfnascimento(rs.getString("ufnascimento"));
        student.setCidadenascimento(rs.getString("cidadenascimento"));
        student.setEndereco(endereco);
        return student;
    }

    private Endereco instancializaEndereco(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setIdaddress(rs.getLong("idaddress"));
        endereco.setLogradouro(rs.getString("logradouro"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setCep(rs.getString("cep"));
        endereco.setStudent_id(rs.getInt("student_id"));
        return endereco;
    }

    private final String listAllSQL = "SELECT * FROM students";
    private final String listByNameSQL = "select est.*, address.* FROM students est INNER JOIN endereco address ON est.idstudent = address.student_id WHERE LOWER(est.nome) Like ? ORDER BY est.nome";
    private final String sqlInsert = "INSERT INTO students(nome, cpf, dta_nasc, "
            + "sexo, status, nomemae, nomepai, telefonemae, telefonepai, nacionalidade,"
            + "ufnascimento, cidadenascimento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String sqlUpdate = "UPDATE students SET nome = ?, cpf = ?, dta_nasc = ?, "
            + "sexo = ?, status = ?, nomemae = ?, nomepai = ?, telefonemae = ?, telefonepai = ? ,"
            + "nacionalidade = ?, ufnascimento = ?, cidadenascimento = ? WHERE idstudent = ?";
    private final String sqlInsertEndereco = "INSERT INTO endereco(logradouro, numero, complemento,"
            + "bairro, estado, cidade, cep, student_id) VALUES (?,?,?,?,?,?,?,?)";
    private final String listByIdSQL = "select est.*, address.* FROM students est INNER JOIN endereco address ON est.idstudent = address.student_id WHERE est.idstudent = ?;";
    private final String sqlFindToCpf = "select est.*, address.* FROM students est INNER JOIN endereco address ON est.idstudent = address.student_id WHERE est.cpf = ?";
    private final String sqlUpdateEndereco = "UPDATE endereco SET logradouro = ?, "
            + "numero = ?, complemento = ?,"
            + "bairro = ?, estado = ?, cidade = ?, cep = ?, student_id = ? WHERE idaddress = ?";
    private final String sqlDelete = "DELETE FROM students WHERE idstudent = ?";
    private final Connection connection;
}
