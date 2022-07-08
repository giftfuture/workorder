package com.xbhy.workorder.enums;

public enum OrderSortEnum {

    SENDORDER( "发货组","FH"), TICKET("开票和资料组","KP"),
    BOOKORDER( "订货组","DH"), REMIT("打款组","DK"),
    PURCHASE( "进货组","JH"), PRICE("价格组","JG");

    private final String code;
    private final String group;

    /**
     *
     * @param code
     * @return
     */
    public static OrderSortEnum getCodeEnum(String code){
        OrderSortEnum orderSortEnum = null;
        OrderSortEnum[] enums = OrderSortEnum.values();
        for(OrderSortEnum sortEnum:enums){
            if(code.equals(sortEnum.getCode())){
                orderSortEnum = sortEnum;
                break;
            }
        }
        return orderSortEnum;
    }


    OrderSortEnum( String group,String code) {
        this.group = group;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public String getGroup() {
        return group;
    }
}
