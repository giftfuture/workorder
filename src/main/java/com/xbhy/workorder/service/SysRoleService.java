package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.SysRole;
import com.xbhy.workorder.vo.SysRoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author 
 * @since 2022-06-28 09:42:32
 */
public interface SysRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRoleVO queryById(Integer roleId);

    /**
     * 分页查询
     *
     * @param sysRoleVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<SysRoleVO> queryByPage(SysRoleVO sysRoleVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysRoleVO 实例对象
     * @return 实例对象
     */
    SysRoleVO insert(SysRoleVO sysRoleVO);

    /**
     * 修改数据
     *
     * @param sysRoleVO 实例对象
     * @return 实例对象
     */
    SysRoleVO update(SysRoleVO sysRoleVO);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer roleId);

}
