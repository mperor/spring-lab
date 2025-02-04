package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.concurrent.atomic.AtomicInteger;

public class SqlStatementInterceptor implements StatementInspector {

    private static final AtomicInteger QUERY_COUNTER = new AtomicInteger(0);

    @Override
    public String inspect(String sql) {
        QUERY_COUNTER.getAndIncrement();
        return sql;
    }

    public static int getQueryCount() {
        return QUERY_COUNTER.get();
    }

    public static void resetQueryCount() {
        QUERY_COUNTER.set(0);
    }
}