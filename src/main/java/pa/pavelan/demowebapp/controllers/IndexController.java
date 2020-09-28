package pa.pavelan.demowebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pa.pavelan.demowebapp.model.SearchForm;
import pa.pavelan.webmetrics.handler.MetricsHelper;
import pa.pavelan.webmetrics.model.MetricsModel;
import pa.pavelan.webmetrics.model.MetricsSummaryModel;

import java.util.*;

@Controller
public class IndexController {
    final private String DUMMY_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
            "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    @GetMapping("/")
    public String index(Model model) {
        // return view name
        return "index";
    }

    @GetMapping("/allResults")
    public String allResults(Model model) {
        Collection<MetricsModel> results = MetricsHelper.getAllMetrics();

        model.addAttribute("results", results);

        return "allResults";
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("hasData", false);
        model.addAttribute("showNoSearchResult", false);
        model.addAttribute("searchForm", new SearchForm());

        return "search";
    }

    @PostMapping("/search")
    public String submissionResult(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        if (searchForm.getId() == null) {
            model.addAttribute("hasData", false);
            model.addAttribute("showNoSearchResult", true);
        } else {
            MetricsModel metricsModel = MetricsHelper.getMetricById(UUID.fromString(searchForm.getId()));
            if (metricsModel == null) {
                model.addAttribute("hasData", false);
                model.addAttribute("showNoSearchResult", true);
            } else {
                model.addAttribute("hasData", true);
                model.addAttribute("showNoSearchResult", false);
                model.addAttribute("result", metricsModel);
            }
        }
        return "search";
    }

    @GetMapping("/metrics")
    public String metrics(Model model) {
        MetricsSummaryModel summaryModel = MetricsHelper.getMetricSummary();
        model.addAttribute("minimumTime", summaryModel.getMinProcessingTime());
        model.addAttribute("maximumTime", summaryModel.getMaxProcessingTime());
        model.addAttribute("averageTime", String.format("%.2f",summaryModel.getAverageProcessingTime()));

        model.addAttribute("minimumSize", summaryModel.getMinResponseSize());
        model.addAttribute("maximumSize", summaryModel.getMaxResponseSize());
        model.addAttribute("averageSize", String.format("%.2f",summaryModel.getAverageResponseSize()));

        // return view name
        return "metrics";
    }

    @GetMapping("/random")
    public String randomText(Model model) throws InterruptedException {
        Random random = new Random();
        int count = random.nextInt(100);
        List<String> results = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            results.add(DUMMY_TEXT);
        }
        model.addAttribute("results", results);
        Thread.sleep(100 * count);
        return "randomPage";
    }
}
