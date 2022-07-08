package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderTab;
import com.xbhy.workorder.vo.OrderSortVO;
import com.xbhy.workorder.vo.OrderTabVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
    OrderTabVO queryById(Long id);
    /**
     * 获取所有选项卡
     * @return
     */
    List<OrderTabVO> fetchAll();
    /**
     * 分页查询
     *
     * @param orderTabVO    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<OrderTabVO> queryByPage(OrderTabVO orderTabVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderTabVO 实例对象
     * @return 实例对象
     */
    OrderTabVO insert(OrderTabVO orderTabVO);

    /**
     * 修改数据
     *
     * @param orderTabVO 实例对象
     * @return 实例对象
     */
    OrderTabVO update(OrderTabVO orderTabVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
