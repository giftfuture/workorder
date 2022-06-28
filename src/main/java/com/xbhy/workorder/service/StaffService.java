package com.xbhy.workorder.service;

import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.vo.ResetPwdVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Staff)表服务接口
 *
 * @author 
 * @since 2022-06-26 22:40:41
 */
public interface StaffService {


    /**
     *
     * @param resetPwdVO
     * @return
     */
    Boolean resetPwd(ResetPwdVO resetPwdVO);

    /**
     * 根据登录名查询
     * @param loginName
     * @return
     */
    Staff selectByLoginName(String loginName);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Staff queryById(Long id);

    /**
     * 分页查询
     *
     * @param staff       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Staff> queryByPage(Staff staff, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    Staff insert(Staff staff);

    /**
     * 修改数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    Staff update(Staff staff);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
