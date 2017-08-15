package com.git.toolbox.thrift;

import com.git.toolbox.rpc.CalculatorService;
import com.git.toolbox.thrift.client.ThriftClient;
import com.git.toolbox.thrift.client.impl.ThriftClientImpl;
import com.git.toolbox.thrift.client.pool.ThriftServerInfo;
import org.apache.thrift.TException;

import java.util.Arrays;

/**
 * Created by poan on 2017/08/15.
 */
public class ThriftTest {

    public static void main(String[] args) {
        ThriftClient thriftClient = new ThriftClientImpl(() -> Arrays.asList(//
                ThriftServerInfo.of("192.168.30.249", 9090) //

                // or you can return a dynamic result.
        ));
        // get iface and call
        try {
            System.out.println(thriftClient.iface(CalculatorService.Client.class).add(1,2));
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}