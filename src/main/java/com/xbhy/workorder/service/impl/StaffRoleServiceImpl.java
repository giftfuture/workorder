package com.xbhy.workorder.service.impl;

import com.xbhy.workorder.entity.StaffRole;
import com.xbhy.workorder.dao.StaffRoleDao;
import com.xbhy.workorder.service.StaffRoleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户和角色关联表(StaffRole)表服务实现类
 *
 * @author makejava
 * @since 2022-06-28 09:52:06
 */
@Service("staffRoleService")
public class StaffRoleServiceImpl implements StaffRoleService {
    @Resource
    private StaffRoleDao staffRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StaffRole queryById(Long id) {
        return this.staffRoleDao.queryById(id);
    }

    @Override
    public List<StaffRole> queryByStaffId(Long staffId) {
        return staffRoleDao.queryByStaffId(staffId);
    }

    /**
     * 分页查询
     *
     * @param staffRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<StaffRole> queryByPage(StaffRole staffRole, PageRequest pageRequest) {
        long total = this.staffRoleDao.count(staffRole);
        return new PageImpl<>(this.staffRoleDao.queryAllByLimit(staffRole, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staffRole 实例对象
     * @return 实例对象
     */
    @Override
    public StaffRole insert(StaffRole staffRole) {
        this.staffRoleDao.insert(staffRole);
        return staffRole;
    }

    /**
     * 修改数据
     *
     * @param staffRole 实例对象
     * @return 实例对象
     */
    @Override
    public StaffRole update(StaffRole staffRole) {
        this.staffRoleDao.update(staffRole);
        return this.queryById(staffRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.staffRoleDao.deleteById(id) > 0;
    }
}
