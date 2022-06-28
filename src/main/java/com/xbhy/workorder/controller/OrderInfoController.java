package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.OrderInfo;
import com.xbhy.workorder.service.OrderInfoService;
import com.xbhy.workorder.util.file.FileUploadUtils;
import io.swagger.annotations.Api;
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
 * (OrderInfo)表控制层
 *
 * @author 
 * @since 2022-06-26 22:40:33
 */
@Slf4j
@RestController
@Api(tags = "工单系统: 用户授权模块")
@RequestMapping("orderInfo")
public class OrderInfoController {
    /**
     * 服务对象
     */
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 分页查询
     *
     * @param orderInfo   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "查询所有工单")
    @RequiresPermissions("user_q:query")
    @PostMapping(value = "/query")
    public ResponseEntity<Page<OrderInfo>> queryByPage(OrderInfo orderInfo, PageRequest pageRequest) {
        return ResponseEntity.ok(this.orderInfoService.queryByPage(orderInfo, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderInfo> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.orderInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param orderInfo 实体
     * @return 新增结果
     */
    @ApiOperation(value = "发起工单")
    @RequiresPermissions("user_q:create")
    @PostMapping(value = "/create")
    public ResponseEntity<OrderInfo> create(OrderInfo orderInfo) {
        return ResponseEntity.ok(this.orderInfoService.insert(orderInfo));
    }

    /**
     * 编辑数据
     *
     * @param orderInfo 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<OrderInfo> edit(OrderInfo orderInfo) {
        return ResponseEntity.ok(this.orderInfoService.update(orderInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除工单")
    @DeleteMapping
   //@RequiresPermissions("user_q:terminate")
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.orderInfoService.deleteById(id));
    }
    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName =fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

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
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath =  Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}

