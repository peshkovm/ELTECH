package eltech;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.List;

public class Plot extends ApplicationFrame {
    /**
     * Constructs a new application frame.
     *
     * @param title the frame title.
     */
    public Plot(String title, List<PlotDataPair> list) {
        this(title, list, 500, 300);
    }

    public Plot(String title, List<PlotDataPair> list, int width, int height) {
        super(title);
        final JFreeChart chart = createCombinedChart(list);
        final ChartPanel panel = new ChartPanel(chart,
                true,
                true,
                true,
                false,
                true);
        panel.setPreferredSize(new Dimension(width, height));
        setContentPane(panel);
    }

    private JFreeChart createCombinedChart(List<PlotDataPair> list) {
        final XYDataset data1 = createPlotDataset(list);
        final StandardXYItemRenderer renderer = new StandardXYItemRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
        final org.jfree.chart.axis.NumberAxis rangeAxis = new org.jfree.chart.axis.NumberAxis("time");
        final org.jfree.chart.axis.NumberAxis domainAxis = new org.jfree.chart.axis.NumberAxis("count of threads");
        final XYPlot subplot = new XYPlot(data1, domainAxis, rangeAxis, renderer);

        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot();

        plot.add(subplot);
        plot.setOrientation(PlotOrientation.VERTICAL);

        return new JFreeChart("Plot",
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                true);
    }

    private XYDataset createPlotDataset(List<PlotDataPair> list) {
        final XYSeriesCollection collection = new XYSeriesCollection();

        list.forEach(plotDataPair -> {
            XYSeries series = new XYSeries(plotDataPair.getLockName());
            plotDataPair.getPairs().forEach(pair -> series.add(pair.getCountOfThreads(), pair.getExecTime()));
            collection.addSeries(series);
        });

        return collection;
    }

    private static class LabeledXYDataset extends AbstractDataset {

    }
}