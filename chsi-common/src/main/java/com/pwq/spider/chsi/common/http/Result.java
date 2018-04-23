package com.pwq.spider.chsi.common.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Yuyangjun
 * @desc
 * @date 2017/11/30 上午11:30
 */
@Getter
@Setter
public class Result implements java.io.Serializable{

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 3548459998108941175L;

    private String code;//消息码
    private String msg;//消息内容
    private Object data;//返回处理结果数据

    public Result(){
        super();
        setFailure();
    }

    public Result(ResultCode resultCode){
        setResult(resultCode);
    }

    public void appendMsg(String desc){
        this.msg = msg + ":["+desc+"]";
    }

    public void setResult(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public void setSuccess(){
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
    }

    public void setFailure(){
        this.code = ResultCode.FAILURE.getCode();
        this.msg = ResultCode.FAILURE.getMsg();
    }

    public void setBusy(){
        this.code = ResultCode.系统繁忙.getCode();
        this.msg = ResultCode.系统繁忙.getMsg();
    }

    public boolean isSuccess(){
        if(ResultCode.SUCCESS.getCode().equals(getCode())){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
