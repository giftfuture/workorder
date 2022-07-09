package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.OrderLog;
import com.xbhy.workorder.mapper.OrderLogMapper;
import com.xbhy.workorder.service.OrderLogService;
import com.xbhy.workorder.vo.OrderLogVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (OrderLog)表服务实现类
 *
 * @author 
 * @since 2022-06-27 13:37:42
 */
@Service("orderLogService")
public class OrderLogServiceImpl implements OrderLogService {
    @Resource
    private OrderLogMapper orderLogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderLogVO queryById(Long id) {
        return BeanUtil.copyProperties(this.orderLogMapper.queryById(id),OrderLogVO.class);
    }

    /**
     * 获取工单操作历史
     * @param orderId
     * @return
     */
    @Override
    public List<OrderLogVO> queryByOrderId(Long orderId) {
        return BeanUtil.copyToList(orderLogMapper.queryByOrderId(orderId),OrderLogVO.class);
    }

    /**
     * 分页查询
     *
     * @param orderLogVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderLogVO> queryByPage(OrderLogVO orderLogVO, PageRequest pageRequest) {
        OrderLog orderLog = BeanUtil.copyProperties(orderLogVO, OrderLog.class);
        long total = this.orderLogMapper.count(orderLog);
        return new PageImpl<>(BeanUtil.copyToList(this.orderLogMapper.queryAllByLimit(orderLog, pageRequest),OrderLogVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderLogVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderLogVO insert(OrderLogVO orderLogVO) {
        this.orderLogMapper.insert(BeanUtil.copyProperties(orderLogVO,OrderLog.class));
        return orderLogVO;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderLogMapper.deleteById(id) > 0;
    }
}
