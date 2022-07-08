package com.xbhy.workorder.dao;

import com.xbhy.workorder.entity.OrderDict;
import com.xbhy.workorder.vo.OrderDictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (OrderDict)表数据库访问层
 *
 * @author 
 * @since 2022-06-28 10:37:29
 */
@Mapper
public interface OrderDictDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderDict queryById(Long id);

    /**
     * 工单字典服务
     * @return
     */
    List<OrderDictVO> loadOrderDict();

    /**
     * 工单字典服务
     * @return
     */
    OrderDictVO loadOrderDictBySort(String sortTag);
    /**
     * 查询指定行数据
     *
     * @param orderDict 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<OrderDict> queryAllByLimit(OrderDict orderDict, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param orderDict 查询条件
     * @return 总行数
     */
    long count(OrderDict orderDict);

    /**
     * 新增数据
     *
     * @param orderDict 实例对象
     * @return 影响行数
     */
    int insert(OrderDict orderDict);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OrderDict> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OrderDict> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OrderDict> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OrderDict> entities);

    /**
     * 修改数据
     *
     * @param orderDict 实例对象
     * @return 影响行数
     */
    int update(OrderDict orderDict);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

