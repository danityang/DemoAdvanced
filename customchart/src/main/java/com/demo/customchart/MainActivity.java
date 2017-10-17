package com.demo.customchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class MainActivity extends AppCompatActivity {

    // 图表View控件
    private ColumnChartView columnChartView;
    // 图表数据
    private ColumnChartData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        columnChartView = (ColumnChartView) findViewById(R.id.columnchartview);
        initData();
    }

    private void initData() {
        int numSubcolumns = 2;
        int numColumns = 8;
        // 长方体集合
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        // 可以理解为x轴上的循环
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            // 可以理解为y轴上的循环
            for (int j = 0; j < numSubcolumns; ++j) {
                // 添加y轴上的数据
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            // 长方体数据
            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);

            data = new ColumnChartData(columns);
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            axisX.setName("Axis X");
            axisY.setName("Axis Y");

            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

            columnChartView.setColumnChartData(data);
        }
    }
}
