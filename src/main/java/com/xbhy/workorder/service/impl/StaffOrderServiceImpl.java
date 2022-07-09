package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.StaffOrder;
import com.xbhy.workorder.mapper.StaffOrderMapper;
import com.xbhy.workorder.service.StaffOrderService;
import com.xbhy.workorder.vo.StaffOrderVO;
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
    private StaffOrderMapper staffOrderMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StaffOrderVO queryById(Long id) {
        return BeanUtil.copyProperties(this.staffOrderMapper.queryById(id),StaffOrderVO.class);
    }

    /**
     * 分页查询
     *
     * @param staffOrderVO  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<StaffOrderVO> queryByPage(StaffOrderVO staffOrderVO, PageRequest pageRequest) {
        StaffOrder staffOrder = BeanUtil.copyProperties(staffOrderVO,StaffOrder.class);
        long total = this.staffOrderMapper.count(staffOrder);
        return new PageImpl<>(BeanUtil.copyToList(this.staffOrderMapper.queryAllByLimit(staffOrder, pageRequest),StaffOrderVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staffOrderVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffOrderVO insert(StaffOrderVO staffOrderVO) {
        this.staffOrderMapper.insert(BeanUtil.copyProperties(staffOrderVO,StaffOrder.class));
        return staffOrderVO;
    }

    /**
     * 修改数据
     *
     * @param staffOrderVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffOrderVO update(StaffOrderVO staffOrderVO) {
        this.staffOrderMapper.update(BeanUtil.copyProperties(staffOrderVO, StaffOrder.class));
        return this.queryById(staffOrderVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.staffOrderMapper.deleteById(id) > 0;
    }
}
