package am.gd.app.config;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.Getter;

import java.net.InetSocketAddress;

@Getter
public class CassandraDatabaseConnector {

    private CqlSession session;

    public void connect() {
        var initialSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .withAuthCredentials("cassandra", "cassandra")
                .build();

        initialSession.execute(
                "CREATE KEYSPACE IF NOT EXISTS task_management " +
                        "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}"
        );

        initialSession.close();

        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .withAuthCredentials("cassandra", "cassandra")
                .withKeyspace("task_management")
                .build();
    }

    public void close() {
        if (session != null) {
            session.close();
        }
    }
}