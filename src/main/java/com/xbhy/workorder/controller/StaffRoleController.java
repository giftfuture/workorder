package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.StaffRole;
import com.xbhy.workorder.service.StaffRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户和角色关联表(StaffRole)表控制层
 *
 * @author makejava
 * @since 2022-06-28 09:52:05
 */
@RestController
@RequestMapping("/staffRole")
public class StaffRoleController {
    /**
     * 服务对象
     */
    @Resource
    private StaffRoleService staffRoleService;

    /**
     * 分页查询
     *
     * @param staffRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<StaffRole>> queryByPage(StaffRole staffRole, PageRequest pageRequest) {
        return ResponseEntity.ok(this.staffRoleService.queryByPage(staffRole, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<StaffRole> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.staffRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param staffRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<StaffRole> add(StaffRole staffRole) {
        return ResponseEntity.ok(this.staffRoleService.insert(staffRole));
    }

    /**
     * 编辑数据
     *
     * @param staffRole 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<StaffRole> edit(StaffRole staffRole) {
        return ResponseEntity.ok(this.staffRoleService.update(staffRole));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.staffRoleService.deleteById(id));
    }

}

