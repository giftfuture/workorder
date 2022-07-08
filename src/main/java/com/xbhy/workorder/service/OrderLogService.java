package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderLog;
import com.xbhy.workorder.vo.OrderLogVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
    OrderLogVO queryById(Long id);

    /**
     * 工单操作
     * @param orderId
     * @return
     */
    List<OrderLogVO> queryByOrderId(Long orderId);

    /**
     * 分页查询
     *
     * @param orderLogVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<OrderLogVO> queryByPage(OrderLogVO orderLogVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderLogVO 实例对象
     * @return 实例对象
     */
    OrderLogVO insert(OrderLogVO orderLogVO);

    /**
     * 修改数据
     *
     * @param orderLogVO 实例对象
     * @return 实例对象
     */
    OrderLogVO update(OrderLogVO orderLogVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
