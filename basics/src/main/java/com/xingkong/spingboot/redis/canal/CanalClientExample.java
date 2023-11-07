package com.xingkong.spingboot.redis.canal;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import redis.clients.jedis.Jedis;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * * @className: CanalClientExample
 * * @description: canal与redis双写一致性
 * * @author: fan xiaoping
 * * @date: 2023/11/7 0007 17:22
 **/
public class CanalClientExample {

    public static void main(String args[]) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("123.57.13.131",
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            //需要订阅的表
//            connector.subscribe(".*\\..*");
            connector.subscribe("canal_test.user");
            connector.rollback();
            int totalEmptyCount = 120;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    redisDelete(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    redisInsert(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------&gt; before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------&gt; after");
                    redisUpdate(rowData.getAfterColumnsList());
                }
            }
        }
    }

    /**
     * 新增
     * @param columns
     */
    private static void  redisInsert(List<Column> columns){
        Jedis jedis = new Jedis("r-2ze4r1ddzycwxhkq9dpd.redis.rds.aliyuncs.com",6379);
        //2.指定访问redis的密码
        jedis.auth("Rendui123456!");
        //3.获得jedis客户端，可以向jdbc一样，访问我们的redis
        System.out.println(jedis.ping());
        //指定db4
        jedis.select(4);
        JSONObject jsonObject = new JSONObject();
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
            jsonObject.put(column.getName(),column.getValue());
        }
        jedis.set(columns.get(0).getValue(),jsonObject.toJSONString());
    }

    /**
     * 修改
     * @param columns
     */
    private static void  redisUpdate(List<Column> columns){
        Jedis jedis = new Jedis("r-2ze4r1ddzycwxhkq9dpd.redis.rds.aliyuncs.com",6379);
        //2.指定访问redis的密码
        jedis.auth("Rendui123456!");
        //3.获得jedis客户端，可以向jdbc一样，访问我们的redis
        System.out.println(jedis.ping());
        //指定db4
        jedis.select(4);
        JSONObject jsonObject = new JSONObject();
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
            jsonObject.put(column.getName(),column.getValue());
        }
        jedis.set(columns.get(0).getValue(),jsonObject.toJSONString());
    }

    /**
     * 删除
     * @param columns
     */
    private static void  redisDelete(List<Column> columns){
        Jedis jedis = new Jedis("r-2ze4r1ddzycwxhkq9dpd.redis.rds.aliyuncs.com",6379);
        //2.指定访问redis的密码
        jedis.auth("Rendui123456!");
        //3.获得jedis客户端，可以向jdbc一样，访问我们的redis
        System.out.println(jedis.ping());
        //指定db4
        jedis.select(4);
        JSONObject jsonObject = new JSONObject();
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
            jsonObject.put(column.getName(),column.getValue());
        }
        jedis.del(columns.get(0).getValue());
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
