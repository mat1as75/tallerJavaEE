package org.tallerJava.monitoringModule.infrastructure;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxMeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MetricsRegister {
    public static final String METRIC_PAYMENT_OK = "payment_ok";
    public static final String METRIC_PAYMENT_FAIL = "payment_fail";
    public static final String METRIC_SALES_REPORT = "sales_report";
    public static final String METRIC_DEPOSIT = "deposit";
    public static final String METRIC_COMPLAINT = "complaint";

    private InfluxConfig config;
    private MeterRegistry meterRegistry;
    private Map<String, Counter> counters = new HashMap<>();

    @PostConstruct
    public void init() {
        // Metrics repository config
        config = new InfluxConfig() {
            @Override
            public String get(String key) { return null; }

            @Override
            public Duration step() { return Duration.ofSeconds(10); }

            @Override
            public String db() { return "TallerJava"; }

            @Override
            public String uri() { return "http://localhost:8086"; }

            @Override
            public String userName() { return "root"; }

            @Override
            public String password() { return "root"; }

            @Override
            public boolean enabled() { return true; }
        };

        meterRegistry = new InfluxMeterRegistry(config, Clock.SYSTEM);

        counters.put(METRIC_PAYMENT_OK, meterRegistry.counter(METRIC_PAYMENT_OK));
        counters.put(METRIC_PAYMENT_FAIL, meterRegistry.counter(METRIC_PAYMENT_FAIL));
        counters.put(METRIC_SALES_REPORT, meterRegistry.counter(METRIC_SALES_REPORT));
        counters.put(METRIC_DEPOSIT, meterRegistry.counter(METRIC_DEPOSIT));
        counters.put(METRIC_COMPLAINT, meterRegistry.counter(METRIC_COMPLAINT));

        System.out.println("MetricsRegister initialized with InfluxDB in: " + config.uri());
    }

    @PreDestroy
    public void close() {
        if (meterRegistry != null) {
            meterRegistry.close();
            System.out.println("MetricsRegister closed");
        }
    }

    public void counterIncrement(String counterName) {
        Counter counter = counters.computeIfAbsent(counterName, k -> meterRegistry.counter(counterName));
        counter.increment();
        System.out.println("Metric Incremented: " + counterName);
    }
}