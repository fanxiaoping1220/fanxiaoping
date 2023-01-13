package com.xingkong.spingboot.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * * @className: CreateChartTest
 * * @description: 生成折线图
 * * @author: fan xiaoping
 * * @date: 2023/1/10 0010 14:30
 **/
public class CreateChartTest {

    public static void main(String[] args) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.setValue(10, "ibm", "2018-05-21");
        ds.setValue(20, "ibm", "2018-05-22");
        ds.setValue(32, "ibm", "2018-05-23");
        ds.setValue(25, "ibm", "2018-05-24");
        ds.setValue(0, "ibm", "2018-05-25");
        ds.setValue(4, "ibm", "2018-05-26");
        ds.setValue(32, "ibm", "2018-05-27");
        ds.setValue(0, "ibm", "2018-05-28");
        ds.setValue(358, "ibm", "2018-05-29");
        ds.setValue(4, "ibm", "2018-05-30");
        String filePath = "d:/lgg.jpg";
        createPieChart(ds,filePath,0,360);
    }

    public static void createPieChart(DefaultCategoryDataset ds, String filePath,double min,double max) {
        try {
            // 创建柱状图.标题,X坐标,Y坐标,数据集合,orientation,是否显示legend,是否显示tooltip,是否使用url链接
            JFreeChart chart = ChartFactory.createLineChart("", "", "", ds, PlotOrientation.VERTICAL,false, true, true);
            Color gray = new Color(153,153,153);
            chart.setBackgroundPaint(Color.black);
            chart.setBorderVisible(false);
//            Font font = new Font("宋体", Font.BOLD, 12);
//            chart.getTitle().setFont(font);
//            chart.setBackgroundPaint(Color.WHITE);
            // 配置字体（解决中文乱码的通用方法）
//            Font xfont = new Font("仿宋", Font.BOLD, 12); // X轴
//            Font yfont = new Font("宋体", Font.BOLD, 12); // Y轴
//            Font titleFont = new Font("宋体", Font.BOLD, 12); // 图片标题
            CategoryPlot categoryPlot = chart.getCategoryPlot();
//            categoryPlot.getDomainAxis().setLabelFont(xfont);
//            categoryPlot.getDomainAxis().setLabelFont(xfont);
//            categoryPlot.getRangeAxis().setLabelFont(yfont);
//            chart.getTitle().setFont(titleFont);
            categoryPlot.setBackgroundPaint(Color.black);
            // x轴 // 分类轴网格是否可见
//            categoryPlot.setDomainGridlinesVisible(false);
            // y轴 //数据轴网格是否可见
//            categoryPlot.setRangeGridlinesVisible(false);
            // 设置网格竖线颜色
//            categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            // 设置网格横线颜色
//            categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
            // 没有数据时显示的文字说明
            categoryPlot.setNoDataMessage("没有数据显示");
            // 设置曲线图与xy轴的距离
            categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
            // 设置面板字体
//            Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
            // 取得Y轴
            NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
//            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//            rangeAxis.setAutoRangeIncludesZero(true);
            rangeAxis.setUpperMargin(0.20);
            rangeAxis.setLabelAngle(Math.PI / 2.0);
            rangeAxis.setLowerBound(min);
            rangeAxis.setRange(min,max);
            rangeAxis.setAxisLinePaint(Color.white);
            rangeAxis.setAxisLineVisible(true);
            rangeAxis.setTickLabelsVisible(false);
            rangeAxis.setTickMarksVisible(false);
            // 取得X轴
            CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
            categoryAxis.setAxisLinePaint(Color.white);
            categoryAxis.setAxisLineVisible(true);
            categoryAxis.setTickLabelsVisible(false);
            categoryAxis.setTickMarksVisible(false);
            // 设置X轴坐标上的文字
//            categoryAxis.setTickLabelFont(labelFont);
            // 设置X轴的标题文字
//            categoryAxis.setLabelFont(labelFont);
            // 横轴上的 Lable 45度倾斜
//            categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            // 设置距离图片左端距离
//            categoryAxis.setLowerMargin(0.0);
            // 设置距离图片右端距离
//            categoryAxis.setUpperMargin(0.0);
            // 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
            LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
            // 是否显示折点
//            lineandshaperenderer.setBaseShapesVisible(true);
            // 是否显示折线
            lineandshaperenderer.setBaseLinesVisible(true);
//            // series 点（即数据点）间有连线可见 显示折点数据
//            lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//            lineandshaperenderer.setBaseItemLabelsVisible(true);
            ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 210, 72);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
