package com.snowbuffer.study.java.common.excel.out.column;

import com.snowbuffer.study.java.common.excel.out.merge.Point;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-09-23 9:50
 */
public class PointExportColumn<T> extends ExportColumn<T> {

    private List<Point> pointList;


    public PointExportColumn(List<Point> pointList) {
        super(null, null);
        this.pointList = pointList;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public boolean existPointList() {
        return false == CollectionUtils.isEmpty(pointList);
    }
}

