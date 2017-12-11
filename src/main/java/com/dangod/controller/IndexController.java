package com.dangod.controller;

import com.dangod.core.base.BaseController;
import com.dangod.model.bo.BusBO;
import com.dangod.model.bo.ParkBO;
import com.dangod.service.BusService;
import com.dangod.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/20.
 */
@Controller
@RequestMapping(value = {"","/","/index"})
public class IndexController extends BaseController {
    @Autowired
    private BusService busService;
    @Autowired
    private ParkService parkService;
    //内存存放之前Bus位置列表和当前Bus位置列表
    public static Map<String, BusBO> preBusMap;

    /**
     * 通过deptId 进入对应停车场
     * @param request
     * @param response
     * @param model
     * @param deptId
     * @return
     */
    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model,
                        @RequestParam("deptId")String deptId){
        ParkBO park = parkService.getParkById(deptId);
        model.addAttribute("fence",park);
        return "index";
    }

    /**
     * 通过deptId 获取围栏坐标
     * @param request
     * @param response
     * @param model
     * @param deptId
     * @return
     */
    @RequestMapping(value = {"/getFence"})
    public String getFence(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam("deptId")String deptId){
        ParkBO park = parkService.getParkById(deptId);
        return ajaxReturn(response, park, "", 0);
    }

    /**
     * 通过deptId 获取场内车辆信息
     * @param request
     * @param response
     * @param model
     * @param deptId
     * @param isNew 1 表示全新第一次请求
     * @return
     */
    @PostMapping(value = {"/getBusByDeptId"})
    public String getBusIn(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam("deptId")String deptId,
                           @RequestParam("isNew")Integer isNew){
        long startTime=System.nanoTime();
        if(isNew == 0)preBusMap = null;
        Calendar now = Calendar.getInstance();
        now.set(2017, 10, 24, 5, 47, 23);


        //TODO 后台限制数据库访问每5s一次存内存 降低多线程请求时 数据库连接压力问题
        preBusMap = busService.getBusVO(deptId, preBusMap, now);
        List<BusBO> busBOList = new ArrayList<>(preBusMap.values());
        System.out.println(busBOList.size());
        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)/1e9+"s");
        return ajaxReturn(response, busBOList,"",0);
    }
}
