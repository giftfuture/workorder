package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.OrderSort;
import com.xbhy.workorder.service.OrderSortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (OrderSort)表控制层
 *
 * @author 
 * @since 2022-06-26 22:40:37
 */
@Slf4j
@RestController
@RequestMapping("orderSort")
public class OrderSortController {
    /**
     * 服务对象
     */
    @Resource
    private OrderSortService orderSortService;

    /**
     * 分页查询
     *
     * @param orderSort   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<OrderSort>> queryByPage(OrderSort orderSort, PageRequest pageRequest) {
        return ResponseEntity.ok(this.orderSortService.queryByPage(orderSort, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderSort> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.orderSortService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderSort 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<OrderSort> add(OrderSort orderSort) {
        return ResponseEntity.ok(this.orderSortService.insert(orderSort));
    }

    /**
     * 编辑数据
     *
     * @param orderSort 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<OrderSort> edit(OrderSort orderSort) {
        return ResponseEntity.ok(this.orderSortService.update(orderSort));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.orderSortService.deleteById(id));
    }

}

