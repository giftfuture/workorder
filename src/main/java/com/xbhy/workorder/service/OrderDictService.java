package com.xbhy.workorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (OrderDict)表服务接口
 *
 * @author makejava
 * @since 2022-06-28 09:44:41
 */
public interface OrderDictService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderDict queryById(Long id);

    /**
     * 分页查询
     *
     * @param orderDict 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<OrderDict> queryByPage(OrderDict orderDict, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderDict 实例对象
     * @return 实例对象
     */
    OrderDict insert(OrderDict orderDict);

    /**
     * 修改数据
     *
     * @param orderDict 实例对象
     * @return 实例对象
     */
    OrderDict update(OrderDict orderDict);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
