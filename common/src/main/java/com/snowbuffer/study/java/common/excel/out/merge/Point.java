package com.snowbuffer.study.java.common.excel.out.merge;

import com.alibaba.excel.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-09-20 14:59
 */
public class Point {
    private int rowStart; // 行号，从1 开始
    private int columStart; // 列号，从1 开始
    private int leftToRightRepeat; // 行方向重复次数 包含rowStart位置上的header
    private int upToDownRepeat; // 列方向重复次数 包含columStart位置上的header
    private Object header;

    /*
        [1, 1, 1, 1, 1, 1, 1]
        [2, 2, 3, 3, 4, 4, 4]
        [BBBB, BBBB, 3, 3, BBBB, BBBB, 5]
        [BBBB, BBBB, 3, 3, BBBB, BBBB, 5]
        [BBBB, BBBB, BBBB, BBBB, BBBB, BBBB, 5]
        [BBBB, 6, 6, 6, 6, BBBB, 5]
        [BBBB, 6, 6, 6, 6, BBBB, BBBB]
        [BBBB, 6, 6, 6, 6, BBBB, BBBB]
        [BBBB, BBBB, BBBB, BBBB, BBBB, BBBB, BBBB]
        [BBBB, BBBB, BBBB, BBBB, BBBB, BBBB, BBBB]
        [BBBB, BBBB, BBBB, BBBB, 7, 7, 7]
        [BBBB, 8, 8, 8, 8, BBBB, BBBB]
        [BBBB, 8, 8, 8, 8, BBBB, BBBB]
     */

    // 所有列rowStart + upToDownRepeat 中的 最大值为当前sheet-title行的最大行数
    // 所有行columStart + leftToRightRepeat 中的 最大值为当前sheet-title列的最大列数
    public Point(int rowStart/*行号*/, int columnStart/*列号*/,
                 int leftToRightRepeat/*行方向header重复次数*/, int upToDownRepeat/*列方向header重复次数*/,
                 Object header) {
        check(rowStart, columnStart, leftToRightRepeat, upToDownRepeat, header);
        this.rowStart = rowStart;
        this.columStart = columnStart;
        this.leftToRightRepeat = leftToRightRepeat;
        this.upToDownRepeat = upToDownRepeat;
        this.header = header;
    }

    public List<Integer> getPointRangeList() {
        List<Integer> rs = new ArrayList<>();
        // 起始行
        rs.add(rowStart - 1);
        // 结束行
        rs.add(rowStart + upToDownRepeat - 2);
        // 起始列
        rs.add(columStart - 1);
        // 结束列
        rs.add(columStart + leftToRightRepeat - 2);
        return rs;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getColumStart() {
        return columStart;
    }

    public int getLeftToRightRepeat() {
        return leftToRightRepeat;
    }

    public int getUpToDownRepeat() {
        return upToDownRepeat;
    }

    public Object getHeader() {
        return header;
    }

    private void check(int rowStart, int columStart, int rowDataRepeat, int columDataRepeat, Object header) {
        if (rowStart <= 0) {
            throw new RuntimeException("rowStart 不能小于 0");
        }
        if (columStart <= 0) {
            throw new RuntimeException("columStart 不能小于 0");
        }
        if (rowDataRepeat <= 0) {
            throw new RuntimeException("rowDataRepeat 不能小于 0");
        }
        if (columDataRepeat <= 0) {
            throw new RuntimeException("columDataRepeat 不能小于 0");
        }
        if (StringUtils.isEmpty(header)) {
            throw new RuntimeException("header 不能为empty");
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Point [");
        sb.append("rowStart=").append(rowStart);
        sb.append(", columStart=").append(columStart);
        sb.append(", leftToRightRepeat=").append(leftToRightRepeat);
        sb.append(", upToDownRepeat=").append(upToDownRepeat);
        sb.append(", header=").append(header);
        sb.append("]");
        return sb.toString();
    }
}

