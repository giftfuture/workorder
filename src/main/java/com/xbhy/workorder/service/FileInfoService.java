package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.FileInfo;
import com.xbhy.workorder.vo.FileInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 本地存储(FileInfo)表服务接口
 *
 * @author 
 * @since 2022-07-05 19:22:50
 */
public interface FileInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FileInfoVO queryById(Long id);

    /**
     * 分页查询
     *
     * @param fileInfoVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<FileInfoVO> queryByPage(FileInfoVO fileInfoVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    FileInfoVO insert(FileInfoVO fileInfoVO);

    /**
     * 修改数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    FileInfoVO update(FileInfoVO fileInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
