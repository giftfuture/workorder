package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.StaffOrder;
import com.xbhy.workorder.vo.StaffOrderVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (StaffOrder)表服务接口
 *
 * @author 
 * @since 2022-06-26 22:40:42
 */
public interface StaffOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffOrderVO queryById(Long id);

    /**
     * 分页查询
     *
     * @param staffOrderVO  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<StaffOrderVO> queryByPage(StaffOrderVO staffOrderVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param staffOrderVO 实例对象
     * @return 实例对象
     */
    StaffOrderVO insert(StaffOrderVO staffOrderVO);

    /**
     * 修改数据
     *
     * @param staffOrder 实例对象
     * @return 实例对象
     */
    StaffOrderVO update(StaffOrderVO staffOrderVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
