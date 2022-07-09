package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.OrderSort;
import com.xbhy.workorder.mapper.OrderSortMapper;
import com.xbhy.workorder.service.OrderSortService;
import com.xbhy.workorder.vo.OrderSortVO;
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
    private OrderSortMapper orderSortMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderSortVO queryById(Long id) {
        return BeanUtil.copyProperties(this.orderSortMapper.queryById(id),OrderSortVO.class);
    }

    /**
     * 分页查询
     *
     * @param orderSortVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderSortVO> queryByPage(OrderSortVO orderSortVO, PageRequest pageRequest) {
        OrderSort orderSort = BeanUtil.copyProperties(orderSortVO,OrderSort.class);
        long total = this.orderSortMapper.count(orderSort);
        return new PageImpl<>(BeanUtil.copyToList(this.orderSortMapper.queryAllByLimit(orderSort, pageRequest),OrderSortVO.class), pageRequest, total);
    }

    /**
     * 获取所有工单类别
     * @return
     */
    @Override
    public List<OrderSortVO> fetchAll() {
        return BeanUtil.copyToList(orderSortMapper.fetchAll(),OrderSortVO.class);
    }

    /**
     * 新增数据
     *
     * @param orderSortVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderSortVO insert(OrderSortVO orderSortVO) {
        this.orderSortMapper.insert(BeanUtil.copyProperties(orderSortVO,OrderSort.class));
        return orderSortVO;
    }

    /**
     * 修改数据
     *
     * @param orderSortVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderSortVO update(OrderSortVO orderSortVO) {
        this.orderSortMapper.update(BeanUtil.copyProperties(orderSortVO,OrderSort.class));
        return this.queryById(orderSortVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderSortMapper.deleteById(id) > 0;
    }
}
