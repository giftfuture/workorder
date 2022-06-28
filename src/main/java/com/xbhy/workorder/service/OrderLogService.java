package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (OrderLog)表服务接口
 *
 * @author 
 * @since 2022-06-27 13:37:42
 */
public interface OrderLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderLog queryById(Long id);

    /**
     * 分页查询
     *
     * @param orderLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<OrderLog> queryByPage(OrderLog orderLog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderLog 实例对象
     * @return 实例对象
     */
    OrderLog insert(OrderLog orderLog);

    /**
     * 修改数据
     *
     * @param orderLog 实例对象
     * @return 实例对象
     */
    OrderLog update(OrderLog orderLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
