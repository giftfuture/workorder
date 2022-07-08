package com.xbhy.workorder.rest;

import com.xbhy.workorder.service.OrderDictService;
import com.xbhy.workorder.vo.OrderDictVO;
import com.xbhy.workorder.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (OrderDict)表控制层
 *
 * @author 
 * @since 2022-06-28 09:44:40
 */
@RestController
@RequestMapping("/dict")
public class OrderDictController {
    /**
     * 服务对象
     */
    @Resource
    private OrderDictService orderDictService;

    /**
     * 根据工单类别获取字典
     * @param sortTag
     * @return
     */
    @GetMapping("/loadBySort")
    public ResponseVO<OrderDictVO> loadDict(@RequestParam("sortTag") String sortTag) {
        return ResponseVO.success(this.orderDictService.loadOrderDictBySort(sortTag));
    }

    /**
     * 新增数据
     *
     * @param orderDictVO 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseVO<OrderDictVO> add(OrderDictVO orderDictVO) {
        return ResponseVO.success(this.orderDictService.insert(orderDictVO));
    }

    /**
     * 编辑数据
     *
     * @param orderDictVO 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseVO<OrderDictVO> edit(OrderDictVO orderDictVO) {
        return ResponseVO.success(this.orderDictService.update(orderDictVO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseVO<Boolean> deleteById(Long id) {
        return ResponseVO.success(this.orderDictService.deleteById(id));
    }

}

