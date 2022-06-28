package com.xbhy.workorder.service.impl;

import com.xbhy.workorder.entity.StaffOrder;
import com.xbhy.workorder.dao.StaffOrderDao;
import com.xbhy.workorder.service.StaffOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (StaffOrder)表服务实现类
 *
 * @author 
 * @since 2022-06-26 22:40:42
 */
@Slf4j
@Primary
@Transactional
@Service("staffOrderService")
public class StaffOrderServiceImpl implements StaffOrderService {
    @Resource
    private StaffOrderDao staffOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StaffOrder queryById(Long id) {
        return this.staffOrderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param staffOrder  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<StaffOrder> queryByPage(StaffOrder staffOrder, PageRequest pageRequest) {
        long total = this.staffOrderDao.count(staffOrder);
        return new PageImpl<>(this.staffOrderDao.queryAllByLimit(staffOrder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staffOrder 实例对象
     * @return 实例对象
     */
    @Override
    public StaffOrder insert(StaffOrder staffOrder) {
        this.staffOrderDao.insert(staffOrder);
        return staffOrder;
    }

    /**
     * 修改数据
     *
     * @param staffOrder 实例对象
     * @return 实例对象
     */
    @Override
    public StaffOrder update(StaffOrder staffOrder) {
        this.staffOrderDao.update(staffOrder);
        return this.queryById(staffOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.staffOrderDao.deleteById(id) > 0;
    }
}
