package com.xbhy.workorder.dao;

import com.xbhy.workorder.entity.StaffSession;
import com.xbhy.workorder.vo.StaffSessionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * 在线用户记录(StaffSession)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-06 00:23:59
 */
@Mapper
public interface StaffSessionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffSession queryById(Integer id);


    /**
     * 通过SessionID查询
     * @param sessionId
     * @return
     */
    StaffSession queryBySessionId(String sessionId);


    /**
     * 获取某个时刻过期的Session
     * @param expiredDate
     * @return
     */
    List<StaffSession> selectOnlineByExpired(Date expiredDate);

    /**
     * 批量删除过期Session
     * @param sessionIds
     * @return
     */
    Integer batchDeleteSession(@Param("sessionIds")List<String> sessionIds);

    /**
     * 查询指定行数据
     *
     * @param staffSession 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<StaffSession> queryAllByLimit(StaffSession staffSession, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param staffSession 查询条件
     * @return 总行数
     */
    long count(StaffSession staffSession);

    /**
     * 新增数据
     *
     * @param staffSession 实例对象
     * @return 影响行数
     */
    int insert(StaffSession staffSession);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffSession> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<StaffSession> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffSession> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<StaffSession> entities);

    /**
     * 修改数据
     *
     * @param staffSession 实例对象
     * @return 影响行数
     */
    int update(StaffSession staffSession);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

