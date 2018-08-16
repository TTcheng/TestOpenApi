package com.founder.openapi;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DictQueryTest {
    public static void main(String[] args) {
        new DictQueryTest().testOpenApi();
    }

    public void testOpenApi() {
        JSONObject json = new JSONObject();
        json.put("SenderId", "C10-10000002");//请求方编号
        json.put("ServiceId", "S10-10000145");//服务方编号
        json.put("Method", "Query");//调用方法名
        //{"UserName":"admin","CallReason":"测试","UserDept":"010001"}
        json.put("EndUser", "{\"UserName\":\"admin\",\"CallReason\":\"测试\",\"UserDept\":\"010001\"}");//调用方法名
        JSONObject params = new JSONObject();
        params.put("Condition", "ZDBH = 'GB_D_XBDM'");
//        params.put("RequiredItems","XM,XB,ZJHM");
        params.put("OrderItems", "{'DM':'desc'}");
        params.put("InfoCodeMode", "1");
        json.put("Params", params);//参数信息
        String results = post(json, "http://123.57.28.24:9017/jwzh-manage/jwzh/openApi/queryDictByCondition");
        System.out.println("results = " + results);
    }

    /**
     * 调用http接口
     *
     * @param jObject
     * @param url
     * @return
     */
    public String post(JSONObject jObject, String url) {
        InputStream in = null;
        ByteArrayOutputStream barray = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(120000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//            out.write(jObject.toString().getBytes("utf-8"));
            out.write("fasdfasf".getBytes("utf-8"));
//            out.write("{\"SenderId\":\"C12-10000001\",\"ServiceId\":\"S12-10000003\",\"AuthorizeInfo\":\"2BDC-2E9D-45CE-EF01\",\"EndUser\":{\"UserCardId\":\"123456789987654321\",\"UserName\":\"张三\",\"UserDept\":\"010000000000\"},\"Method\":\"Query\",\"Params\":{\"Condition\":\"ZDBH='GB_D_XBDM'\",\"RequiredItems\":\"ZDBH,ZDMC\",\"OrderItems\":{\"zdbh\":\"desc\"},\"InfoCodeMode\":\"0\",\"RowsPerPage\":\"10\",\"PageNum\":\"1\"}}".getBytes("utf-8"));
//            out.write("{\"SenderId\":\"C12-10000001\"}".getBytes("utf-8"));
            // 接收返回信息
            in = new DataInputStream(conn.getInputStream());
            byte[] array = new byte[4096];
            int count = -1;
            barray = new ByteArrayOutputStream();
            while (-1 != (count = in.read(array))) {
                barray.write(array, 0, count);
            }
            byte[] data = barray.toByteArray();
            barray.close();
            String ret = new String(data, "utf-8");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (barray != null) {
                try {
                    barray.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
//    /**
//     * 打印结果集
//     * @param resoult
//     */
//    private static void printResult(String result){
//        System.out.println("解析结果报文");
//        JSONObject jObject = JSONObject.fromObject(result);
//        System.out.println("请求方ID："+jObject.get("SenderId"));
//        System.out.println("服务方ID："+jObject.get("ServiceId"));
//        System.out.println("调度节点ID："+jObject.get("PdnId"));
//        System.out.println("返回消息类别："+jObject.get("MsgType"));
//        JSONObject payLoad = JSONObject.fromObject(jObject.get("PayLoad"));
//        System.out.println("代码："+payLoad.get("Code")+" | 返回信息："+payLoad.get("Message"));
//
//        if("000".equals(payLoad.get("Code"))){
//            JSONObject json = JSONObject.fromObject(payLoad.get("Data"));
//            System.out.println("数据总条数："+json.get("TotalNum"));
//            JSONArray dataSet = json.getJSONArray(ServiceConstants.RESULT);
//            for (int i = 0; i < dataSet.size(); i++) {
//                JSONObject data = JSONObject.fromObject(dataSet.get(i));
//                System.out.println("*******当前第"+(i+1)+"条数据*******");
//                for (Object key :data.keySet()) {
//                    System.out.print(key+"="+data.get(key));
//                    System.out.print("      ");
//                }
//                System.out.println();
//            }
//        }else{
//            System.out.println("请求失败："+"错误代码【"+payLoad.get("Code")+"】，错误信息【"+payLoad.get("Message")+"】");
//        }
//    }

}
