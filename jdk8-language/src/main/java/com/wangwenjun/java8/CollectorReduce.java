package com.wangwenjun.java8;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/27 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CollectorReduce {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        long count = menu.stream().filter(d -> d.isVegetarian()).count();

        Long collect = menu.stream().filter(d -> d.isVegetarian()).collect(Collectors.counting());

//        Optional<Integer> maxCalories = menu.stream().map(Dish::getCalories).reduce(Integer::max);
        // reduce 分析： https://blog.csdn.net/icarusliu/article/details/79504602
        Optional<Dish> maxCalories = menu.stream().reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2);
        maxCalories.ifPresent((item) -> System.out.println("==" + item));

        Optional<Dish> maxCaloriesCollect = menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        maxCaloriesCollect.ifPresent(System.out::println);


        Integer collect1 = menu.stream().collect(Collectors.collectingAndThen(toList(), t -> t.size()));
        System.out.println("==" + collect1);
        Map<Dish.Type, List<Dish>> collect2 = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println("==" + collect2);
        Map<Dish.Type, Double> collect3 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));
        System.out.println("==" + collect3);

    }
}

/*

stream：reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2) 获取最大值
        filter(d -> d.isVegetarian()) 过滤数据， true 包含， false 排除
        map(Dish::getName) -> 抽取项目
        collect：Collectors.counting() 统计个数
            Collectors.maxBy/minBy(Comparator.comparingInt(Dish::getCalories)) 获取最大值/最小值
            Collectors.reducing(
                        BinaryOperator.maxBy(
                                Comparator.comparingInt(Dish::getCalories)
                        )
                ) 获取最大值  => reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
            Collectors.collectingAndThen(toList(), t -> t.size()) 先搜集数据，然后在结果机上进行二次处理
            Collectors.groupingBy(Dish::getType) 分组
            Collectors.partitioningBy(Dish::isVegetarian) 分组 功能与 groupingBy一样
            Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)) 先分组，然后对同组的数据进行求平均值
            Collectors.partitioningBy(Dish::isVegetarian, Collectors.averagingInt(Dish::getCalories))  先分组，然后对同组的数据进行求平均值 groupingBy一样
            Collectors.groupingBy(Dish::getType, TreeMap::new, Collectors.averagingInt(Dish::getCalories)) 先分组，然后对同组的数据进行求平均值 结果使用treeMap封装
            Collectors.groupingByConcurrent(Dish::getType) 同上
            Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b)) 分组，并统计每组的元素个数
            Collectors.averagingDouble(Dish::getCalories)) 通过dubule方式求平均值
            Collectors.averagingInt(Dish::getCalories)) 通过int方式求平均值
            Collectors.averagingLong(Dish::getCalories)) 通过long方式求平均值
            Collectors.summarizingInt(Dish::getCalories) 计算统计信息：如：{count=9, sum=1000, min=120, average=500.666667, max=800}
            Collectors.summarizingDouble(Dish::getCalories))  计算统计信息：如：{count=9, sum=1000.000000, min=120.000000, average=500.666667, max=800.000000}
            Collectors.summarizingLong(Dish::getCalories)) 计算统计信息：如：{count=9, sum=1000, min=120, average=500.666667, max=800}
            Collectors.summarizingInt(Dish::getCalories))  计算统计信息：如：{count=9, sum=1000, min=120, average=500.666667, max=800}
            Collectors.summingDouble(Dish::getCalories)) 求和 100.0
            map(Dish::getCalories).mapToInt(Integer::intValue).sum() 求和 100
            Collectors.summingLong(Dish::getCalories))
            Collectors.summingInt(Dish::getCalories))
            Collectors.joining()) 将结果集进行合并
            Collectors.joining(",", "Names[", "]")) 同上，以,号分割，前缀为Names[, 后缀为]
            Collectors.mapping(Dish::getName, Collectors.joining(","))) 将结果集进行合并， 是map(Dish::getName) 和 joining(",") 综合使用
            Collectors.reducing(0, (d1, d2) -> d1 + d2): 求和 基础值为0  前置条件需要 map(Dish::getCalories)
            Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2) 求和 基础值为0 不需要前置条件，本方法已经存在内置条件，功能 Collectors.reducing(0, (d1, d2) -> d1 + d2)
            Collectors.toCollection(LinkedList::new))
            Collectors.toMap(Dish::getName, Dish::getCalories)
            Collectors.toMap(Dish::getType, v -> 1L, (a, b) -> a + b, Hashtable::new))
            Collectors.toConcurrentMap(Dish::getName, Dish::getCalories))
            Collectors.toList()
            Collectors.toSet()
 */