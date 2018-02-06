package com.dangod.util;

import com.dangod.model.entity.pi.BusActionLog;
import com.dangod.model.entity.pi.BusScore;
import com.dangod.model.vo.BusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yxm on 2017/11/30.
 */
public class BusStatusUtil {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static List<BusScore> calStatus(List<BusActionLog> actionLogs, Map<String, BusVO> busVoMap, java.util.Date now, String deptId, List<BusScore> preBusScore){
        Map<String, BusVO> containBusRe0= new HashMap<>();//当天存在归0动作车辆
        for(BusActionLog log : actionLogs) {

            switch (log.getActiontype()) {
                case "1"://进场安检
                    busVoMap.get(log.getBusid()).addActStatus(1);
                    break;
                case "2"://收银
                    busVoMap.get(log.getBusid()).addActStatus(5);
                    break;
                case "4"://加油结束
                    busVoMap.get(log.getBusid()).addActStatus(3);
                    break;
                case "6"://洗车结束
                    busVoMap.get(log.getBusid()).addActStatus(2);
                    break;
                case "8"://出场安检 分数清零
                    if(!containBusRe0.containsKey(log.getBusid()))//记录当天存在动作的车辆
                        containBusRe0.put(log.getBusid(), busVoMap.get(log.getBusid()));
                    busVoMap.get(log.getBusid()).setActstatus(0);
                    break;
            }
//            busVoMap.get(log.getBusid()).setLasttime(new java.sql.Timestamp(log.getActiontime().getTime()));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(now);
        nowTime.set(Calendar.HOUR_OF_DAY, 1);
        nowTime.set(Calendar.MINUTE, 0);
        nowTime.set(Calendar.SECOND, 0);
        long reZero = nowTime.getTime().getTime();//工作日的开始 1:00AM
//
//        //从数据库验证score
//        for(BusScore s : preBusScore){
//            //验证0分车 是否因为跨天未出厂为0
//            if(busVoMap.containsKey(s.getBusid()) && busVoMap.get(s.getBusid()).getActstatus()==0) {
//                // 最后一次时间 是今天1:00前
//                if(s.getLasttime().getTime() < reZero) {
//                    //因为跨天无记录 停留在场内 维持原有分数
//                    busVoMap.get(s.getBusid()).setActstatus(s.getScore());
//                }
//            }
//        }


        Map<String,BusScore> preScoreMap = new HashMap<>();
        for(BusScore b: preBusScore){
            preScoreMap.put(b.getBusid(), b);
        }

        for(BusVO v : busVoMap.values()){   //遍历在场内的车辆分数
            if(preScoreMap.containsKey(v.getBusid())){ //数据库中分数信息
                if(preScoreMap.get(v.getBusid()).getLasttime().getTime() < reZero) {    //不是同一天工作日数据
                    if(containBusRe0.containsKey(v.getBusid())) {//当天车有出场动作（有归零动作）
                        preScoreMap.put(v.getBusid(), new BusScore(v.getBusid(), v.getActstatus(), now, deptId));//覆盖数据库数据 情况例如：正常出车
                    }else{//车当天未出过场
                        if(v.getActstatus() == 0) {// 当天没有任何加分项  相当于车没动 数据库记录时间、分数不改变，仅取值 情况例如：多天不出车
                            v.setActstatus(preScoreMap.get(v.getBusid()).getScore());
                        }else{//当天存在加分动作
                            v.addActStatus(preScoreMap.get(v.getBusid()).getScore());// 在数据库基础上加上现在的分数 情况例如:隔天洗车
                            preScoreMap.put(v.getBusid(), new BusScore(v.getBusid(), v.getActstatus(), now, deptId));//覆盖数据库数据
                        }
                    }
                }else{  //  是同一天工作日数据，将当前分数覆盖 情况例如：正常出车
                    v.setActstatus(preScoreMap.get(v.getBusid()).getScore());
                    preScoreMap.put(v.getBusid(),new BusScore(v.getBusid(), v.getActstatus(), now, deptId));
                }
            }else{
                preScoreMap.put(v.getBusid(),new BusScore(v.getBusid(), v.getActstatus(), now, deptId));
            }
        }




        List<BusScore> scoreList = new ArrayList<>();
        scoreList.addAll(preScoreMap.values());



        /**
         * 之前1点存在bug
         */
//        List<BusScore> scoreList = new ArrayList<>();
//        for(BusVO v : busVoMap.values()){
//            BusScore score;
//            if(v.getLasttime()!=null) {
//                score = new BusScore(v.getBusid(), v.getActstatus(), v.getLasttime(), deptId);
//            }else{
//                score = new BusScore(v.getBusid(), v.getActstatus(), new Timestamp(now.getTime()), deptId);
//            }
//            scoreList.add(score);
//        }



        return scoreList;
    }
}
