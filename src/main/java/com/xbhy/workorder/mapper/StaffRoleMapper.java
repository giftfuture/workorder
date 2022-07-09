package com.xbhy.workorder.mapper;

import com.xbhy.workorder.entity.StaffRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户和角色关联表(StaffRole)表数据库访问层
 *
 * @author 
 * @since 2022-06-28 10:37:43
 */

public interface StaffRoleMapper extends BaseMapper<StaffRole>{

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffRole queryById(@Param("Id") Long id);

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    List<StaffRole>  queryByStaffId(@Param("staffId") Long staffId);

    /**
     * 查询指定行数据
     *
     * @param staffRole 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<StaffRole> queryAllByLimit(StaffRole staffRole, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param staffRole 查询条件
     * @return 总行数
     */
    long count(StaffRole staffRole);

    /**
     * 新增数据
     *
     * @param staffRole 实例对象
     * @return 影响行数
     */
    int insert(StaffRole staffRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<StaffRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<StaffRole> entities);

    /**
     * 修改数据
     *
     * @param staffRole 实例对象
     * @return 影响行数
     */
    int update(StaffRole staffRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

