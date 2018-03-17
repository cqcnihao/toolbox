package com.git;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by poan on 2018/03/17.
 */
public class RescursiveTest {


    private static List list = Arrays.asList("人", 1, 2, 3, 4, "汉", 5, 6, 7, 8, "钋", 9, 10, 11, "人1", 12);

    private static List<String> futures = Arrays.asList("人", "钋", "汉", "人1");


    public static Map<String, List> groupByAlph(Iterator iterator) throws NoSuchFieldException, IllegalAccessException {


        Map<String, List> resultMap = new LinkedHashMap<>();

        while (iterator.hasNext()) {
            Object next = iterator.next();
            String ele = String.valueOf(next);
            if (futures.contains(ele)) {
                Set<String> keys = resultMap.keySet();
                if (!keys.contains(ele)) {
                    resultMap.put(ele, new ArrayList());
                }
            } else {
                // 获取resultMap最新添加的元素
                Field tail = resultMap.getClass().getDeclaredField("tail");
                tail.setAccessible(true);
                Map.Entry lastEntry = (Map.Entry) tail.get(resultMap);
                String lastKey = (String) lastEntry.getKey();

                List group = resultMap.get(lastKey);
                group.add(ele);
                resultMap.put(lastKey, group);
            }


        }
        return resultMap;

    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Map<String, List> stringListMap = groupByAlph(list.iterator());
        for (Map.Entry<String, List> entry : stringListMap.entrySet()) {
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
        }

    }

}
