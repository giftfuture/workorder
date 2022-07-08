package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.SysPermission;
import com.xbhy.workorder.dao.SysPermissionDao;
import com.xbhy.workorder.service.SysPermissionService;
import com.xbhy.workorder.vo.SysPermissionVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (SysPermission)表服务实现类
 *
 * @author makejava
 * @since 2022-07-05 21:34:12
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionDao sysPermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysPermissionVO queryById(Integer id) {
        return BeanUtil.copyProperties(this.sysPermissionDao.queryById(id),SysPermissionVO.class);
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
        long total = this.sysPermissionDao.count(sysPermission);
        return new PageImpl<>(BeanUtil.copyToList(this.sysPermissionDao.queryAllByLimit(sysPermission, pageRequest),SysPermissionVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysPermissionVO 实例对象
     * @return 实例对象
     */
    @Override
    public SysPermissionVO insert(SysPermissionVO sysPermissionVO) {
        this.sysPermissionDao.insert(BeanUtil.copyProperties(sysPermissionVO,SysPermission.class));
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
        this.sysPermissionDao.update(BeanUtil.copyProperties(sysPermissionVO,SysPermission.class));
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
        return this.sysPermissionDao.deleteById(id) > 0;
    }
}
