package com.xbhy.workorder.rest;

import cn.hutool.json.JSONUtil;
import com.xbhy.workorder.enums.DataExchangeTypeEnum;
import com.xbhy.workorder.util.DataExchangeUtil;
import com.xbhy.workorder.util.TextParseUtil;
import com.xbhy.workorder.vo.DataExchangeRequest;
import com.xbhy.workorder.vo.ResponseVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

/**
 * Created on 2022/7/2.
 *
 * @author zz
 */
@Slf4j
@RestController
@RequestMapping("/data")
@Api(value = "数据交互", tags = {"数据格式转换"})
public class DataExchangeController {

    @PostMapping("/exchange")
    public ResponseVO<String> dataExchange(@RequestBody DataExchangeRequest input) throws IOException {
        log.info("/data/exchange {}", JSONUtil.toJsonStr(input));
        DataExchangeTypeEnum type = DataExchangeTypeEnum.getByCode(input.getType());
        if (Objects.isNull(type)) {
            log.error("unknown exchange type {}", input.getType());
            return ResponseVO.fail("参数异常");
        }
        String output = StringUtils.EMPTY;
        switch (type) {
            case QUOTATION_PARSE:
                output = DataExchangeUtil.exchangeTextFormat(input.getText());
                break;
            case ORDER_CALC:
                output = DataExchangeUtil.miniCalc(input.getText());
                break;
            case BILL_CALC:
                output = DataExchangeUtil.simpleCalc(input.getText());
                break;
            case INVOICE:
                output = TextParseUtil.parseInvoice(input.getText());
                break;
            case SEARCH_PARSE:
                output = TextParseUtil.parseSearchResult(input.getText());
                break;
            case SEARCH_PARSE1:
                output = TextParseUtil.parseSearchResultSimple(input.getText());
                break;
            default:
                log.error("unknown data exchange type {}", input.getType());
                //nop
        }
        log.info("/data/exchange response {}", output);
        return ResponseVO.success(output);
    }
}
