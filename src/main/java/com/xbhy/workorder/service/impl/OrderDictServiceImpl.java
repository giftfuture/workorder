package com.xbhy.workorder.service.impl;

import com.xbhy.workorder.dao.OrderDictDao;
import com.xbhy.workorder.service.OrderDictService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (OrderDict)表服务实现类
 *
 * @author makejava
 * @since 2022-06-28 09:44:41
 */
@Service("orderDictService")
public class OrderDictServiceImpl implements OrderDictService {
    @Resource
    private OrderDictDao orderDictDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderDict queryById(Long id) {
        return this.orderDictDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param orderDict 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderDict> queryByPage(OrderDict orderDict, PageRequest pageRequest) {
        long total = this.orderDictDao.count(orderDict);
        return new PageImpl<>(this.orderDictDao.queryAllByLimit(orderDict, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderDict 实例对象
     * @return 实例对象
     */
    @Override
    public OrderDict insert(OrderDict orderDict) {
        this.orderDictDao.insert(orderDict);
        return orderDict;
    }

    /**
     * 修改数据
     *
     * @param orderDict 实例对象
     * @return 实例对象
     */
    @Override
    public OrderDict update(OrderDict orderDict) {
        this.orderDictDao.update(orderDict);
        return this.queryById(orderDict.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderDictDao.deleteById(id) > 0;
    }
}
