package br.sgde.base.model.dao;

import br.sgde.database.DB;
import br.sgde.exceptions.ExceptionDAO;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> {

    private final Connection connection;
    private final Class<T> entityClass;

    public GenericDao(Class<T> entityClass) {
        this.connection = DB.getConexao();
        this.entityClass = entityClass;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public int create(String insertSQL, Object... parametros) throws ExceptionDAO {
        try (var stmt = connection.prepareStatement(insertSQL);) {
            for (int i = 0; i < parametros.length; i++) {
                stmt.setObject(i + 1, parametros[i]);
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao salvar os registros\n" + e.getMessage());
        }
    }

    public T findById(String listSql, Long id) throws ExceptionDAO {
        T objeto = null;
        try (var stmt = connection.prepareStatement(listSql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    objeto = createObjectFromResultSet(rs);
                }
            }
        } catch (SQLException | ReflectiveOperationException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao ler os registros\n" + e.getMessage());
        }
        return objeto;
    }

    public List<T> read(String listSQL, String value) throws ExceptionDAO {
        List<T> resultados = new ArrayList<>();
        try (var stmt = connection.prepareStatement(listSQL)) {
            stmt.setString(1, "%" + value.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T objeto = createObjectFromResultSet(rs);
                    resultados.add(objeto);
                }
            }
        } catch (SQLException | ReflectiveOperationException e) {
            throw new ExceptionDAO("Ocorreu o seguinte erro ao ler os registros\n" + e.getMessage());
        }
        return resultados;
    }

    private T createObjectFromResultSet(ResultSet rs) throws ReflectiveOperationException, SQLException {
        T objeto = entityClass.getDeclaredConstructor().newInstance();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String nomeColuna = metaData.getColumnName(i);
            Field campo = objeto.getClass().getDeclaredField(nomeColuna);
            campo.setAccessible(true);

            Class<?> fieldType = campo.getType();
            Object value = rs.getObject(i);

            if (fieldType == LocalDate.class && value instanceof Date) {
                LocalDate localDateValue = ((Date) value).toLocalDate();
                campo.set(objeto, localDateValue);
            } else {
                campo.set(objeto, value);
            }
        }
        return objeto;
    }
}
