package com.git.toolbox.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IService<T> {

    T selectByKey(Object key);

    int saveNotNull(T entity);

    int save(T entity);

    int delete(Object key);

    int deleteByExample(Object example);

    int updateAll(T entity);

    int updateNotNull(T entity);

    int updateByExample(T entity, Object example);

    /**
     * 根据Example条件更新实体`entity`包含的不是null的属性值
     *
     * @param entity
     * @param example
     * @return
     */
    int updateByExampleNotNull(T entity, Object example);

    int selectCountByEntity(T entity);

    int selectCountByExample(Object example);

    List<T> selectAll();

    List<T> selectByEntity(T entity);

    List<T> selectByExample(Object example);

    @SuppressWarnings("rawtypes")
    PageInfo selectByExample(Object example, int pageNum, int pageSize);

}
