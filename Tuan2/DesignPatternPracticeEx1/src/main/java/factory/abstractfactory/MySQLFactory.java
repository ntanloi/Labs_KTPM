package factory.abstractfactory;

import model.Database;
import model.MySQLDatabase;

public class MySQLFactory implements DatabaseAbstractFactory {
    @Override
    public Database createDatabase() {
        return new MySQLDatabase();
    }
}