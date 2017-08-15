package com.git.toolbox.start;


import com.git.toolbox.rpc.CalculatorService;
import com.git.toolbox.rpc.CalculatorServiceHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import sun.misc.Signal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author w.vela <vela@longbeach-inc.com>
 * @date 2014年7月15日 上午9:52:42
 */
@SuppressWarnings("restriction")
public final class ThriftServerStarter {

    private static final int TIMEOUT = (int) TimeUnit.MINUTES.toMillis(1);

    private static final String TAP_NIC_NAME = "tap";

    private static final String NEI_SERVER_PREFIX_IP = "192.";

    private static final String IGNORE_PREFIX_IP = "10.0.3.";

    private static final int DEFAULT_MIN_WORKER_THREAD = 1;

    private static final int DEFAULT_MAX_WORKER_THREAD = 50;

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(ThriftServerStarter.class);

    private static Map<ThriftServer, Pair<String, TServer>> startedThriftServers = Collections
            .synchronizedMap(new HashMap<>());

    private static volatile Set<Runnable> postProcessors;

    public static final void start(ThriftServer thriftServer, TProcessor processor,
                                   int minWorkerThreads, int maxWorkerThreads) throws TTransportException, IOException,
            InterruptedException {
        // 获取本机IP地址先
        String ipAddress = getValidNeiIp();
        InetSocketAddress address = new InetSocketAddress(ipAddress, thriftServer.getPort());
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(address, TIMEOUT);
        THsHaServer server = new THsHaServer(new Args(serverTransport)
                .maxWorkerThreads(DEFAULT_MAX_WORKER_THREAD)
                .transportFactory(new TFramedTransport.Factory())
                .protocolFactory(new TCompactProtocol.Factory()).processor(processor));
        String serverLocate = ipAddress + ":" + thriftServer.getPort();
        startedThriftServers.put(thriftServer, Pair.of(serverLocate, server));

        Signal.handle(new Signal("TERM"), ThriftServerStarter::handle);
        server.serve();
    }



    public static final void start(ThriftServer thriftServer, TProcessor processor)
            throws TTransportException, IOException, InterruptedException {
        start(thriftServer, processor, DEFAULT_MIN_WORKER_THREAD, DEFAULT_MAX_WORKER_THREAD);
    }


    private static String getValidNeiIp() {
        Enumeration<NetworkInterface> nics;
        try {
            nics = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
        while (nics.hasMoreElements()) {
            NetworkInterface nic = nics.nextElement();

            // 如果有连接vpn，那么就要忽略掉vpn的网卡，目前好的办法就是取名字匹配，因为vpn都是用tap的驱动
            // 不是很好的办法，但是总比没有办法好
            if (StringUtils.containsIgnoreCase(nic.getName(), TAP_NIC_NAME)) {
                continue;
            }

            Enumeration<InetAddress> inetAddresses = nic.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                String hostAddress = inetAddress.getHostAddress();
                // 目前内网都是10.x.x.x开头，hard code一下好了，没想到别的好办法，目前
                if (hostAddress.startsWith(NEI_SERVER_PREFIX_IP)) {
                    if (hostAddress.startsWith(IGNORE_PREFIX_IP)) {
                        continue;
                    }
                    return hostAddress;
                }
            }
        }
        return null;
    }

    private static void handle(Signal sig) {
        try {
            for (Map.Entry<ThriftServer, Pair<String, TServer>> entry : startedThriftServers
                    .entrySet()) {
                try {
                    logger.info("success remove thrift server register:{}", entry);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(30));
                    TServer server = entry.getValue().getRight();
                    if (server.isServing()) {
                        server.stop();
                    }
                    logger.info("server stopped.");
                    if (postProcessors != null) {
                        logger.info("do post processing...");
                        postProcessors.forEach(Runnable::run);
                    }
                } catch (Throwable e) {
                    logger.error("fail to remove server setFlag:{}", entry, e);
                }
            }
        } catch (Throwable e) {
            logger.error("fail to term {}", startedThriftServers, e);
        } finally {
            logger.info("thrift servers:{} is shutdown.", startedThriftServers);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            ThriftServerStarter.start(ThriftServer.of("127.0.0.1:9090"),
                    new CalculatorService.Processor<>(new CalculatorServiceHandler()));
        } catch (TTransportException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}

