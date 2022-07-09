package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.SysPermission;
import com.xbhy.workorder.mapper.SysPermissionMapper;
import com.xbhy.workorder.service.SysPermissionService;
import com.xbhy.workorder.vo.SysPermissionVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysPermission)表服务实现类
 *
 * @author makejava
 * @since 2022-07-05 21:34:12
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysPermissionVO queryById(Integer id) {
        return BeanUtil.copyProperties(this.sysPermissionMapper.queryById(id),SysPermissionVO.class);
    }

    /**
     * 通过角色获取权限
     * @param roleId
     * @return
     */
    @Override
    public List<SysPermissionVO> queryByRoleId(Integer roleId) {
        return BeanUtil.copyToList(this.sysPermissionMapper.queryByRoleId(roleId),SysPermissionVO.class);
    }

    /**
     * 分页查询
     *
     * @param sysPermissionVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<SysPermissionVO> queryByPage(SysPermissionVO sysPermissionVO, PageRequest pageRequest) {
        SysPermission sysPermission = BeanUtil.copyProperties(sysPermissionVO, SysPermission.class);
        long total = this.sysPermissionMapper.count(sysPermission);
        return new PageImpl<>(BeanUtil.copyToList(this.sysPermissionMapper.queryAllByLimit(sysPermission, pageRequest),SysPermissionVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysPermissionVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysPermissionVO insert(SysPermissionVO sysPermissionVO) {
        this.sysPermissionMapper.insert(BeanUtil.copyProperties(sysPermissionVO,SysPermission.class));
        return sysPermissionVO;
    }

    /**
     * 修改数据
     *
     * @param sysPermissionVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysPermissionVO update(SysPermissionVO sysPermissionVO) {
        this.sysPermissionMapper.update(BeanUtil.copyProperties(sysPermissionVO,SysPermission.class));
        return this.queryById(sysPermissionVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysPermissionMapper.deleteById(id) > 0;
    }
}
