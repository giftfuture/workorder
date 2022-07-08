package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.dao.StaffDao;
import com.xbhy.workorder.service.StaffService;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.ResetPwdVO;
import com.xbhy.workorder.vo.StaffVO;
import com.xbhy.workorder.vo.UpdatePwdVO;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (Staff)表服务实现类
 *
 * @author 
 * @since 2022-06-26 22:40:41
 */
@Slf4j
@Primary
@Transactional
@Service("staffService")
public class StaffServiceImpl implements StaffService {
    @Resource
    private StaffDao staffDao;

    @Override
    public List<Staff> fetchAll() {
        return staffDao.fetchAll();
    }

    /**
     *
     * @return
     */
    @Override
    public Map<Long,String> queryAll() {
        return staffDao.queryAll();
    }

    /**
     * 重置密码
     *
     * @param resetPwdVO 实体对象
     * @return
     */
    @Override
    public Boolean resetPwd(ResetPwdVO resetPwdVO) {
        Staff staff = Staff.builder().passwd(BCrypt.hashpw(resetPwdVO.getNewPwd(), BCrypt.gensalt())).loginName(resetPwdVO.getLoginName()).updateBy(ShiroUtils.getStaffId()).build();
        return staffDao.updatePwd(staff) > 0;
    }

    /**
     * 更新密码
     * @param updatePwdVO
     * @return
     */
    @Override
    public Boolean updatePwd(UpdatePwdVO updatePwdVO) {
        Staff staff = Staff.builder().passwd(BCrypt.hashpw(updatePwdVO.getNewPwd(), BCrypt.gensalt())).loginName(updatePwdVO.getLoginName()).updateBy(ShiroUtils.getStaffId()).build();
        return staffDao.updatePwd(staff) > 0;
    }

    /**
     * 逻辑批量删除
     *
     * @param idList
     * @return
     */
//    @Override
//    public Boolean logicDeleteBatch(List<Long> idList) {
//        if (Optional.ofNullable(idList).isEmpty()) {
//            return false;
//        }
//        return SqlHelper.retBool(getBaseMapper().deleteBatchIds(idList));
//    }


    @Override
    public StaffVO selectByLoginName(String loginName) {
        return BeanUtil.copyProperties(staffDao.selectByLoginName(loginName),StaffVO.class);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StaffVO queryById(Long id) {
        return BeanUtil.copyProperties(this.staffDao.queryById(id),StaffVO.class);
    }

    /**
     * 分页查询
     *
     * @param staffVO       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<StaffVO> queryByPage(StaffVO staffVO, PageRequest pageRequest) {
        Staff staff = BeanUtil.copyProperties(staffVO,Staff.class);
        long total = this.staffDao.count(staff);
        return new PageImpl<>(BeanUtil.copyToList(this.staffDao.queryAllByLimit(staff, pageRequest),StaffVO.class), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staffVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffVO insert(StaffVO staffVO) {
        this.staffDao.insert(BeanUtil.copyProperties(staffVO,Staff.class));
        return staffVO;
    }

    /**
     * 修改数据
     *
     * @param staffVO 实例对象
     * @return 实例对象
     */
    @Override
    public StaffVO update(StaffVO staffVO) {
        this.staffDao.update(BeanUtil.copyProperties(staffVO,Staff.class));
        return this.queryById(staffVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.staffDao.deleteById(id) > 0;
    }
}
