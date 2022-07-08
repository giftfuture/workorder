package com.xbhy.workorder.rest;

import com.xbhy.workorder.service.StaffRoleService;
import com.xbhy.workorder.service.SysRoleService;
import com.xbhy.workorder.vo.ResponseVO;
import com.xbhy.workorder.vo.SysRoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色信息表(SysRole)表控制层
 *
 * @author 
 * @since 2022-06-28 09:42:32
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private StaffRoleService staffRoleService;

    /**
     * 分页查询
     *
     * @param sysRoleVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping("/page")
    public ResponseVO<Page<SysRoleVO>> queryByPage(SysRoleVO sysRoleVO, PageRequest pageRequest) {
        return ResponseVO.success(this.sysRoleService.queryByPage(sysRoleVO, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public ResponseVO<SysRoleVO> queryById(@PathVariable("id") Integer id) {
        return ResponseVO.success(this.sysRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoleVO 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseVO<SysRoleVO> add(SysRoleVO sysRoleVO) {
        return ResponseVO.success(this.sysRoleService.insert(sysRoleVO));
    }

    /**
     * 编辑数据
     *
     * @param sysRoleVO 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ResponseVO<SysRoleVO> edit(SysRoleVO sysRoleVO) {
        return ResponseVO.success(this.sysRoleService.update(sysRoleVO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/del")
    public ResponseVO<Boolean> deleteById(Integer id) {
        return ResponseVO.success(this.sysRoleService.deleteById(id));
    }

}

