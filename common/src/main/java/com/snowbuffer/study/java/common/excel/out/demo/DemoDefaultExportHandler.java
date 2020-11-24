package com.snowbuffer.study.java.common.excel.out.demo;

import com.google.common.collect.Lists;
import com.snowbuffer.study.java.common.excel.out.*;
import com.snowbuffer.study.java.common.excel.out.column.ExportColumn;
import com.snowbuffer.study.java.common.excel.out.column.PointExportColumn;
import com.snowbuffer.study.java.common.excel.out.handler.DefaultExportHandler;
import com.snowbuffer.study.java.common.excel.out.merge.Point;
import com.snowbuffer.study.java.common.excel.support.BasePageQuery;
import com.snowbuffer.study.java.common.excel.support.MelotListUtil;
import com.snowbuffer.study.java.common.excel.support.MelotPageResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 22:35
 */
public class DemoDefaultExportHandler {


    public static void main(String[] args) {
        // 搜集handler
        List<ExportHandler> exportHandlers = Lists.newArrayList();
        DefaultExportHandler handler = new DefaultExportHandler();
        exportHandlers.add(handler);

        // 实例化引擎
        ExportEngine exportEngine = new ExportEngine(exportHandlers);

        // 设置配置
        ExportConfig exportConfig = new ExportConfig();
        exportConfig.setDebug(false);
        exportConfig.setUserId(10086L);
        exportConfig.setDownLoadFileName("abc.xlsx");
        exportConfig.setExportSource(null);


        // 设置sheetWorker
        List<SheetWorker<?>> sheetWorkers = Lists.newArrayList();

        CourseJoinPreService courseJoinPreService = new CourseJoinPreService();
        CourseJoinPreVOParam preVOParam = new CourseJoinPreVOParam();
        preVOParam.setPageSize(3);
        preVOParam.setOffset(0);
        SheetWorker<CourseJoinPreVO> sheetWorker
                = new SheetWorker<>("abc", preVOParam, new Function<BasePageQuery, MelotPageResult<?>>() {
            @Override
            public MelotPageResult<?> apply(BasePageQuery pageQuery) {
                return courseJoinPreService.query(preVOParam);
            }
        }, getExportSetting());

        sheetWorkers.add(sheetWorker);
        exportConfig.setSheetWorkers(sheetWorkers);

        // 开始导出
        exportEngine.start(exportConfig);

        // 关闭引擎
        exportEngine.close();
    }

    public static List<ExportColumn<CourseJoinPreVO>> getExportSetting() {
        List<ExportColumn<CourseJoinPreVO>> setting = Lists.newArrayList();
        setting.add(new PointExportColumn<>(
                Arrays.asList(
                        new Point(3, 7, 1, 4, 5),
                        new Point(2, 1, 2, 1, "2"),
                        new Point(2, 3, 2, 3, "3"),
                        new Point(2, 5, 3, 1, "4"),
                        new Point(11, 5, 3, 1, "7"),
                        new Point(1, 1, 7, 1, "1"),
                        new Point(12, 2, 4, 2, "8"),
                        new Point(6, 2, 4, 3, "6")
                )));
        setting.add(new ExportColumn<>("课程名称", CourseJoinPreVO::getCourseName));
        setting.add(new ExportColumn<>("学段", CourseJoinPreVO::getSectionName));
        // 数值类型测试
        setting.add(new ExportColumn<>("用户id", CourseJoinPreVO::getUserId, new Function<List<CourseJoinPreVO>, Object>() {
            @Override
            public Object apply(List<CourseJoinPreVO> courseJoinPreVOS) {
                return "汇总行：";
            }
        }));
        setting.add(new ExportColumn<>("班级", CourseJoinPreVO::getClassName, 30));
        // 数值类型测试
        setting.add(new ExportColumn<>("用户id", CourseJoinPreVO::getUserId, new Function<List<CourseJoinPreVO>, Object>() {
            @Override
            public Object apply(List<CourseJoinPreVO> courseJoinPreVOS) {
                List<Long> idList = collectIdList(courseJoinPreVOS, CourseJoinPreVO::getUserId);
                LongSummaryStatistics collect = idList.stream().collect(Collectors.summarizingLong(value -> value));
                long rs = 0L;
                if (collect != null) {
                    rs = collect.getCount();
                }
                return "总数：" + rs;
            }
        }));
        setting.add(new ExportColumn<>("用户名", CourseJoinPreVO::getUsername, 50));
        // 数值类型测试
        setting.add(new ExportColumn<>("用户id", CourseJoinPreVO::getUserId, new Function<List<CourseJoinPreVO>, Object>() {
            @Override
            public Object apply(List<CourseJoinPreVO> courseJoinPreVOS) {
                List<Long> idList = collectIdList(courseJoinPreVOS, CourseJoinPreVO::getUserId);
                LongSummaryStatistics collect = idList.stream().collect(Collectors.summarizingLong(value -> value));
                Double rs = 0d;
                if (collect != null) {
                    rs = collect.getAverage();
                }
                return "平均值：" + rs;
            }
        }));
        setting.add(new ExportColumn<>("姓名", CourseJoinPreVO::getPersonName));
        // 数值类型测试
        setting.add(new ExportColumn<>("用户id", CourseJoinPreVO::getUserId, new Function<List<CourseJoinPreVO>, Object>() {
            @Override
            public Object apply(List<CourseJoinPreVO> courseJoinPreVOS) {
                List<Long> idList = collectIdList(courseJoinPreVOS, CourseJoinPreVO::getUserId);
                LongSummaryStatistics collect = idList.stream().collect(Collectors.summarizingLong(value -> value));
                long rs = 0L;
                if (collect != null) {
                    rs = collect.getSum();
                }
                return "总和：" + rs;
            }
        }));
        setting.add(new ExportColumn<>("报名类型", CourseJoinPreVO::getJoinTypeCn));
        // 数值类型测试
        setting.add(new ExportColumn<>("用户id", CourseJoinPreVO::getUserId, new Function<List<CourseJoinPreVO>, Object>() {
            @Override
            public Object apply(List<CourseJoinPreVO> courseJoinPreVOS) {
                List<Long> idList = collectIdList(courseJoinPreVOS, CourseJoinPreVO::getUserId);
                LongSummaryStatistics collect = idList.stream().collect(Collectors.summarizingLong(value -> value));
                long rs = 0L;
                if (collect != null) {
                    rs = collect.getMax();
                }
                return "最大值：" + rs;
            }
        }, 80));
        setting.add(new ExportColumn<>("报名时间", CourseJoinPreVO::getJoinTime));
        // 数值类型测试
        setting.add(new ExportColumn<>("用户id", CourseJoinPreVO::getUserId, new Function<List<CourseJoinPreVO>, Object>() {
            @Override
            public Object apply(List<CourseJoinPreVO> courseJoinPreVOS) {
                List<Long> idList = collectIdList(courseJoinPreVOS, CourseJoinPreVO::getUserId);
                LongSummaryStatistics collect = idList.stream().collect(Collectors.summarizingLong(value -> value));
                long rs = 0L;
                if (collect != null) {
                    rs = collect.getMin();
                }
                return "最小值：" + rs;
            }
        }));
        return setting;
    }

