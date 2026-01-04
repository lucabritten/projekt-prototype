package com.britten.service.laps;

import com.britten.domain.Lap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class LapChartService {

    public void generateSingleDriverChart(
            List<Lap> laps,
            String title,
            String outputFile
    ){
        XYSeries series = new XYSeries("Lap time");

        laps = laps.stream()
                .filter(lap -> lap.duration() > 0) //Filter zero laps
                .toList();

        laps.forEach(lap ->
                series.add(lap.lapNumber(), lap.duration())
        );

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Lap",
                "Lap time (s)",
                dataset
        );

        XYPlot plot = chart.getXYPlot();

        // Improve background and grid
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

        // Improve Y-axis (zoom into relevant range)
        double min = laps
                .stream()
                .mapToDouble(Lap::duration)
                .min()
                .orElse(0);

        double max = laps
                .stream()
                .mapToDouble(Lap::duration)
                .max()
                .orElse(0);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(min - 2, max + 2);
        rangeAxis.setLabel("Lap time (s)");

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(5));
        xAxis.setAutoRangeIncludesZero(false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(0, Color.RED.darker());
        plot.setRenderer(renderer);

        try {
            ChartUtils.saveChartAsPNG(new File(outputFile), chart, 500, 300);
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }

    public void generateCompareChart(
            Map<Integer, List<Lap>> lapsByDriver, //driverNumber mapped to laps
            String title,
            String outputFile
    ){
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int driverNumber : lapsByDriver.keySet()) {

            List<Lap> laps = lapsByDriver.get(driverNumber)
                    .stream()
                    .filter(lap -> lap.duration() > 0) // Outlaps raus
                    .toList();

            XYSeries series = new XYSeries("Driver " + driverNumber);

            laps.forEach(lap ->
                    series.add(lap.lapNumber(), lap.duration())
            );

            dataset.addSeries(series);
        }

        createCompareChart(dataset, title);
    }

    private void createCompareChart(
            XYDataset dataset,
            String title
    ) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Lap",
                "Lap time (s)",
                dataset
        );

        XYPlot plot = chart.getXYPlot();

        // Background
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

        // X-Axis: nur jede 5. Runde
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(5));
        xAxis.setAutoRangeIncludesZero(false);

        // Y-Axis: automatisch zoomen
        double globalMin = Double.MAX_VALUE;
        double globalMax = Double.MIN_VALUE;

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            XYSeries series = ((XYSeriesCollection) dataset).getSeries(i);
            for (int j = 0; j < series.getItemCount(); j++) {
                double value = series.getY(j).doubleValue();
                globalMin = Math.min(globalMin, value);
                globalMax = Math.max(globalMax, value);
            }
        }
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(globalMin - 1.5, globalMax + 1.5);
        yAxis.setLabel("Lap time (s)");
        yAxis.setTickUnit(new NumberTickUnit(0.5));

        // Renderer
        XYLineAndShapeRenderer renderer =
                new XYLineAndShapeRenderer(true, false);

        renderer.setAutoPopulateSeriesStroke(false);
        renderer.setDefaultStroke(new BasicStroke(2.0f));

        plot.setRenderer(renderer);

        try {
            ChartUtils.saveChartAsPNG(
                    new File("laps_compare.png"),
                    chart,
                    800,
                    500
            );
        } catch (Exception e) {
            System.out.println("Problem occurred creating comparison chart.");
        }
    }
}
