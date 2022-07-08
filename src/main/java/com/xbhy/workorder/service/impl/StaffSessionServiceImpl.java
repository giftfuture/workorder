package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.StaffSession;
import com.xbhy.workorder.dao.StaffSessionDao;
import com.xbhy.workorder.service.StaffSessionService;
import com.xbhy.workorder.vo.StaffSessionVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 在线用户记录(StaffSession)表服务实现类
 *
 * @author makejava
 * @since 2022-07-06 00:23:59
 */
@Service("staffSessionService")
public class StaffSessionServiceImpl implements StaffSessionService {
    @Resource
    private StaffSessionDao staffSessionDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StaffSessionVO queryById(Integer id) {
        return BeanUtil.copyProperties(this.staffSessionDao.queryById(id),StaffSessionVO.class);
    }

    /**
     * 通过SessionID查询
     * @param sessionId
     * @return
     */
    @Override
    public StaffSessionVO queryBySessionId(String sessionId) {
        return BeanUtil.copyProperties(this.staffSessionDao.queryBySessionId(sessionId),StaffSessionVO.class);
    }

    @Override
    public List<StaffSessionVO> selectOnlineByExpired(Date expiredDate) {
        return BeanUtil.copyToList(this.staffSessionDao.selectOnlineByExpired(expiredDate),StaffSessionVO.class);
    }

    @Override
    public Integer batchDeleteOnline(List<String> sessionIds) {
        return this.staffSessionDao.batchDeleteSession(sessionIds);
    }

    /**
     * 分页查询
     *
     * @param staffSessionVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<StaffSessionVO> queryByPage(StaffSessionVO staffSessionVO, PageRequest pageRequest) {
        StaffSession staffSession = BeanUtil.copyProperties(staffSessionVO,StaffSession.class);
        long total = this.staffSessionDao.count(staffSession);
        return new PageImpl<>(BeanUtil.copyToList(this.staffSessionDao.queryAllByLimit(staffSession, pageRequest),StaffSessionVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staffSessionVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffSessionVO insert(StaffSessionVO staffSessionVO) {
        this.staffSessionDao.insert(BeanUtil.copyProperties(staffSessionVO,StaffSession.class));
        return staffSessionVO;
    }

    /**
     * 修改数据
     *
     * @param staffSessionVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffSessionVO update(StaffSessionVO staffSessionVO) {
        this.staffSessionDao.update(BeanUtil.copyProperties(staffSessionVO,StaffSession.class));
        return this.queryById(staffSessionVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.staffSessionDao.deleteById(id) > 0;
    }
}
