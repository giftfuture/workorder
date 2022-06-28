package com.xbhy.workorder.dao;

import com.xbhy.workorder.entity.OrderLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (OrderLog)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-28 10:37:34
 */
public interface OrderLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param orderLog 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<OrderLog> queryAllByLimit(OrderLog orderLog, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param orderLog 查询条件
     * @return 总行数
     */
    long count(OrderLog orderLog);

    /**
     * 新增数据
     *
     * @param orderLog 实例对象
     * @return 影响行数
     */
    int insert(OrderLog orderLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OrderLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OrderLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OrderLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OrderLog> entities);

    /**
     * 修改数据
     *
     * @param orderLog 实例对象
     * @return 影响行数
     */
    int update(OrderLog orderLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

