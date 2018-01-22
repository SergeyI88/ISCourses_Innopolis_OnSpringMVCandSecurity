package db.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Component;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Component
public class ConnectionManagerPostgres implements ConnectionManager {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static ConnectionManager connectionManager;
    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerPostgres();
            connectionManager.setPropertiesPool();
        }
        return ConnectionManagerPostgres.connectionManager;
    }
    private ConnectionManagerPostgres() {
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = cpds.getConnection();
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public void close() {
        cpds.close();
    }
    @Override
    public void setPropertiesPool () {
        try {
            cpds.setDriverClass("org.postgresql.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl("jdbc:postgresql://localhost/developer");
        cpds.setUser("postgres");
        cpds.setPassword("root");
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(100);
    }
}
