package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.SysRole;
import com.xbhy.workorder.mapper.SysRoleMapper;
import com.xbhy.workorder.service.SysRoleService;
import com.xbhy.workorder.vo.SysRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author 
 * @since 2022-06-28 09:42:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRoleVO queryById(Integer roleId) {
        return BeanUtil.copyProperties(this.sysRoleMapper.queryById(roleId),SysRoleVO.class);
    }

    /**
     * 分页查询
     *
     * @param sysRoleVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<SysRoleVO> queryByPage(SysRoleVO sysRoleVO, PageRequest pageRequest) {
        SysRole sysRole = BeanUtil.copyProperties(sysRoleVO,SysRole.class);
        long total = this.sysRoleMapper.count(sysRole);
        return new PageImpl<>(BeanUtil.copyToList(this.sysRoleMapper.queryAllByLimit(sysRole, pageRequest),SysRoleVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysRoleVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoleVO insert(SysRoleVO sysRoleVO) {
        this.sysRoleMapper.insert(BeanUtil.copyProperties(sysRoleVO,SysRole.class));
        return sysRoleVO;
    }

    /**
     * 修改数据
     *
     * @param sysRoleVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoleVO update(SysRoleVO sysRoleVO) {
        this.sysRoleMapper.update(BeanUtil.copyProperties(sysRoleVO,SysRole.class));
        return this.queryById(sysRoleVO.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer roleId) {
        return this.sysRoleMapper.deleteById(roleId) > 0;
    }
}
