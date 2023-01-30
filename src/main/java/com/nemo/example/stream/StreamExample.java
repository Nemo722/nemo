package com.nemo.example.stream;

import com.nemo.example.Example;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mingLong.zhao
 */
public class StreamExample {

    private List<Example> list;

    public void example() {

        // 一对一map
        Map<Integer, Example> oneTOneMap =
            list.stream().collect(Collectors.toMap(Example::getId, example -> example));

        // 转成LinkedHashMap
        LinkedHashMap<String, List<Example>> listLinkedHashMap =
            list.stream().collect(Collectors.groupingBy(Example::getName, LinkedHashMap::new, Collectors.toList()));

        // 分组计数
        Map<String, Long> countMap = list.stream()
            .collect(Collectors.groupingBy(Example::getName, Collectors.counting()));

        // 去重
        list = list.stream().collect(Collectors.collectingAndThen(
            Collectors.toCollection(() -> new TreeSet<>(
                Comparator.comparing(data -> data.getName() + "-" + data.getText()))), ArrayList::new));

        // 多级排序
        // 数字降序，中文首字母生序
        Comparator<Object> chinaComparator = Collator.getInstance(Locale.CHINA);
        list = list.stream().sorted(Comparator.comparing(Example::getNumber, Comparator.reverseOrder())
                .thenComparing((a, b) -> chinaComparator.compare(a.getName(), b.getName())))
            .collect(Collectors.toList());
    }
}
