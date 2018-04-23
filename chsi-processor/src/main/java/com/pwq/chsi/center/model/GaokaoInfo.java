package com.pwq.chsi.center.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author：WenqiangPu
 * @Description
 * @Date：Created in 14:31 2018/4/23
 * @Modified By：
 */
@Setter
@Getter
@NoArgsConstructor
public class GaokaoInfo {

    private String educationLevel;//学历层次
    private String ml;//门类
    private String xk;//学科
    private String specialtyName;//专业名称
    private String specialtyCode;//专业代码
    private String conErate;//今年就业
    private String lastErate;//去年就业
    private String blastErate;//去年就业
    private String graduateScale;//毕业生规模
    private String asRate;//文理科比例
    private String mwRate;//男女比例


}
