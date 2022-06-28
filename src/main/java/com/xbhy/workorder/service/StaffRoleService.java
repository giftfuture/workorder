package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.StaffRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 用户和角色关联表(StaffRole)表服务接口
 *
 * @author makejava
 * @since 2022-06-28 09:52:06
 */
public interface StaffRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffRole queryById(Long id);
    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    List<StaffRole> queryByStaffId(Long staffId);

    /**
     * 分页查询
     *
     * @param staffRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<StaffRole> queryByPage(StaffRole staffRole, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param staffRole 实例对象
     * @return 实例对象
     */
    StaffRole insert(StaffRole staffRole);

    /**
     * 修改数据
     *
     * @param staffRole 实例对象
     * @return 实例对象
     */
    StaffRole update(StaffRole staffRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
