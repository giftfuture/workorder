package com.xbhy.workorder.controller;

import com.xbhy.workorder.service.OrderDictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (OrderDict)表控制层
 *
 * @author makejava
 * @since 2022-06-28 09:44:40
 */
@RestController
@RequestMapping("orderDict")
public class OrderDictController {
    /**
     * 服务对象
     */
    @Resource
    private OrderDictService orderDictService;

    /**
     * 分页查询
     *
     * @param orderDict 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<OrderDict>> queryByPage(OrderDict orderDict, PageRequest pageRequest) {
        return ResponseEntity.ok(this.orderDictService.queryByPage(orderDict, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderDict> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.orderDictService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderDict 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<OrderDict> add(OrderDict orderDict) {
        return ResponseEntity.ok(this.orderDictService.insert(orderDict));
    }

    /**
     * 编辑数据
     *
     * @param orderDict 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<OrderDict> edit(OrderDict orderDict) {
        return ResponseEntity.ok(this.orderDictService.update(orderDict));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.orderDictService.deleteById(id));
    }

}

