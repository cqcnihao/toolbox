package com.git.toolbox.framework.monad;

import com.git.toolbox.exception.CommonException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 在web应用的Controller层经常要校验参数是否合法，其中一种形式是json格式
 * 传输，在Controller层可以将其解析为一个参数对象，对于该参数的校验，
 * 通常校验都是if(xx==null) do:something if (yy==null) do:something
 * 这里可以使用monad模式，统一校验~~~
 */
public class ParamValidator<T> {

    private final T t;

    private final List<Throwable> exceptions = new ArrayList<>();

    public ParamValidator(T t) {
        this.t = t;
    }

    // ParamValidator即构建器，由ParamValidator来对T进行预处理
    public static <T> ParamValidator<T> of(T t) {
        return new ParamValidator<>(Objects.requireNonNull(t));
    }

    public ParamValidator<T> validate(Predicate<T> validation, String msg) {
        if (!validation.test(t)) {
            exceptions.add(new CommonException(msg));
        }
        return this;
    }


    /**
     * @param projection 英文为“投射”；即，获取某个属性的函数
     * @param validation 校验的函数
     * @param msg        检验不通过时，设置的提示语
     * @param <U>
     * @return
     */
    public <U> ParamValidator<T> validate(Function<T, U> projection, Predicate<U> validation, String msg) {
        return validate(projection.andThen(validation::test)::apply, msg);
    }

    /**
     * @return 被校验的实体
     * @throws IllegalStateException
     */
    public T get() throws IllegalStateException {
        if (exceptions.isEmpty()) {
            return t;
        }
        IllegalStateException e = new IllegalStateException();
        exceptions.forEach(e::addSuppressed);
        throw e;
    }


}
