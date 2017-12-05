package com.dangod.controller;

import com.dangod.core.base.BaseController;
import com.dangod.model.bo.ParkBO;
import com.dangod.model.entity.pi.BusGPSInfo;
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
import java.util.List;

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

    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model,
                        @RequestParam("deptId")String deptId){
        ParkBO park = parkService.getParkById(deptId);
        model.addAttribute("fence",park);
        return "index";
    }

    @RequestMapping(value = {"/getFence"})
    public String getFence(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam("deptId")String deptId){
        ParkBO park = parkService.getParkById(deptId);
        return ajaxReturn(response, park, "", 0);
    }

    @PostMapping(value = {"/getBusByDeptId"})
    public String getBusIn(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam("deptId")String deptId){
//        long startTime=System.nanoTime();
        //TODO 后台限制数据库访问每5s一次存内存 降低多线程请求时 数据库连接压力问题
        List<BusGPSInfo> busInList = busService.getParkBusIn(deptId);
//        long endTime=System.nanoTime(); //获取结束时间
//        System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
        return ajaxReturn(response, busInList,"",0);
    }
}
