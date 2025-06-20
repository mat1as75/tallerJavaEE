package org.tallerJava.monitoringModule.infrastructure;

import io.micrometer.core.instrument.Clock;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxMeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;


@ApplicationScoped
public class MetricsRegister {
    public static final String METRIC_PAYMENT_OK = "payment_ok";
    public static final String METRIC_PAYMENT_FAIL = "payment_fail";
    public static final String METRIC_SALES_REPORT = "sales_report";
    public static final String METRIC_DEPOSIT = "deposit";
    public static final String METRIC_COMPLAINT = "complaint";

    private InfluxConfig config;
    private MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        // Metrics repository config
        config = new InfluxConfig() {
            @Override
            public String get(String key) { return null; }

            @Override
            public Duration step() { return Duration.ofSeconds(10); }

            @Override
            public String uri() { return "http://localhost:8086"; }

            @Override
            public String bucket() { return "TallerJava"; }

            @Override
            public String org() { return "TallerJavaEquipo2"; }

            @Override
            public String token() { return "secret-key"; }

            @Override
            public boolean enabled() { return true; }
        };

        meterRegistry = new InfluxMeterRegistry(config, Clock.SYSTEM);
    }

    public void counterIncrement(String counterName) {
        meterRegistry.counter(counterName).increment();
    }
}