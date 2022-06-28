package com.xbhy.workorder.service.impl;

import com.xbhy.workorder.entity.OrderSort;
import com.xbhy.workorder.dao.OrderSortDao;
import com.xbhy.workorder.service.OrderSortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (OrderSort)表服务实现类
 *
 * @author 
 * @since 2022-06-26 22:40:38
 */
@Slf4j
@Primary
@Transactional
@Service("orderSortService")
public class OrderSortServiceImpl implements OrderSortService {
    @Resource
    private OrderSortDao orderSortDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderSort queryById(Long id) {
        return this.orderSortDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param orderSort   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderSort> queryByPage(OrderSort orderSort, PageRequest pageRequest) {
        long total = this.orderSortDao.count(orderSort);
        return new PageImpl<>(this.orderSortDao.queryAllByLimit(orderSort, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderSort 实例对象
     * @return 实例对象
     */
    @Override
    public OrderSort insert(OrderSort orderSort) {
        this.orderSortDao.insert(orderSort);
        return orderSort;
    }

    /**
     * 修改数据
     *
     * @param orderSort 实例对象
     * @return 实例对象
     */
    @Override
    public OrderSort update(OrderSort orderSort) {
        this.orderSortDao.update(orderSort);
        return this.queryById(orderSort.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderSortDao.deleteById(id) > 0;
    }
}
