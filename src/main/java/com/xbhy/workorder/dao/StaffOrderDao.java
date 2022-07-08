package com.xbhy.workorder.dao;

import com.xbhy.workorder.entity.StaffOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (StaffOrder)表数据库访问层
 *
 * @author 
 * @since 2022-06-28 10:37:41
 */
@Mapper
public interface StaffOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffOrder queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param staffOrder 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<StaffOrder> queryAllByLimit(StaffOrder staffOrder, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param staffOrder 查询条件
     * @return 总行数
     */
    long count(StaffOrder staffOrder);

    /**
     * 新增数据
     *
     * @param staffOrder 实例对象
     * @return 影响行数
     */
    int insert(StaffOrder staffOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<StaffOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffOrder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<StaffOrder> entities);

    /**
     * 修改数据
     *
     * @param staffOrder 实例对象
     * @return 影响行数
     */
    int update(StaffOrder staffOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

