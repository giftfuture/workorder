package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.vo.ResetPwdVO;
import com.xbhy.workorder.vo.StaffVO;
import com.xbhy.workorder.vo.UpdatePwdVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * (Staff)表服务接口
 *
 * @author 
 * @since 2022-06-26 22:40:41
 */
public interface StaffService {

    /**
     *  获取所有在职员工（后台用）
     *
     * @return 实例对象
     */
    List<Staff> fetchAll();
    /**
     *  获取所有在职员工（员工用）
     *
     * @return 实例对象
     */
    Map<Long,String> queryAll();
    /**
     *
     * @param resetPwdVO
     * @return
     */
    Boolean resetPwd(ResetPwdVO resetPwdVO);

    /**
     *
     * @param updatePwdVO
     * @return
     */
    Boolean updatePwd(UpdatePwdVO updatePwdVO);
    /**
     * 根据登录名查询
     * @param loginName
     * @return
     */
    StaffVO selectByLoginName(String loginName);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffVO queryById(Long id);

    /**
     * 分页查询
     *
     * @param staffVO       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<StaffVO> queryByPage(StaffVO staffVO, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param staffVO 实例对象
     * @return 实例对象
     */
    StaffVO insert(StaffVO staffVO);

    /**
     * 修改数据
     *
     * @param staffVO 实例对象
     * @return 实例对象
     */
    StaffVO update(StaffVO staffVO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
