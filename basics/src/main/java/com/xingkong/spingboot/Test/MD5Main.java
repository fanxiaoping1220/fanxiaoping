package com.xingkong.spingboot.Test;//MD5????
//?????projectId??companyId???????????????????????????authKey


import java.security.MessageDigest;
import java.util.Scanner;

public class MD5Main {

    public static void main(String[] args) {
        //??????????????
        Scanner sc1=new Scanner(System.in);
        Scanner sc2 =new Scanner(System.in);
        //???????
        System.out.println("??????companyId??");
        //????????companyId
        int companyId = sc1.nextInt();

        System.out.println("??????projectId??");
        String projectId = sc2.next();
        //System.out.println(companyId+"---"+projectId);

        //??????????????
        getTodayZeroPointTimestamps();
        //System.out.println(getTodayZeroPointTimestamps());

        String authKey = new String();
        authKey = (companyId + projectId + getTodayZeroPointTimestamps());
        //System.out.println(buff);
        authKey.toString();



        //MD5????
        String str = MD5(authKey).toLowerCase().substring(4,20);
        System.out.println("authKey???"+ str);





    }
    public static Long getTodayZeroPointTimestamps(){
        Long currentTimestamps=System.currentTimeMillis();
        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        return currentTimestamps-(currentTimestamps+60*60*8*1000)%oneDayTimestamps;
    }

    //MD5????
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // ???MD5?????? MessageDigest ????
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ????????????????
            mdInst.update(btInput);
            // ???????
            byte[] md = mdInst.digest();
            // ???????????????????????????
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
