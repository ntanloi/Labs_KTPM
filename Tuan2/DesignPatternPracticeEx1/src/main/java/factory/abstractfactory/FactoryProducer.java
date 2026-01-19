package factory.abstractfactory;

public class FactoryProducer {

    public static DatabaseAbstractFactory getFactory(String factoryType) {
        if (factoryType == null) {
            return null;
        }

        switch (factoryType.toUpperCase()) {
            case "MYSQL":
                return new MySQLFactory();
            case "POSTGRESQL":
                return new PostgreSQLFactory();
            case "MONGODB":
                return new MongoFactory();
            default:
                throw new IllegalArgumentException("Factory không hợp lệ: " + factoryType);
        }
    }
}