package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.xbhy.workorder.entity.FileInfo;
import com.xbhy.workorder.dao.FileInfoDao;
import com.xbhy.workorder.service.FileInfoService;
import com.xbhy.workorder.vo.FileInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 本地存储(FileInfo)表服务实现类
 *
 * @author 
 * @since 2022-07-05 19:22:51
 */
@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoDao fileInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public FileInfoVO queryById(Long id) {
        FileInfo fileInfo = this.fileInfoDao.queryById(id);
        FileInfoVO fileInfoVO = BeanUtil.copyProperties(fileInfo,FileInfoVO.class);
        return fileInfoVO;
    }

    /**
     * 分页查询
     *
     * @param fileInfoVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<FileInfoVO> queryByPage(FileInfoVO fileInfoVO, PageRequest pageRequest) {
        FileInfo fileInfo = BeanUtil.copyProperties(fileInfoVO,FileInfo.class);
        long total = this.fileInfoDao.count(fileInfo);
        List<FileInfo> list = this.fileInfoDao.queryAllByLimit(fileInfo, pageRequest);
        List<FileInfoVO> fileInfoVOList = BeanUtil.copyToList(list,FileInfoVO.class);
        return new PageImpl<>(fileInfoVOList, pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param fileInfoVO 实例对象
     * @return 实例对象
     */
    @Override
    public FileInfoVO insert(FileInfoVO fileInfoVO) {
        FileInfo fileInfo = BeanUtil.copyProperties(fileInfoVO,FileInfo.class);
        this.fileInfoDao.insert(fileInfo);
        return fileInfoVO;
    }

    /**
     * 修改数据
     *
     * @param fileInfoVO 实例对象
     * @return 实例对象
     */
    @Override
    public FileInfoVO update(FileInfoVO fileInfoVO) {
        FileInfo fileInfo = BeanUtil.copyProperties(fileInfoVO,FileInfo.class);
        this.fileInfoDao.update(fileInfo);
        return this.queryById(fileInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.fileInfoDao.deleteById(id) > 0;
    }
}
