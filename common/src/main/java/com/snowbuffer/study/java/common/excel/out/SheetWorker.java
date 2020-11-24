package com.snowbuffer.study.java.common.excel.out;

import com.snowbuffer.study.java.common.excel.out.column.ExportColumn;
import com.snowbuffer.study.java.common.excel.out.column.PointExportColumn;
import com.snowbuffer.study.java.common.excel.out.column.SummaryRow;
import com.snowbuffer.study.java.common.excel.out.merge.Point;
import com.snowbuffer.study.java.common.excel.out.merge.PointManager;
import com.snowbuffer.study.java.common.excel.support.BasePageQuery;
import com.snowbuffer.study.java.common.excel.support.MelotPageResult;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
public class SheetWorker<T> {

    /**
     * sheet索引值
     */
    private Integer sheetIndex;

    /**
     * sheet名称
     */
    private String sheetName;

    /**
     * 基本分页参数
     */
    private BasePageQuery basePageQuery;

    /**
     * 数据提供器(无分页)
     */
    private Supplier<List<?>> dataSupplierWithoutPage;

    /**
     * 数据提供器(有分页)
     */
    private Function<BasePageQuery, MelotPageResult<?>> dataSupplierWithPage;


    private List<ExportColumn<T>> exportColumns;


    private SummaryRow summaryRow;

    /**
     * 单元格合管理器
     */
    private PointManager pointManager;

    private Map<Integer, Integer> columnWidthMap;

    public SheetWorker(
            Integer sheetIndex, String sheetName, Supplier<List<?>> dataSupplier, List<ExportColumn<T>> settings) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
        this.dataSupplierWithoutPage = dataSupplier;
        this.exportColumns = settings;

        init();

    }

    public SheetWorker(String sheetName,
                       BasePageQuery basePageQuery,
                       Function<BasePageQuery, MelotPageResult<?>> dataSupplier, List<ExportColumn<T>> settings) {
        this(1, sheetName, basePageQuery, dataSupplier, settings);
    }

    public SheetWorker(
            Integer sheetIndex, String sheetName,
            BasePageQuery basePageQuery,
            Function<BasePageQuery, MelotPageResult<?>> dataSupplier, List<ExportColumn<T>> settings) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
        this.basePageQuery = basePageQuery;
        this.dataSupplierWithPage = dataSupplier;
        this.exportColumns = settings;

        init();

    }

    private void init() {

        initPointManager(); // 先初始化manager,在初始化 summaryRown，因为两个set通用一个List,删除操作会相互影响

        initSummaryRow(sheetName, exportColumns.size());

        initColumnWidthMap(exportColumns.size());
    }

    private void initPointManager() {
        Iterator<ExportColumn<T>> iterator = exportColumns.iterator();
        while (iterator.hasNext()) {
            ExportColumn item = iterator.next();
            if (item instanceof PointExportColumn) {
                PointExportColumn pointExportColumn = (PointExportColumn) item;
                if (pointExportColumn.existPointList()) {
                    this.pointManager = new PointManager();
                    List<Point> pointList = pointExportColumn.getPointList();
                    for (Point point : pointList) {
                        pointManager.addPoint(point);
                    }
                    pointManager.preCalcRectangle(); // 提前计算矩阵
                }
                iterator.remove();
            }
        }
    }

    private void initSummaryRow(String sheetName, int size) {
        for (int i = 0; i < size; i++) {
            ExportColumn column = exportColumns.get(i);
            if (column.existSummaryAction()) {
                summaryRow = new SummaryRow(sheetName, size);
                break;
            }
        }

        if (summaryRow != null) {
            for (int i = 0; i < size; i++) {
                ExportColumn column = exportColumns.get(i);
                if (column.existSummaryAction()) {
                    summaryRow.put(i, column);
                }
            }
        }
    }

    private void initColumnWidthMap(int size) {
        if (size > 0) {
            columnWidthMap = new HashMap<>();
        }
        for (int i = 0; i < size; i++) {
            ExportColumn column = exportColumns.get(i);
            if (column.existColumnWidth()) {
                columnWidthMap.put(i, column.getColumnWidth());
            }
        }
    }

    public boolean existSummaryRow() {
        return summaryRow != null;
    }

    public boolean existPointManager() {
        return pointManager != null;
    }

    public boolean existColumnWidthMap() {
        return columnWidthMap != null && columnWidthMap.size() > 0;
    }

}