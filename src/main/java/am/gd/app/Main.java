package am.gd.app;

import am.gd.app.config.CassandraDatabaseConnector;

public class Main {
    public static void main(String[] args) {
        var connector = new CassandraDatabaseConnector();
        connector.connect();
    }
}