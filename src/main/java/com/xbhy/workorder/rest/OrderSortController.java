package com.xbhy.workorder.rest;

import com.xbhy.workorder.service.OrderSortService;
import com.xbhy.workorder.vo.OrderSortVO;
import com.xbhy.workorder.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/sort")
public class OrderSortController {
    /**
     * 服务对象
     */
    @Resource
    private OrderSortService orderSortService;

    /**
     *
     *
     * @return 查询结果
     */
    @GetMapping("/fetchAll")
    public ResponseVO<OrderSortVO> fetchAll() {
        return ResponseVO.success(this.orderSortService.fetchAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public ResponseVO<OrderSortVO> queryById(@PathVariable("id") Long id) {
        return ResponseVO.success(this.orderSortService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderSortVO 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseVO<OrderSortVO> add(OrderSortVO orderSortVO) {
        return ResponseVO.success(this.orderSortService.insert(orderSortVO));
    }

    /**
     * 编辑数据
     *
     * @param orderSortVO 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ResponseVO<OrderSortVO> edit(OrderSortVO orderSortVO) {
        return ResponseVO.success(this.orderSortService.update(orderSortVO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/del")
    public ResponseVO<Boolean> deleteById(Long id) {
        return ResponseVO.success(this.orderSortService.deleteById(id));
    }

}

