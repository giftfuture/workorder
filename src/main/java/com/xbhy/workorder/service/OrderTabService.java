package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderTab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (OrderTab)表服务接口
 *
 * @author 
 * @since 2022-06-26 22:40:39
 */
public interface OrderTabService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderTab queryById(Long id);

    /**
     * 分页查询
     *
     * @param orderTab    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<OrderTab> queryByPage(OrderTab orderTab, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderTab 实例对象
     * @return 实例对象
     */
    OrderTab insert(OrderTab orderTab);

    /**
     * 修改数据
     *
     * @param orderTab 实例对象
     * @return 实例对象
     */
    OrderTab update(OrderTab orderTab);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
