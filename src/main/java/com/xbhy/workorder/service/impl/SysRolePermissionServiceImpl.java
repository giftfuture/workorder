package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.SysRolePermission;
import com.xbhy.workorder.mapper.SysRolePermissionMapper;
import com.xbhy.workorder.service.SysRolePermissionService;
import com.xbhy.workorder.vo.SysRolePermissionVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (SysRolePermission)表服务实现类
 *
 * @author
 * @since 2022-07-05 21:34:12
 */
@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRolePermissionVO queryById(Integer id) {
        return BeanUtil.copyProperties(this.sysRolePermissionMapper.queryById(id),SysRolePermissionVO.class);
    }

    /**
     * 分页查询
     *
     * @param sysRolePermissionVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<SysRolePermissionVO> queryByPage(SysRolePermissionVO sysRolePermissionVO, PageRequest pageRequest) {
        SysRolePermission sysRolePermission = BeanUtil.copyProperties(sysRolePermissionVO,SysRolePermission.class);
        long total = this.sysRolePermissionMapper.count(sysRolePermission);
        return new PageImpl<>(BeanUtil.copyToList(this.sysRolePermissionMapper.queryAllByLimit(sysRolePermission, pageRequest),SysRolePermissionVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysRolePermissionVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolePermissionVO insert(SysRolePermissionVO sysRolePermissionVO) {
        this.sysRolePermissionMapper.insert(BeanUtil.copyProperties(sysRolePermissionVO,SysRolePermission.class));
        return sysRolePermissionVO;
    }

    /**
     * 修改数据
     *
     * @param sysRolePermissionVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolePermissionVO update(SysRolePermissionVO sysRolePermissionVO) {
        this.sysRolePermissionMapper.update(BeanUtil.copyProperties(sysRolePermissionVO,SysRolePermission.class));
        return this.queryById(sysRolePermissionVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysRolePermissionMapper.deleteById(id) > 0;
    }
}
