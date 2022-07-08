package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.OrderDict;
import com.xbhy.workorder.vo.OrderDictVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (OrderDict)表服务接口
 *
 * @author 
 * @since 2022-06-28 09:44:41
 */
public interface OrderDictService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderDictVO queryById(Long id);

    /**
     * 工单字典服务
     * @return
     */
    List<OrderDictVO> loadOrderDict();
    /**
     * 工单字典服务
     * @return
     */
    OrderDictVO loadOrderDictBySort(String sortTag);
    /**
     * 分页查询
     *
     * @param orderDictVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<OrderDictVO> queryByPage(OrderDictVO orderDictVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderDictVO 实例对象
     * @return 实例对象
     */
    OrderDictVO insert(OrderDictVO orderDictVO);

    /**
     * 修改数据
     *
     * @param orderDictVO 实例对象
     * @return 实例对象
     */
    OrderDictVO update(OrderDictVO orderDictVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
