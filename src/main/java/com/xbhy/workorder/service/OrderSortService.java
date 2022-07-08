package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderSort;
import com.xbhy.workorder.vo.OrderSortVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
    OrderSortVO queryById(Long id);

    /**
     * 分页查询
     *
     * @param orderSortVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<OrderSortVO> queryByPage(OrderSortVO orderSortVO, PageRequest pageRequest);

    /**
     * 获取所有工单类别
     * @return
     */
    List<OrderSortVO> fetchAll();
    /**
     * 新增数据
     *
     * @param orderSortVO 实例对象
     * @return 实例对象
     */
    OrderSortVO insert(OrderSortVO orderSortVO);

    /**
     * 修改数据
     *
     * @param orderSortVO 实例对象
     * @return 实例对象
     */
    OrderSortVO update(OrderSortVO orderSortVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
