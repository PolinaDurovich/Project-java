import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.Color;
import java.util.Map;

public class BarChart extends JFrame {

    public BarChart(Map<String, Double> health) {
        initUI(health);
    }

    public void initUI(Map<String, Double> healthByCountry) {
        CategoryDataset dataset = createDataset(healthByCountry);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setVerticalAxisTrace(true);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Форма");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset(Map<String, Double> health) {
        var dataset = new DefaultCategoryDataset();
        health.forEach((key, value) -> dataset.setValue(value, "Показатель здоровья", key));
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Показеть здоровья в странах",
                "Страны",
                "Показатель здоровья",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }
}