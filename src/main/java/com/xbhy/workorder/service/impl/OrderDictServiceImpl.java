package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.mapper.OrderDictMapper;
import com.xbhy.workorder.entity.OrderDict;
import com.xbhy.workorder.service.OrderDictService;
import com.xbhy.workorder.vo.OrderDictVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (OrderDict)表服务实现类
 *
 * @author 
 * @since 2022-06-28 09:44:41
 */
@Service("orderDictService")
public class OrderDictServiceImpl implements OrderDictService {
    @Resource
    private OrderDictMapper orderDictMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderDictVO queryById(Long id) {
        return BeanUtil.copyProperties(this.orderDictMapper.queryById(id),OrderDictVO.class);
    }

    /**
     *
     * @return
     */
    @Override
    public List<OrderDictVO> loadOrderDict(){
       return BeanUtil.copyToList(orderDictMapper.loadOrderDict(),OrderDictVO.class);
    }

    @Override
    public OrderDictVO loadOrderDictBySort(String sortTag) {
        return orderDictMapper.loadOrderDictBySort(sortTag);
    }

    /**
     * 分页查询
     *
     * @param orderDictVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderDictVO> queryByPage(OrderDictVO orderDictVO, PageRequest pageRequest) {
        OrderDict orderDict = BeanUtil.copyProperties(orderDictVO,OrderDict.class);
        long total = this.orderDictMapper.count(orderDict);
        return new PageImpl<>(BeanUtil.copyToList(this.orderDictMapper.queryAllByLimit(orderDict, pageRequest),OrderDictVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderDictVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderDictVO insert(OrderDictVO orderDictVO) {
        this.orderDictMapper.insert(BeanUtil.copyProperties(orderDictVO,OrderDict.class));
        return orderDictVO;
    }

    /**
     * 修改数据
     *
     * @param orderDictVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderDictVO update(OrderDictVO orderDictVO) {
        this.orderDictMapper.update(BeanUtil.copyProperties(orderDictVO,OrderDict.class));
        return this.queryById(orderDictVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderDictMapper.deleteById(id) > 0;
    }
}
