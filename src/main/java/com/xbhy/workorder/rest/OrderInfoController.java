package com.xbhy.workorder.rest;

import com.google.common.collect.Maps;
import com.xbhy.workorder.config.BaseConfig;
import com.xbhy.workorder.entity.OrderInfo;
import com.xbhy.workorder.service.OrderInfoService;
import com.xbhy.workorder.util.file.FileUploadUtils;
import com.xbhy.workorder.util.file.FileUtils;
import com.xbhy.workorder.vo.OrderInfoVO;
import com.xbhy.workorder.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * (OrderInfo)表控制层
 *
 * @author 
 * @since 2022-06-26 22:40:33
 */
@Slf4j
@RestController
@Api(tags = "工单系统: 用户授权模块")
@RequestMapping("/order")
public class OrderInfoController {
    /**
     * 服务对象
     */
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 分页查询
     *
     * @param orderInfoVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "查询所有工单")
    @PostMapping(value = "/list")
    public ResponseVO<Page<OrderInfo>> queryByPage(OrderInfoVO orderInfoVO, PageRequest pageRequest) {
        return ResponseVO.success(this.orderInfoService.queryByPage(orderInfoVO, pageRequest));
    }

    /** 综合搜索
     *
     * @param orderInfoVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "综合搜索")
    @PostMapping(value = "/search")
    ResponseVO<Page<OrderInfoVO>> overallSearch(OrderInfoVO orderInfoVO, PageRequest pageRequest){
        return ResponseVO.success(this.orderInfoService.overallSearch(orderInfoVO, pageRequest));
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public ResponseVO<OrderInfoVO> queryById(@PathVariable("id") Long id) {
        return ResponseVO.success(this.orderInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderInfoVO 实体
     * @return 新增结果
     */
    @ApiOperation(value = "创建工单")
    @PostMapping(value = "/create")
    public ResponseVO<OrderInfoVO> create(OrderInfoVO orderInfoVO) {
        return ResponseVO.success(this.orderInfoService.insert(orderInfoVO));
    }

    /**
     * 编辑数据
     *
     * @param orderInfoVO 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "编辑工单")
    @PostMapping(value = "/edit")
    @PutMapping
    public ResponseVO<OrderInfo> edit(OrderInfoVO orderInfoVO) {
        return ResponseVO.success(this.orderInfoService.update(orderInfoVO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除工单")
    @DeleteMapping("/del")
   //@RequiresPermissions("user_q:terminate")
    public ResponseVO<Boolean> deleteById(Long id) {
        return ResponseVO.success(this.orderInfoService.deleteById(id));
    }
    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/down")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(String.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName =fileName.substring(fileName.indexOf("_") + 1);
            String filePath = BaseConfig.getAttachUrl() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseVO uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath =  BaseConfig.getAttachUrl();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = filePath + fileName;
            Map<String,String> data = Maps.newHashMap();
            data.put("fileName", fileName);
            data.put("url", url);
            return ResponseVO.success(data);
        }
        catch (Exception e)
        {
            return ResponseVO.fail(e.getMessage());
        }
    }
}

