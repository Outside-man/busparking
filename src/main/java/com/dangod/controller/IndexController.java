package com.dangod.controller;

import com.dangod.core.base.BaseController;
import com.dangod.model.bo.ParkBO;
import com.dangod.model.bo.ParkInfoBo;
import com.dangod.model.vo.BusInfoVO;
import com.dangod.model.vo.BusVO;
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
    public static Map<String, BusVO> preBusMap;
    //marker和海量点的切换标志
    private static int status = 0;//0:使用marker 1使用海量点

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
        Calendar now = Calendar.getInstance();
//        now.set(2017, 10, 20, 5, 47, 23);
        if(isNew == 0){
            //新的访问 必须请求数据
            preBusMap = null;
            preBusMap = busService.getBusVO(deptId, preBusMap, now);
        }
        List<BusVO> busVOList = new ArrayList<>(preBusMap.values());
        System.out.println(busVOList.size());
        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)/1e9+"s");
        return ajaxReturn(response, busVOList,"",status);
    }

    /**
     * 通过DeptId获取当天车场内的信息
     * @param request
     * @param response
     * @param model
     * @param deptId
     * @return
     */
    @PostMapping(value = {"/getParkInfo"})
    public String getParkInfo(HttpServletRequest request, HttpServletResponse response, Model model,
                              @RequestParam("deptId")String deptId){
        Calendar now = Calendar.getInstance();
//        now.set(2017, 10, 20, 5, 47, 23);
        ParkInfoBo b = parkService.getParkInfoBo(deptId, now);
        return ajaxReturn(response, b,"",0);
    }

    /**
     * 通过BusId获取车子的信息
     * @param request
     * @param response
     * @param model
     * @param busId
     * @return
     */
    @PostMapping(value = {"/getBusInfoById"})
    public String getBusInfoById(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("busId")String busId){
        BusInfoVO v = busService.getBusInfo(busId);
        try {
            v.setHealth(preBusMap.get(v.getBusId()).getActstatus());
        }catch (NullPointerException e){
            System.out.println("车辆已离场");
            v.setHealth(0);
        }
        return ajaxReturn(response,v,"",0);
    }

    /**
     * 海量点方式获取车辆信息 通过GPS点
     * @param request
     * @param response
     * @param model
     * @param x
     * @param y
     * @return
     */
    @PostMapping(value = {"/getBusInfoByGPS"})
    public String getBusInfoByGPS(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("x")String x,
                             @RequestParam("y")String y){
        String busId = null;
        for(BusVO b : preBusMap.values()){
            if(b.getLastx().equals(x)&&b.getLasty().equals(y)){
                busId = b.getBusid();
                break;
            }
        }
        if(busId!=null) {
            BusInfoVO v = busService.getBusInfo(busId);
            try {
                v.setHealth(preBusMap.get(v.getBusId()).getActstatus());
            } catch (NullPointerException e) {
                System.out.println("车辆已离场");
                v.setHealth(0);
            }
            return ajaxReturn(response,v,"",0);
        }else{
            return ajaxReturn(response,null,"未找到车辆",-1);
        }
    }

}
