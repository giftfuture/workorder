package com.xbhy.workorder.dao;

import com.xbhy.workorder.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 本地存储(FileInfo)表数据库访问层
 *
 * @author 
 * @since 2022-07-05 19:22:47
 */
@Mapper
public interface FileInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FileInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param fileInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<FileInfo> queryAllByLimit(FileInfo fileInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param fileInfo 查询条件
     * @return 总行数
     */
    long count(FileInfo fileInfo);

    /**
     * 新增数据
     *
     * @param fileInfo 实例对象
     * @return 影响行数
     */
    int insert(FileInfo fileInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<FileInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<FileInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<FileInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<FileInfo> entities);

    /**
     * 修改数据
     *
     * @param fileInfo 实例对象
     * @return 影响行数
     */
    int update(FileInfo fileInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

