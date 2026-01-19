package factory.abstractfactory;

import model.Database;
import model.MongoDatabase;

public class MongoFactory implements DatabaseAbstractFactory {
    @Override
    public Database createDatabase() {
        return new MongoDatabase();
    }
}