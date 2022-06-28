package com.xbhy.workorder.service.impl;

import com.xbhy.workorder.entity.OrderTab;
import com.xbhy.workorder.dao.OrderTabDao;
import com.xbhy.workorder.service.OrderTabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (OrderTab)表服务实现类
 *
 * @author 
 * @since 2022-06-26 22:40:40
 */
@Slf4j
@Primary
@Transactional
@Service("orderTabService")
public class OrderTabServiceImpl implements OrderTabService {
    @Resource
    private OrderTabDao orderTabDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderTab queryById(Long id) {
        return this.orderTabDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param orderTab    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderTab> queryByPage(OrderTab orderTab, PageRequest pageRequest) {
        long total = this.orderTabDao.count(orderTab);
        return new PageImpl<>(this.orderTabDao.queryAllByLimit(orderTab, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderTab 实例对象
     * @return 实例对象
     */
    @Override
    public OrderTab insert(OrderTab orderTab) {
        this.orderTabDao.insert(orderTab);
        return orderTab;
    }

    /**
     * 修改数据
     *
     * @param orderTab 实例对象
     * @return 实例对象
     */
    @Override
    public OrderTab update(OrderTab orderTab) {
        this.orderTabDao.update(orderTab);
        return this.queryById(orderTab.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderTabDao.deleteById(id) > 0;
    }
}
