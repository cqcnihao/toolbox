package com.git.toolbox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by poan on 2017/11/01.
 */
public class ConsumerTest {

    /**
     * 当一个一百行的方法中，99行都一样，只有一行不一样；在请求一个Api时，
     * 需要准备的参数不一样，
     *
     * @param consumer
     * @return
     */
    public static Map<String, Integer> apply(Consumer<Map<String, Integer>> consumer, String... requestParams) {

        // 拼接请求参数requestParams...
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1); // 使用IDEA的shift crtl alt + p 可以将改行代码function化！！以后不用再去找Function接口了！
        map.put("b", 2);

        consumer.accept(map);

        // 使用http请求或是使用RPC调用远程接口...

        //
        return map;
    }

    public static void main(String[] args) {

        Map<String, Integer> c = apply(map -> map.put("c", 3));
        for (Map.Entry<String, Integer> entry : c.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }


    }

}
