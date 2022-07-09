package com.xbhy.workorder.mapper;

import com.xbhy.workorder.entity.OrderTab;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (OrderTab)表数据库访问层
 *
 * @author 
 * @since 2022-06-28 10:37:37
 */

public interface OrderTabMapper extends BaseMapper<OrderTab>{

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderTab queryById(Long id);

    /**
     * 获取所有选项卡
     * @return
     */
    List<OrderTab> fetchAll();
    /**
     * 查询指定行数据
     *
     * @param orderTab 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<OrderTab> queryAllByLimit(OrderTab orderTab, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param orderTab 查询条件
     * @return 总行数
     */
    long count(OrderTab orderTab);

    /**
     * 新增数据
     *
     * @param orderTab 实例对象
     * @return 影响行数
     */
    int insert(OrderTab orderTab);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OrderTab> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OrderTab> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OrderTab> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OrderTab> entities);

    /**
     * 修改数据
     *
     * @param orderTab 实例对象
     * @return 影响行数
     */
    int update(OrderTab orderTab);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

