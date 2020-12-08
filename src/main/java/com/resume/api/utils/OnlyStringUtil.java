package com.resume.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class OnlyStringUtil {
   /**
       * 根据当前时间生成的唯一字符串
    * @return
    */
   public static String OnlyStringDate() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
		Random random=new Random();
		for(int i=0;i<3;i++){
		result+=random.nextInt(10);
		}
		return newDate+result;
	}
   /**
       * 使用UUID生成唯一字符串
    * @return
    */
   public static String OnlyStringUUId() {
		int machineId = 1;//最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
		hashCodeV = - hashCodeV;
		}
	    return machineId+ String.format("%015d", hashCodeV);
	}
   
     public static void main(String[] args) {
		System.out.println(OnlyStringUUId());
		System.out.println(OnlyStringDate());
	}

}
