package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (OrderSort)表服务接口
 *
 * @author 
 * @since 2022-06-26 22:40:38
 */
public interface OrderSortService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderSort queryById(Long id);

    /**
     * 分页查询
     *
     * @param orderSort   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<OrderSort> queryByPage(OrderSort orderSort, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderSort 实例对象
     * @return 实例对象
     */
    OrderSort insert(OrderSort orderSort);

    /**
     * 修改数据
     *
     * @param orderSort 实例对象
     * @return 实例对象
     */
    OrderSort update(OrderSort orderSort);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
