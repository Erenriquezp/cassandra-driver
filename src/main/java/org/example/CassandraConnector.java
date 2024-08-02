package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

import java.net.InetSocketAddress;
import java.time.Duration;

public class CassandraConnector {
    private CqlSession session;
    DriverConfigLoader loader = DriverConfigLoader.programmaticBuilder()
            .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(10))
            .build();
    public void connect(String node, int port, String datacenter, String keyspace) {
        session = CqlSession.builder()
                .withConfigLoader(loader)
                .addContactPoint(new InetSocketAddress(node, port))
                .withLocalDatacenter(datacenter)
                .withKeyspace(keyspace)
                .build();
    }
    public CqlSession getSession() {
        return session;
    }
    public void close() {
        session.close();
    }
}
