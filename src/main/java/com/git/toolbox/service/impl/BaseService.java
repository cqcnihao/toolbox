package com.git.toolbox.service.impl;

import com.git.toolbox.service.IService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }
    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int saveNotNull(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteByExample(Object example) {

        return mapper.deleteByExample(example);

    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }
    @Override
    public int updateNotNull(T entity) {

        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByExample(T entity, Object example) {
        return mapper.updateByExample(entity,example);
    }

    @Override
    public int updateByExampleNotNull(T entity, Object example) {

        return mapper.updateByExampleSelective(entity,example);
    }


    @Override
    public int selectCountByEntity(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> selectByEntity(T entity) {
        return mapper.select(entity);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public PageInfo selectByExample(Object example, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list =
                mapper.selectByExample(example);
        return new PageInfo(list);
    }
}
