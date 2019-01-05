package com.company;

//created by Mahmoud Aliarab
//this class paint graphs with zooming ability (select a Foursquare for zooming):)
import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYLineChart_AWT extends ApplicationFrame {

    float[][] list;
    String GraphType;
    String[] algos;
    public XYLineChart_AWT( String applicationTitle, String chartTitle,float[][] list,String[] labels,String Ytitle,String Xtitle ) {

        super(applicationTitle);
        for (int i=0;i<labels.length;i++)
        this.list = list;
        this.algos = labels;
        if (Ytitle.equals("Optimal action %"))
            GraphType = "percent";
        else
            GraphType = "average";
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                Xtitle,
                Ytitle ,
                createDataset(labels) ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );

        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesPaint( 3 , Color.BLUE );
        renderer.setSeriesPaint( 4 , Color.magenta );
        renderer.setSeriesPaint( 5 , Color.ORANGE );

        renderer.setSeriesStroke( 0 , new BasicStroke( 5.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 5.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 5.0f ) );
        renderer.setSeriesStroke( 3 , new BasicStroke( 5.0f ) );
        renderer.setSeriesStroke( 4 , new BasicStroke( 5.0f ) );
        renderer.setSeriesStroke( 5 , new BasicStroke( 5.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( String[] labels) {
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        for (int j=0;j<labels.length;j++) {
            final XYSeries firefox = new XYSeries(labels[j]);

            for (int i = 0; i < list[j].length; i++)
                if (GraphType.equals("percent"))
                    firefox.add(i, list[j][i] * 100);
                else
                    firefox.add(i, list[j][i]);
            dataset.addSeries(firefox);
        }
        return dataset;
    }

}