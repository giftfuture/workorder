package com.xbhy.workorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Maps;
import com.xbhy.workorder.entity.OrderInfo;
import com.xbhy.workorder.mapper.OrderInfoMapper;
import com.xbhy.workorder.enums.OrderSortEnum;
import com.xbhy.workorder.service.OrderInfoService;
import com.xbhy.workorder.util.ArgumentUtil;
import com.xbhy.workorder.util.DateStyle;
import com.xbhy.workorder.util.DateUtils;
import com.xbhy.workorder.util.GenUtils;
import com.xbhy.workorder.vo.OrderInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (OrderInfo)表服务实现类
 *
 * @author 
 * @since 2022-06-26 22:40:35
 */
@Slf4j
@Primary
@Transactional
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderInfoVO queryById(Long id) {
        return BeanUtil.copyProperties(this.orderInfoMapper.queryById(id), OrderInfoVO.class);
    }

    /**
     * 分页查询
     *
     * @param orderInfoVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderInfoVO> queryByPage(OrderInfoVO orderInfoVO, PageRequest pageRequest) {
        orderInfoVO.setCreateTimeBegin(DateUtils.StringToDate(DateUtils.dayOfStartFormat.format(orderInfoVO.getCreateTimeBegin()), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
        orderInfoVO.setCreateTimeEnd(DateUtils.StringToDate(DateUtils.dayOfEndFormat.format(orderInfoVO.getCreateTimeEnd()), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
        List<String> searchContent = Arrays.stream(orderInfoVO.getSearchContent().split(" ")).map(String::trim).collect(Collectors.toList());
        List<String> orderNos = searchContent.stream().filter(e-> GenUtils.OrderSortCodes.contains(e.substring(0,2))).collect(Collectors.toList());
        List<String> otherKeyWords = searchContent.stream().filter(e-> !GenUtils.OrderSortCodes.contains(e.substring(0,2))).collect(Collectors.toList());
        String orderNo = orderNos.isEmpty() ? StringUtils.EMPTY : orderNos.get(0);
        //工单号精确匹配
        if(orderNo.length() == GenUtils.orderNoLen){
            orderInfoVO.setOrderNo(orderNo);
        }
        //工单号模糊匹配
        if(orderNo.length() < GenUtils.orderNoLen){
            orderInfoVO.setOrderNoPrefix(orderNo);
        }
        OrderInfo orderInfo = BeanUtil.copyProperties(orderInfoVO, OrderInfo.class);
        //取前10个关键字
        if(CollectionUtils.isNotEmpty(otherKeyWords)){
            List<String> subKeyWords = otherKeyWords.size() <= GenUtils.keyWordsLen ? otherKeyWords : otherKeyWords.subList(0,10);
            if(OrderSortEnum.TICKET.getCode().equals(orderInfo.getOrderSort())){
                orderInfo.setKpContextList(subKeyWords);
            }else{
            //生成文本关键字索引排列组合
            List<String> contentIdxList = ArgumentUtil.buildContentCombinations(GenUtils.keyWordIdx, new StringBuffer()  , 0);
            //生成备注关键字索引排列组合
            List<String> remarkIdxList = ArgumentUtil.buildRemarkText(GenUtils.keyWordIdx, contentIdxList);
            Map<String,String> contentRemarkCondtion = Maps.newHashMap();
            for(int i=0;i<contentIdxList.size();i++){
                String contentIdx = contentIdxList.get(i);
                String remarkIdx = remarkIdxList.get(i);
                StringBuffer content = new StringBuffer();
                contentIdx.chars().forEach(e->{
                    content.append(subKeyWords.get(e)).append(",");
                });
                content.deleteCharAt(content.length()-1);
                StringBuffer remark = new StringBuffer();
                remarkIdx.chars().forEach(e->{
                    remark.append(subKeyWords.get(e)).append(",");
                });
                remark.deleteCharAt(remark.length()-1);
                contentRemarkCondtion.put(content.toString(),remark.toString());
            }
            }
        }
        long total = this.orderInfoMapper.count(orderInfo);
        return new PageImpl<>(BeanUtil.copyToList(this.orderInfoMapper.queryAllByLimit(orderInfo, pageRequest), OrderInfoVO.class), pageRequest, total);
    }


    /**
     * 分页查询
     *
     * @param orderInfoVO   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OrderInfoVO> overallSearch(OrderInfoVO orderInfoVO, PageRequest pageRequest) {
        orderInfoVO.setCreateTimeBegin(DateUtils.StringToDate(DateUtils.dayOfStartFormat.format(orderInfoVO.getCreateTimeBegin()), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
        orderInfoVO.setCreateTimeEnd(DateUtils.StringToDate(DateUtils.dayOfEndFormat.format(orderInfoVO.getCreateTimeEnd()), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
        List<String> searchContent = Arrays.stream(orderInfoVO.getSearchContent().split(" ")).map(String::trim).collect(Collectors.toList());
        List<String> orderNos = searchContent.stream().filter(e-> GenUtils.OrderSortCodes.contains(e.substring(0,2))).collect(Collectors.toList());
        List<String> otherKeyWords = searchContent.stream().filter(e-> !GenUtils.OrderSortCodes.contains(e.substring(0,2))).collect(Collectors.toList());
        String orderNo = orderNos.isEmpty() ? StringUtils.EMPTY : orderNos.get(0);
        //工单号精确匹配
        if(orderNo.length() == GenUtils.orderNoLen){
            orderInfoVO.setOrderNo(orderNo);
        }
        //工单号模糊匹配
        if(orderNo.length() < GenUtils.orderNoLen){
            orderInfoVO.setOrderNoPrefix(orderNo);
        }
        OrderInfo orderInfo = BeanUtil.copyProperties(orderInfoVO, OrderInfo.class);
        //取前10个关键字
        if(CollectionUtils.isNotEmpty(otherKeyWords)){
            List<String> subKeyWords = otherKeyWords.size() <= GenUtils.keyWordsLen ? otherKeyWords : otherKeyWords.subList(0,10);
            if(OrderSortEnum.TICKET.getCode().equals(orderInfo.getOrderSort())){
                orderInfo.setKpContextList(subKeyWords);
            }else{
                //生成文本关键字索引排列组合
                List<String> contentIdxList = ArgumentUtil.buildContentCombinations(GenUtils.keyWordIdx, new StringBuffer()  , 0);
                //生成备注关键字索引排列组合
                List<String> remarkIdxList = ArgumentUtil.buildRemarkText(GenUtils.keyWordIdx, contentIdxList);
                Map<String,String> contentRemarkCondtion = Maps.newHashMap();
                for(int i=0;i<contentIdxList.size();i++){
                    String contentIdx = contentIdxList.get(i);
                    String remarkIdx = remarkIdxList.get(i);
                    StringBuffer content = new StringBuffer();
                    contentIdx.chars().forEach(e->{
                        content.append(subKeyWords.get(e)).append(",");
                    });
                    content.deleteCharAt(content.length()-1);
                    StringBuffer remark = new StringBuffer();
                    remarkIdx.chars().forEach(e->{
                        remark.append(subKeyWords.get(e)).append(",");
                    });
                    remark.deleteCharAt(remark.length()-1);
                    contentRemarkCondtion.put(content.toString(),remark.toString());
                }
            }
        }
        long total = this.orderInfoMapper.count(orderInfo);
        return new PageImpl<>(BeanUtil.copyToList(this.orderInfoMapper.queryAllByLimit(orderInfo, pageRequest), OrderInfoVO.class), pageRequest, total);
    }
    /**
     * 新增数据
     *
     * @param orderInfoVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderInfoVO insert(OrderInfoVO orderInfoVO) {
        this.orderInfoMapper.insert(BeanUtil.copyProperties(orderInfoVO,OrderInfo.class));
        return orderInfoVO;
    }

    /**
     * 修改数据
     *
     * @param orderInfoVO 实例对象
     * @return 实例对象
     */
    @Override
    public OrderInfoVO update(OrderInfoVO orderInfoVO) {
        this.orderInfoMapper.update(BeanUtil.copyProperties(orderInfoVO,OrderInfo.class));
        return this.queryById(orderInfoVO.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderInfoMapper.deleteById(id) > 0;
    }
}
