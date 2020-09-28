package pa.pavelan.demowebapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import pa.pavelan.webmetrics.handler.MetricsHelper;
import pa.pavelan.webmetrics.model.MetricsModel;
import pa.pavelan.webmetrics.model.MetricsSummaryModel;

import java.util.Collection;
import java.util.Comparator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPages() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Demo web app to test metrics");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/metrics",
                String.class)).contains("Metrics summary");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/search",
                String.class)).contains("Search results");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/allResults",
                String.class)).contains("Response size (bytes)");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/random",
                String.class)).contains("Lorem ipsum dolor sit amet");

        Collection<MetricsModel> results = MetricsHelper.getAllMetrics();
        assertEquals(5, results.size());

        MetricsSummaryModel summary = MetricsHelper.getMetricSummary();

        double processTimeAverage = results.stream()
                .map(r -> (int)r.getProcessingTime())
                .mapToInt(t -> t)
                .average()
                .orElse(0.0);

        double responseSizeAverage = results.stream()
                .map(r -> (int)r.getResponseSize())
                .mapToInt(t -> t)
                .average()
                .orElse(0.0);

        long minProcessTime = results.stream()
                .map(MetricsModel::getProcessingTime)
                .min(Comparator.comparing(i -> i))
                .orElse(0L);

        long maxProcessTime = results.stream()
                .map(MetricsModel::getProcessingTime)
                .max(Comparator.comparing(i -> i))
                .orElse(0L);

        long minResponseSize = results.stream()
                .map(MetricsModel::getResponseSize)
                .min(Comparator.comparing(i -> i))
                .orElse(0L);

        long maxResponseSize = results.stream()
                .map(MetricsModel::getResponseSize)
                .max(Comparator.comparing(i -> i))
                .orElse(0L);

        assertEquals(minProcessTime, summary.getMinProcessingTime());
        assertEquals(maxProcessTime, summary.getMaxProcessingTime());
        assertEquals(minResponseSize, summary.getMinResponseSize());
        assertEquals(maxResponseSize, summary.getMaxResponseSize());

        assertTrue(Math.abs(processTimeAverage - summary.getAverageProcessingTime()) < 1e-6);
        assertTrue(Math.abs(responseSizeAverage - summary.getAverageResponseSize()) < 1e-6);

    }
}
