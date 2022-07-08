package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.StaffSession;
import com.xbhy.workorder.vo.StaffSessionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

/**
 * 在线用户记录(StaffSession)表服务接口
 *
 * @author makejava
 * @since 2022-07-06 00:23:59
 */
public interface StaffSessionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffSessionVO queryById(Integer id);

    /**
     * 通过SessionID查询
     * @param sessionId
     * @return
     */
    StaffSessionVO queryBySessionId(String  sessionId);

    /**
     * 获取某个时刻过期的Session
     * @param expiredDate
     * @return
     */
    List<StaffSessionVO> selectOnlineByExpired(Date expiredDate);

    /**
     * 批量删除过期session
     * @param sessionIds
     * @return
     */
    Integer batchDeleteOnline(List<String> sessionIds);
    /**
     * 分页查询
     *
     * @param staffSessionVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<StaffSessionVO> queryByPage(StaffSessionVO staffSessionVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param staffSessionVO 实例对象
     * @return 实例对象
     */
    StaffSessionVO insert(StaffSessionVO staffSessionVO);

    /**
     * 修改数据
     *
     * @param staffSessionVO 实例对象
     * @return 实例对象
     */
    StaffSessionVO update(StaffSessionVO staffSessionVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
