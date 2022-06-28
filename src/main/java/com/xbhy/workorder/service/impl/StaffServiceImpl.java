package com.xbhy.workorder.service.impl;

import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.dao.StaffDao;
import com.xbhy.workorder.exception.CodeX;
import com.xbhy.workorder.service.StaffService;
import com.xbhy.workorder.vo.ResetPwdVO;
import com.xbhy.workorder.vo.UpdatePwdVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * 重置密码
     *
     * @param resetPwdVO 实体对象
     * @return
     */
    @Override
    public Boolean resetPwd(ResetPwdVO resetPwdVO) {
    Staff user = Staff.builder().passwd(resetPwdVO.getPaswd()).id(resetPwdVO.getId()).updateBy(resetPwdVO.getUpdateBy()).build();
        return null;// SqlHelper.retBool(getBaseMapper().updateById(user));
    }

    /**
     * 用户更新
     *
     * @param userBasicDTO 实体对象
     * @return 修改结果
     */
//    @Override
//    public UserBasicDTO update(UserBasicDTO userBasicDTO) {
//        try {
//            UserBasic userBasic = UserBasicDTOMapstruct.INSTANCE.toEntity(userBasicDTO);
//            userBasic.setPassword(null);
//            int result = userBasicDao.updateSelective(userBasic);
//            if (SqlHelper.retBool(result)) {
//                return UserBasicDTOMapstruct.INSTANCE.toDto(userBasic);
//            }
//        } catch (Exception e) {
//            UserCenterValid.validIndex(e);
//        }
//        return UserBasicDTO.builder().build();
//    }

    /**
     * 更新密码
     *
     * @param updatePwdVO 实体对象
     * @return
     */
//    @Override
//    public Boolean updatePwd(UpdatePwdVO updatePwdVO) {
//        UserBasic user = UserBasic.builder().password(updatePwdVO.getPassword()).id(updatePwdVO.getId()).updater(updatePwdVO.getUpdater()).build();
//        return SqlHelper.retBool(getBaseMapper().updateById(user));
//    }

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
    /**
     * 根据用户名、手机号、邮箱其中一个存在，判断用户存在
     *
     * @param userBasicDTO
     * @return
     */
//    @Override
//    public Boolean userExists(UserBasicDTO userBasicDTO) {
//        List<UserBasic> userBasicList = getBaseMapper().selectUser(UserBasicDTOMapstruct.INSTANCE.toEntity(userBasicDTO));
//        return userBasicList.size() > 0;
//    }


    /**
     * 校验用户是否存在
     */
//    private Staff checkExist(UserAddVO userAddVO) {
//        //根据 用户名 手机号 邮箱 查询用户是否存在
//        Staff query = new Staff();
//        query.setUserName(userAddVO.getUserName());
//        query.setMobile(userAddVO.getMobile());
//        query.setEmail(userAddVO.getEmail());
//        List<Staff> userList = getBaseMapper().selectUser(query);
//        if (CollectionUtils.isEmpty(userList)) {
//            return null;
//        }
//        if (userList.size() != 1) {
//            throw new UserCenterException(CommonResult.error(CodeX._60044));
//        }
//        Staff userBasic = userList.get(0);
//        //查询该用户 是否属于传入的client应用里面
//        QueryWrapper<UserClient> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userBasic.getId()).eq("client_id", clientId);
//        Integer count = userClientMapper.selectCount(queryWrapper);
//        if (count != 0) {
//            throw new UserCenterException(CommonResult.error(CodeX._60045));
//        }
//        return userBasic;
//    }

    @Override
    public Staff selectByLoginName(String loginName) {
        return staffDao.selectByLoginName(loginName);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Staff queryById(Long id) {
        return this.staffDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param staff       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Staff> queryByPage(Staff staff, PageRequest pageRequest) {
        long total = this.staffDao.count(staff);
        return new PageImpl<>(this.staffDao.queryAllByLimit(staff, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    @Override
    public Staff insert(Staff staff) {
        this.staffDao.insert(staff);
        return staff;
    }

    /**
     * 修改数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    @Override
    public Staff update(Staff staff) {
        this.staffDao.update(staff);
        return this.queryById(staff.getId());
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
