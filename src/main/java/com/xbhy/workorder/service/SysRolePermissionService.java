package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.SysRolePermission;
import com.xbhy.workorder.vo.SysRolePermissionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (SysRolePermission)表服务接口
 *
 * @author makejava
 * @since 2022-07-05 21:34:12
 */
public interface SysRolePermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRolePermissionVO queryById(Integer id);

   
    /**
     * 分页查询
     *
     * @param sysRolePermissionVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<SysRolePermissionVO> queryByPage(SysRolePermissionVO sysRolePermissionVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysRolePermissionVO 实例对象
     * @return 实例对象
     */
    SysRolePermissionVO insert(SysRolePermissionVO sysRolePermissionVO);

    /**
     * 修改数据
     *
     * @param sysRolePermissionVO 实例对象
     * @return 实例对象
     */
    SysRolePermissionVO update(SysRolePermissionVO sysRolePermissionVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
