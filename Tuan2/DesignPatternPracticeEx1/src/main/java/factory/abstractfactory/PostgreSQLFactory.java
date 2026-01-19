package factory.abstractfactory;

import model.Database;
import model.PostgreSQLDatabase;

public class PostgreSQLFactory implements DatabaseAbstractFactory {
    @Override
    public Database createDatabase() {
        return new PostgreSQLDatabase();
    }
}