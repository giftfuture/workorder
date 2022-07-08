package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.dao.OrderDictDao;
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
    private OrderDictDao orderDictDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderDictVO queryById(Long id) {
        return BeanUtil.copyProperties(this.orderDictDao.queryById(id),OrderDictVO.class);
    }

    /**
     *
     * @return
     */
    @Override
    public List<OrderDictVO> loadOrderDict(){
       return BeanUtil.copyToList(orderDictDao.loadOrderDict(),OrderDictVO.class);
    }

    @Override
    public OrderDictVO loadOrderDictBySort(String sortTag) {
        return orderDictDao.loadOrderDictBySort(sortTag);
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
        long total = this.orderDictDao.count(orderDict);
        return new PageImpl<>(BeanUtil.copyToList(this.orderDictDao.queryAllByLimit(orderDict, pageRequest),OrderDictVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderDictVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderDictVO insert(OrderDictVO orderDictVO) {
        this.orderDictDao.insert(BeanUtil.copyProperties(orderDictVO,OrderDict.class));
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
        this.orderDictDao.update(BeanUtil.copyProperties(orderDictVO,OrderDict.class));
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
        return this.orderDictDao.deleteById(id) > 0;
    }
}
