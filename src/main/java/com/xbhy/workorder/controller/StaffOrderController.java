package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.StaffOrder;
import com.xbhy.workorder.service.StaffOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (StaffOrder)表控制层
 *
 * @author 
 * @since 2022-06-26 22:40:41
 */
@Slf4j
@RestController
@RequestMapping("/staffOrder")
public class StaffOrderController {
    /**
     * 服务对象
     */
    @Resource
    private StaffOrderService staffOrderService;

    /**
     * 分页查询
     *
     * @param staffOrder  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<StaffOrder>> queryByPage(StaffOrder staffOrder, PageRequest pageRequest) {
        return ResponseEntity.ok(this.staffOrderService.queryByPage(staffOrder, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "查询存在的用户")
    @GetMapping("{id}")
    public ResponseEntity<StaffOrder> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.staffOrderService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param staffOrder 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<StaffOrder> add(StaffOrder staffOrder) {
        return ResponseEntity.ok(this.staffOrderService.insert(staffOrder));
    }

    /**
     * 编辑数据
     *
     * @param staffOrder 实体
     * @return 编辑结果
     */
    @PutMapping
    @ApiOperation(value = "编辑用户信息")
    public ResponseEntity<StaffOrder> edit(StaffOrder staffOrder) {
        return ResponseEntity.ok(this.staffOrderService.update(staffOrder));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.staffOrderService.deleteById(id));
    }


}

