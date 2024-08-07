package ru.antonov.jdbc.ex05_repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryField;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryFieldName;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryIdField;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryTable;
import ru.antonov.jdbc.ex05_repositories.config.DataSource;
import ru.antonov.jdbc.exceptions.ApplicationInitializationException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreparedStatementsMap <T>{
    public static final Logger logger = LogManager.getLogger(PreparedStatementsMap.class.getName());
    private DataSource dataSource;
    private Class<T> cls;
    private Map<RepoPreparedStatement, PreparedStatement> preparedStatements = new HashMap<>();
    private List<Field> cachedFields;
    private List<Field> allFields;
    private StringBuilder query;
    private PreparedStatement ps;
    public PreparedStatementsMap(DataSource dataSource, Class cls) {
        this.dataSource = dataSource;
        this.cls = cls;
        allFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                .collect(Collectors.toList());
        prepareCreate();
        prepareFindById();
        prepareFindAll();
        prepareUpdate();
        prepareDeleteById();
        prepareDeleteAll();
    }

    public Map<RepoPreparedStatement, PreparedStatement> getPreparedStatements() { return preparedStatements; }

    private void prepareDeleteAll() {
        query = new StringBuilder("TRUNCATE TABLE ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName);
        logger.debug("prepareDeleteAll-query " + query);
        try {
            ps = dataSource.getConnection().prepareStatement(query.toString());
            preparedStatements.put(RepoPreparedStatement.DELETEALL,ps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
    }

    private void prepareDeleteById() {
        query = new StringBuilder("DELETE FROM ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName).append(" WHERE id = ?");
        logger.debug("prepareDeleteById-query " + query);
        try {
            ps = dataSource.getConnection().prepareStatement(query.toString());
            preparedStatements.put(RepoPreparedStatement.DELETEBYID,ps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
    }

    private void prepareUpdate() {
        query = new StringBuilder("UPDATE ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName).append(" SET ");
        for (Field f : cachedFields) {
            if (f.isAnnotationPresent(RepositoryFieldName.class)) {
                query.append(f.getAnnotation(RepositoryFieldName.class).title());
            } else {
                query.append(f.getName());
            }
            query.append(" = ?, ");
        }
        query.setLength(query.length() - 2);
        query.append(" ");
        query.append("WHERE id = ?;");
        logger.debug("prepareUpdate-query " + query);
        try {
            ps = dataSource.getConnection().prepareStatement(query.toString());
            preparedStatements.put(RepoPreparedStatement.UPDATE,ps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
    }

    private void prepareFindAll() {
        query = new StringBuilder("select ");
        for (Field f : allFields) {
            if (f.isAnnotationPresent(RepositoryFieldName.class)) {
                query.append(f.getAnnotation(RepositoryFieldName.class).title());
            } else {
                query.append(f.getName());
            }
            query.append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(" from ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName);
        try {
            ps = dataSource.getConnection().prepareStatement(query.toString());
            preparedStatements.put(RepoPreparedStatement.FINDALL,ps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
    }

    private void prepareFindById() {
        query = new StringBuilder("select ");
        for (Field f : allFields) {
            if (f.isAnnotationPresent(RepositoryFieldName.class)) {
                query.append(f.getAnnotation(RepositoryFieldName.class).title());
            } else {
                query.append(f.getName());
            }
            query.append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(" from ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName).append(" where id = ?");
        logger.debug("prepareFindById-query " + query);
        try {
            ps = dataSource.getConnection().prepareStatement(query.toString());
            preparedStatements.put(RepoPreparedStatement.FINDBYID,ps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
    }

    private void prepareCreate() {
        query = new StringBuilder("insert into ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName).append(" (");

        cachedFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> !f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());
        for (Field f : cachedFields) {
            f.setAccessible(true);
        }
        for (Field f : cachedFields) {
            if (f.isAnnotationPresent(RepositoryFieldName.class)) {
                query.append(f.getAnnotation(RepositoryFieldName.class).title());
            } else {
                query.append(f.getName());
            }
            query.append(", ");
        }

        query.setLength(query.length() - 2);

        query.append(") values (");
        for (Field f : cachedFields) {
            query.append("?, ");
        }
        query.setLength(query.length() - 2);
        query.append(");");
        try {
            ps = dataSource.getConnection().prepareStatement(query.toString());
            preparedStatements.put(RepoPreparedStatement.CREATE,ps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
    }
}
