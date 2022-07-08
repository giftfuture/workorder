package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.OrderTab;
import com.xbhy.workorder.dao.OrderTabDao;
import com.xbhy.workorder.service.OrderTabService;
import com.xbhy.workorder.vo.OrderSortVO;
import com.xbhy.workorder.vo.OrderTabVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public OrderTabVO queryById(Long id) {
        return BeanUtil.copyProperties(this.orderTabDao.queryById(id),OrderTabVO.class);
    }

    /**
     * 获取所有选项卡
     * @return
     */
    @Override
    public List<OrderTabVO> fetchAll() {
        return BeanUtil.copyToList(orderTabDao.fetchAll(),OrderTabVO.class);
    }

    /**
     * 分页查询
     *
     * @param orderTabVO    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderTabVO> queryByPage(OrderTabVO orderTabVO, PageRequest pageRequest) {
        OrderTab orderTab = BeanUtil.copyProperties(orderTabVO,OrderTab.class);
        long total = this.orderTabDao.count(orderTab);
        return new PageImpl<>(BeanUtil.copyToList(this.orderTabDao.queryAllByLimit(orderTab, pageRequest),OrderTabVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderTabVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderTabVO insert(OrderTabVO orderTabVO) {
        this.orderTabDao.insert(BeanUtil.copyProperties(orderTabVO,OrderTab.class));
        return orderTabVO;
    }

    /**
     * 修改数据
     *
     * @param orderTabVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderTabVO update(OrderTabVO orderTabVO) {
        this.orderTabDao.update(BeanUtil.copyProperties(orderTabVO,OrderTab.class));
        return this.queryById(orderTabVO.getId());
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
