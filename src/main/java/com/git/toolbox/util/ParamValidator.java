package com.git.toolbox.util;

import com.git.toolbox.exception.CommonException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 在web应用的Controller层经常要校验参数是否合法，其中一种形式是json格式
 * 传输，在Controller层可以将其解析为一个参数对象，对于该参数的校验，
 * 通常校验都是if(xx==null) do:something if (yy==null) do:something
 * 这里可以使用monad模式，统一校验~~~
 */
public class ParamValidator {


    private final List<String> msgs = new ArrayList<>();


    public static ParamValidator of() {
        return new ParamValidator();
    }

    public <T extends Serializable> ParamValidator validate(T t, Predicate<T> validation, String msg) {
        if (!validation.test(t)) {
            msgs.add(msg);
        }
        return this;
    }


    /**
     * @return 空串则代表被校验的参数没有问题，否则返回错误信息
     */
    public void valid() throws CommonException {
        if (msgs.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        msgs.forEach(msg -> sb.append(msg).append("、"));
        throw new CommonException(415, sb.substring(0, sb.length() - 1) + "不能为空或格式错误");
    }


    /**
     * 使用方式
     */
    public static void test() {
        Integer packId = Integer.valueOf(1);
        Integer cityId = Integer.valueOf(11);
        Byte gender = Byte.valueOf((byte) 1);
        Integer age = null;
        String name = "";
        String errorMsg = null;
        try {
            ParamValidator.of().validate(packId, Objects::nonNull, "套餐ID")
                    .validate(cityId, Objects::nonNull, "城市ID")
                    .validate(gender, Objects::nonNull, "性别")
                    .validate(age, Objects::nonNull, "年龄")
                    .validate(name, StringUtils::isNoneEmpty, "姓名").valid();
        } catch (CommonException e) {
            e.printStackTrace();
        }
        System.out.println(errorMsg);
    }


}

