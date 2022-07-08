package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.StaffRole;
import com.xbhy.workorder.dao.StaffRoleDao;
import com.xbhy.workorder.service.StaffRoleService;
import com.xbhy.workorder.vo.StaffRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户和角色关联表(StaffRole)表服务实现类
 *
 * @author 
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
    public StaffRoleVO queryById(Long id) {
        return BeanUtil.copyProperties(this.staffRoleDao.queryById(id),StaffRoleVO.class);
    }

    @Override
    public List<StaffRoleVO> queryByStaffId(Long staffId) {
        return BeanUtil.copyToList(staffRoleDao.queryByStaffId(staffId),StaffRoleVO.class);
    }

    /**
     * 分页查询
     *
     * @param staffRoleVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<StaffRoleVO> queryByPage(StaffRoleVO staffRoleVO, PageRequest pageRequest) {
        StaffRole staffRole = BeanUtil.copyProperties(staffRoleVO,StaffRole.class);
        long total = this.staffRoleDao.count(staffRole);
        return new PageImpl<>(BeanUtil.copyToList(this.staffRoleDao.queryAllByLimit(staffRole, pageRequest),StaffRoleVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staffRoleVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffRoleVO insert(StaffRoleVO staffRoleVO) {
        this.staffRoleDao.insert(BeanUtil.copyProperties(staffRoleVO,StaffRole.class));
        return staffRoleVO;
    }

    /**
     * 修改数据
     *
     * @param staffRoleVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffRoleVO update(StaffRoleVO staffRoleVO) {
        this.staffRoleDao.update(BeanUtil.copyProperties(staffRoleVO,StaffRole.class));
        return this.queryById(staffRoleVO.getId());
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
