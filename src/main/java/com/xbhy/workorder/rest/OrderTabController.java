package com.xbhy.workorder.rest;

import com.xbhy.workorder.entity.OrderTab;
import com.xbhy.workorder.service.OrderTabService;
import com.xbhy.workorder.vo.OrderSortVO;
import com.xbhy.workorder.vo.OrderTabVO;
import com.xbhy.workorder.vo.ResponseVO;
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
@RequestMapping("/tab")
public class OrderTabController {
    /**
     * 服务对象
     */
    @Resource
    private OrderTabService orderTabService;

    /**
     *
     *
     * @return 查询结果
     */
    /**
     *
     *
     * @return 查询结果
     */
    @GetMapping("/fetchAll")
    public ResponseVO<OrderTabVO> fetchAll() {
        return ResponseVO.success(this.orderTabService.fetchAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public ResponseVO<OrderTabVO> queryById(@PathVariable("id") Long id) {
        return ResponseVO.success(this.orderTabService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderTabVO 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseVO<OrderTabVO> add(OrderTabVO orderTabVO) {
        return ResponseVO.success(this.orderTabService.insert(orderTabVO));
    }

    /**
     * 编辑数据
     *
     * @param orderTabVO 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ResponseVO<OrderTabVO> edit(OrderTabVO orderTabVO) {
        return ResponseVO.success(this.orderTabService.update(orderTabVO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/del")
    public ResponseVO<Boolean> deleteById(Long id) {
        return ResponseVO.success(this.orderTabService.deleteById(id));
    }

}

