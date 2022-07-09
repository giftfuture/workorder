package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.SysPermission;
import com.xbhy.workorder.vo.SysPermissionVO;
import com.xbhy.workorder.vo.SysRolePermissionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (SysPermission)表服务接口
 *
 * @author makejava
 * @since 2022-07-05 21:34:12
 */
public interface SysPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermissionVO queryById(Integer id);
    /**
     * 通过角色获取权限
     * @param roleId
     * @return
     */
    List<SysPermissionVO> queryByRoleId(Integer roleId);
    /**
     * 分页查询
     *
     * @param sysPermissionVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<SysPermissionVO> queryByPage(SysPermissionVO sysPermissionVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysPermissionVO 实例对象
     * @return 实例对象
     */
    SysPermissionVO insert(SysPermissionVO sysPermissionVO);

    /**
     * 修改数据
     *
     * @param sysPermissionVO 实例对象
     * @return 实例对象
     */
    SysPermissionVO update(SysPermissionVO sysPermissionVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