    public static <T, TARGET> List<T> collectIdList(List<TARGET> list, Function<TARGET, T> convertFn) {
        if (MelotListUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<T> idList = new ArrayList<>(list.size());
        list.forEach((item) -> idList.add(convertFn.apply(item)));
        return idList;
    }

    private static class CourseJoinPreVOParam extends BasePageQuery {

    }

    private static class CourseJoinPreService {

        public MelotPageResult<CourseJoinPreVO> query(CourseJoinPreVOParam courseJoinPreVOParam) {
            return DB.select(courseJoinPreVOParam);
        }
    }

    private static class DB {

        private final static List<CourseJoinPreVO> list = Lists.newArrayList();

        static {
            for (long i = 0; i < 20; i++) {
                CourseJoinPreVO vo = new CourseJoinPreVO();
                vo.setUserId(i);
                vo.setPersonName(i + "_PersonName");
                vo.setSchoolName(i + "_SchoolName");
                vo.setSectionName(i + "_SectionName");
                vo.setClassName(i + "_ClassName");
                vo.setUsername(i + "_Username");
                vo.setCourseName(i + "_CourseName");
                vo.setJoined(i + "_Joined");
                vo.setJoinTime(i + "_JoinTime");
                vo.setPayTypeCn(i + "_PayTypeCn");
                vo.setJoinTypeCn(i + "_JoinTypeCn");
                vo.setOperator(i + "_Operator");
                list.add(vo);
            }
        }

        public static MelotPageResult<CourseJoinPreVO> select(CourseJoinPreVOParam courseJoinPreVOParam) {
            Integer offset = courseJoinPreVOParam.getOffset();
            Integer pageSize = courseJoinPreVOParam.getPageSize();
            List<List<CourseJoinPreVO>> split = MelotListUtil.split(list, pageSize);
            int currentNum = offset / pageSize;
            List<CourseJoinPreVO> courseJoinPreVOS = split.get(currentNum);

            MelotPageResult<CourseJoinPreVO> pageResult = new MelotPageResult<>();
            pageResult.setList(courseJoinPreVOS);
            pageResult.setPageSize(pageSize);
            pageResult.setTotal(list.size());
            return pageResult;
        }
    }

    @Data
    public static class CourseJoinPreVO {
        private Long userId;
        private String personName;
        private String schoolName;
        private String sectionName;
        private String className;
        private String username;
        private String courseName;

        private String joined;

        private String joinTime;
        private List<String> payWayDetail;

        private String payTypeCn;

        private String joinTypeCn;
        private String operator;


    }
}
