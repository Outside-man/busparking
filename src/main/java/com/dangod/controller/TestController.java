package com.dangod.controller;

import com.dangod.core.base.BaseController;
import com.dangod.model.vo.BusVO;
import com.dangod.service.BusService;
import com.dangod.service.DictionaryService;
import com.dangod.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @RequestMapping(value = {"/test"})
//    public String test(HttpServletRequest request, HttpServletResponse response, Model model){
//        long startTime=System.nanoTime();
//        ParkBO v = parkService.getParkById("090101");
////        Calendar now = Calendar.getInstance();
////        now.set(2017, 10, 23, 5, 47, 23);
////        ParkInfoBo v = parkService.getParkInfoBo("090101", now);
//        long endTime=System.nanoTime(); //获取结束时间
//        System.out.println("程序运行时间： "+(endTime-startTime)/1e9+"s");
//        return ajaxReturn(response, v,"",0);
//    }
//
//    @RequestMapping(value = {"/"})
//    public String index(HttpServletRequest request, HttpServletResponse response, Model model){
//        ParkBO park = parkService.getParkById("090101");
//        model.addAttribute("fence",park);
//        return "index";
//    }
//
//    @RequestMapping(value = {"/getFence"})
//    public String getFence(HttpServletRequest request, HttpServletResponse response, Model model){
//        ParkBO park = parkService.getParkById("090101");
//        return ajaxReturn(response, park, "", 0);
//    }
//
//    @RequestMapping(value = {"/getBusByDeptId"})
//    public String getBusIn(HttpServletRequest request, HttpServletResponse response, Model model,
//                           @RequestParam("isNew")Integer isNew){
//        long startTime=System.nanoTime();
//        if(isNew==1)preBusMap = null;
//        Calendar now = Calendar.getInstance();
//        now.set(2017, 10, 24, 5, 47, 23);
//        preBusMap = busService.getBusVO("090101", preBusMap, now);
//        List<BusVO> busVOList = new ArrayList<>(preBusMap.values());
//        System.out.println(busVOList.size());
//        long endTime=System.nanoTime(); //获取结束时间
//        System.out.println("程序运行时间： "+(endTime-startTime)/1e9+"s");
//        return null;
//    }
}
