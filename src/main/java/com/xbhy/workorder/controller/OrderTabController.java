package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.OrderTab;
import com.xbhy.workorder.service.OrderTabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (OrderTab)表控制层
 *
 * @author 
 * @since 2022-06-26 22:40:39
 */
@Slf4j
@RestController
@RequestMapping("orderTab")
public class OrderTabController {
    /**
     * 服务对象
     */
    @Resource
    private OrderTabService orderTabService;

    /**
     * 分页查询
     *
     * @param orderTab    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<OrderTab>> queryByPage(OrderTab orderTab, PageRequest pageRequest) {
        return ResponseEntity.ok(this.orderTabService.queryByPage(orderTab, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderTab> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.orderTabService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderTab 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<OrderTab> add(OrderTab orderTab) {
        return ResponseEntity.ok(this.orderTabService.insert(orderTab));
    }

    /**
     * 编辑数据
     *
     * @param orderTab 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<OrderTab> edit(OrderTab orderTab) {
        return ResponseEntity.ok(this.orderTabService.update(orderTab));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.orderTabService.deleteById(id));
    }

}

