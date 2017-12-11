package com.jd.scrt.common.enums;

/**
 * Created by zhaosiming on 2016/1/28.
 */
public enum EMPayChannel {

    LIANLIAN(212, "215","231", "连连代扣金银猫","1","20001", ""),
    WANGYING(218, "230","231", "网银快捷金银猫","2","20001",""),
    XJKJYM(239, "230","249", "小金库金银猫","3","20001",""),

    LIANLIANJP(242, "243","244", "连连代扣巨品","1","20002","360080000230629280"),
    WANGYINGJP(245, "246","244", "网银快捷巨品","2","20002","360080000230629280"),
    XJKJP(248, "246","247", "小金库巨品","3","20002","360080000230629280"),

    LIANLIANZH(285, "287","280", "连连代扣中汇","1","20003",""),
    WANGYINGZH(282, "281","280", "网银快捷中汇","2","20003",""),
    XJKZH(283, "281","284", "小金库中汇","3","20003",""),

    LIANLIANYH(329, "330","327", "连连代扣银华","1","20004",""),
    WANGYINGYH(323, "324","327", "网银快捷银华","2","20004",""),
    XJKYH(325, "324","326", "小金库银华","3","20004",""),

    LIANLIANHF(399, "400","402", "连连代扣和丰永讯","1","20005",""),
    WANGYINGHF(396, "398","402", "网银快捷和丰永讯","2","20005","360080000232130840"),
    XJKHF(397, "398","403", "小金库恒丰","3","20005","360080000232130840"),

    LIANLIANLJ(399, "400","421", "连连代扣和丰永讯","1","20006",""),
    WANGYINGLJ(396, "398","421", "网银快捷和丰永讯","2","20006","360080000232130840"),
    XJKLJ(397, "398","422", "小金库和丰永讯","3","20006","360080000232130840"),

    LIANLIANZS(399, "400","421", "连连代扣和丰永讯","1","20007",""),
    WANGYINGZS(396, "398","421", "网银快捷和丰永讯","2","20007","360080000232130840"),
    XJKZS(397, "398","422", "小金库和丰永讯","3","20007","360080000232130840"),

    LIANLIANPA(399, "400","453", "连连代扣和丰永讯","1","20008",""),
    WANGYINGPA(396, "398","453", "网银快捷和丰永讯","2","20008","360080000232130840"),
    XJKPA(397, "398","423", "小金库平安","3","20008","360080000232130840"),

    LIANLIANTJ(399, "400","421", "连连代扣和丰永讯","1","20009",""),
    WANGYINGTJ(396, "398","421", "网银快捷和丰永讯","2","20009","360080000232130840"),
    XJKTJ(397, "398","422", "小金库和丰永讯","3","20009","360080000232130840"),

    LIANLIANFB(399, "400","421", "连连代扣和丰永讯","1","20011",""),
    WANGYINGFB(396, "398","421", "网银快捷和丰永讯","2","20011","360080000232130840"),
    XJKFB(397, "398","422", "小金库和丰永讯","3","20011","360080000232130840"),

    LIANLIANGD(399, "400","421", "连连代扣和丰永讯","1","20012",""),
    WANGYINGGD(396, "398","421", "网银快捷和丰永讯","2","20012","360080000232130840"),
    XJKGD(397, "398","422", "小金库和丰永讯","3","20012","360080000232130840"),

    LIANLIANZX(399, "400","421", "连连代扣和丰永讯","1","20013",""),
    WANGYINGZX(396, "398","421", "网银快捷和丰永讯","2","20013","360080000232130840"),
    XJKZX(397, "398","422", "小金库和丰永讯","3","20013","360080000232130840"),

    LIANLIANWZ(399, "400","421", "连连代扣和丰永讯","1","20014",""),
    WANGYINGWZ(396, "398","421", "网银快捷和丰永讯","2","20014","360080000232130840"),
    XJKWZ(397, "398","422", "小金库和丰永讯","3","20014","360080000232130840"),

    ACTIVITY_FUNDS(-1,"916","","票据活动费用","2","","360080000232283375"),//票据活动费用代付枚举配置，活动费用划转专用
    ;

    private int code; // 代扣
    private String payCode; // 对公代付
    private String redemptionCode; // 对私代付（承兑）
    private String desc;
    private String channelType;//1 连连 2 网银 3 小金库
    private String merchantId;//金融台帐商户号
    private String customerId;//融资商户号(网银三代新会员ID(360开头，18位)),该属性为空表示不继续合作的机构或连连通道

    private EMPayChannel(int code, String payCode,String redemptionCode, String desc,String channelType,String merchantId, String customerId) {
        this.code = code;
        this.payCode = payCode;
        this.redemptionCode=redemptionCode;
        this.desc = desc;
        this.channelType = channelType;
        this.merchantId = merchantId;
        this.customerId = customerId;
    }

    public String getCustomerId(){return customerId;}

    public int code() {
        return code;
    }

    public String payCode() {
        return payCode;
    }

    public String desc() {
        return desc;
    }

    public String redemptionCode() {
        return redemptionCode;
    }

    public String channelType() {
        return channelType;
    }

    public String merchantId(){
        return  merchantId;
    }

    public static EMPayChannel getPayChannel(int code) {
        for (EMPayChannel pc : EMPayChannel.values())
            if (pc.code() == code)
                return pc;
        return null;
    }

    public static EMPayChannel getRedemptionChannel(int code, String merchantId) {
        for (EMPayChannel pc : EMPayChannel.values())
            if (pc.code() == code && pc.merchantId().equals(merchantId)) {
                return pc;
            }
        return null;
    }

    public static EMPayChannel getWypayCode(String merchantId) {
        for (EMPayChannel pc : EMPayChannel.values())
            if (pc.merchantId().equals(merchantId)&&"2".equals(pc.channelType))
                return pc;
        return null;
    }


    /**
     * 通道代扣值转换：生成打款单时，小金库渠道算是网银快捷的渠道(对应usesrinfo中的payChannelId是239或248的算成是218或245)
     * @param code
     * @return
     */
    public static String paidToEnterprisePayChannel(String code){

        if(String.valueOf(EMPayChannel.XJKJYM.code).equals(code)){
            return EMPayChannel.WANGYING.code+"";
        }
        if(String.valueOf(EMPayChannel.XJKJP.code).equals(code)){
            return EMPayChannel.WANGYINGJP.code+"";
        }

        return code;
    }
}
