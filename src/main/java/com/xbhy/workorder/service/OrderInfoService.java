package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderInfo;
import com.xbhy.workorder.vo.OrderInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (OrderInfo)表服务接口
 *
 * @author 
 * @since 2022-06-26 22:40:35
 */
public interface OrderInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderInfoVO queryById(Long id);

    /**
     * 分页查询
     *
     * @param orderInfoVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<OrderInfoVO> queryByPage(OrderInfoVO orderInfoVO, PageRequest pageRequest);


    /**
     * 综合搜索
     * @param orderInfoVO
     * @param pageRequest
     * @return
     */
    Page<OrderInfoVO> overallSearch(OrderInfoVO orderInfoVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderInfoVO 实例对象
     * @return 实例对象
     */
    OrderInfoVO insert(OrderInfoVO orderInfoVO);

    /**
     * 修改数据
     *
     * @param orderInfoVO 实例对象
     * @return 实例对象
     */
    OrderInfoVO update(OrderInfoVO orderInfoVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
