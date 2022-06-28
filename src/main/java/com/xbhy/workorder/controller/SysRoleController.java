package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.SysRole;
import com.xbhy.workorder.service.SysRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色信息表(SysRole)表控制层
 *
 * @author makejava
 * @since 2022-06-28 09:42:32
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 分页查询
     *
     * @param sysRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<SysRole>> queryByPage(SysRole sysRole, PageRequest pageRequest) {
        return ResponseEntity.ok(this.sysRoleService.queryByPage(sysRole, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SysRole> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.sysRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<SysRole> add(SysRole sysRole) {
        return ResponseEntity.ok(this.sysRoleService.insert(sysRole));
    }

    /**
     * 编辑数据
     *
     * @param sysRole 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<SysRole> edit(SysRole sysRole) {
        return ResponseEntity.ok(this.sysRoleService.update(sysRole));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.sysRoleService.deleteById(id));
    }

}

