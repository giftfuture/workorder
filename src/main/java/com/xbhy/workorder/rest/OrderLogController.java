package com.xbhy.workorder.rest;

import com.xbhy.workorder.entity.OrderLog;
import com.xbhy.workorder.service.OrderLogService;
import com.xbhy.workorder.vo.OrderLogVO;
import com.xbhy.workorder.vo.ResponseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (OrderLog)表控制层
 *
 * @author 
 * @since 2022-06-27 13:37:36
 */
@RestController
@RequestMapping("/log")
public class OrderLogController {
    /**
     * 服务对象
     */
    @Resource
    private OrderLogService orderLogService;

    /**
     * 分页查询
     *
     * @param orderLogVO 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseVO<Page<OrderLogVO>> queryByPage(OrderLogVO orderLogVO, PageRequest pageRequest) {
        return ResponseVO.success(this.orderLogService.queryByPage(orderLogVO, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param orderId 工单ID
     * @return  工单操作历史
     */
    @GetMapping("/{orderId}")
    public ResponseVO<List<OrderLogVO>> queryById(@PathVariable("orderId") Long orderId) {
        return ResponseVO.success(this.orderLogService.queryByOrderId(orderId));
    }

    /**
     * 新增数据
     *
     * @param orderLogVO 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseVO<OrderLogVO> add(OrderLogVO orderLogVO) {
        return ResponseVO.success(this.orderLogService.insert(orderLogVO));
    }


}

