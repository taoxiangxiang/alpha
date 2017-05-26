package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.VehicleUseManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import org.apache.commons.fileupload.FileItem;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/16.
 */
public class VehicleUseUpdate extends BaseAjaxModule{

    @Resource
    private VehicleUseManager vehicleUseManager;


    public void execute(@Param("id") int id, @Param("actualStartDate") String actualStartDate,
                        @Param("actualEndDate") String actualEndDate, @Param("startMile") Integer startMile,
                        @Param("actualBackDate") String actualBackDate, @Param("endMile") Integer endMile,
                        @Param("fuwuFee") double fuwuFee, @Param("cailvFee") double cailvFee,
                        @Param("jiabanFee") double jiabanFee, @Param("guoluFee") double guoluFee,
                        @Param("guoqiaoFee") double guoqiaoFee, @Param("xicheFee") double xicheFee,
                        @Param("tingcheFee") double tingcheFee, @Param("otherFee") double otherFee,
                        @Param("remark") String remark, @Param("fileList") String fileList,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleUseDO vehicleUseDO = new VehicleUseDO();
            vehicleUseDO.setId(id);
            vehicleUseDO.setActualStartDate(actualStartDate);
            vehicleUseDO.setActualBackDate(actualBackDate);
            vehicleUseDO.setActualEndDate(actualEndDate);
            vehicleUseDO.setStartMile(startMile);
            vehicleUseDO.setEndMile(endMile);
            vehicleUseDO.setFuwuFee(fuwuFee);
            vehicleUseDO.setCailvFee(cailvFee);
            vehicleUseDO.setJiabanFee(jiabanFee);
            vehicleUseDO.setGuoluFee(guoluFee);
            vehicleUseDO.setGuoqiaoFee(guoqiaoFee);
            vehicleUseDO.setXicheFee(xicheFee);
            vehicleUseDO.setTingcheFee(tingcheFee);
            vehicleUseDO.setOtherFee(otherFee);
            vehicleUseDO.setRemark(remark);
            if (fileList != null) {
                JSONObject jsonObject = new JSONObject();
                if (vehicleUseDO.getAttribute() != null) {
                    jsonObject = JSON.parseObject(vehicleUseDO.getAttribute());
                }
                jsonObject.put("fileList", fileList);
                vehicleUseDO.setAttribute(jsonObject.toJSONString());
            }
            boolean res = vehicleUseManager.update(vehicleUseDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("VehicleUseUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
