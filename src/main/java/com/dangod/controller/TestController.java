package com.dangod.controller;

import com.dangod.core.base.BaseController;
import com.dangod.model.bo.ParkBO;
import com.dangod.model.vo.BusVO;
import com.dangod.service.BusService;
import com.dangod.service.DictionaryService;
import com.dangod.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/25.
 */
@Controller
@RequestMapping(value = {"/test", "/test/index"})
public class TestController extends BaseController {
    @Autowired
    private BusService busService;
    @Autowired
    private ParkService parkService;
    @Autowired
    private DictionaryService dictionaryService;

    //内存存放之前Bus位置列表和当前Bus位置列表
    public static Map<String, BusVO> preBusMap;

    @RequestMapping(value = {"/test"})
    public String test(HttpServletRequest request, HttpServletResponse response, Model model){
        long startTime=System.nanoTime();





        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)/1e9+"s");
        return ajaxReturn(response, null,"",0);
    }

    @RequestMapping(value = {"/"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model){
        ParkBO park = parkService.getParkById("090101");
        model.addAttribute("fence",park);
        return "index";
    }

    @RequestMapping(value = {"/getFence"})
    public String getFence(HttpServletRequest request, HttpServletResponse response, Model model){
        ParkBO park = parkService.getParkById("090101");
        return ajaxReturn(response, park, "", 0);
    }

    @RequestMapping(value = {"/getBusByDeptId"})
    public String getBusIn(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam("isNew")Integer isNew){
        long startTime=System.nanoTime();
        if(isNew==1)preBusMap = null;
        Calendar now = Calendar.getInstance();
        now.set(2017, 10, 24, 5, 47, 23);
        preBusMap = busService.getBusVO("090101", preBusMap, now);
        List<BusVO> BusVoList = new ArrayList<>(preBusMap.values());
        System.out.println(BusVoList.size());
        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)/1e9+"s");
        return ajaxReturn(response, BusVoList,"",0);
    }
}
