package com.git.toolbox.start;

import com.google.common.base.Splitter;
import com.google.common.collect.MapMaker;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by poan on 2017/08/15.
 */
public final class ThriftServer {


    /**
     * key:ip+port
     * value:ThriftServerInfo
     * value设为弱引用
     */
    private static ConcurrentMap<String, ThriftServer> allInfos = new MapMaker().weakValues()
            .makeMap();

    private static Splitter splitter = Splitter.on(':');

    private final String host;

    private final int port;

    /**
     * <p>
     * Constructor for ThriftServerInfo.
     * </p>
     */
    private ThriftServer(String hostAndPort) {
        List<String> split = splitter.splitToList(hostAndPort);
        assert split.size() == 2;
        this.host = split.get(0);
        this.port = Integer.parseInt(split.get(1));
    }

    public static ThriftServer of(String host, int port) {
        return allInfos.computeIfAbsent(host + ":" + port, ThriftServer::new);
    }

    public static ThriftServer of(String hostAndPort) {
        return allInfos.computeIfAbsent(hostAndPort, ThriftServer::new);
    }

    /**
     * <p>
     * Getter for the field <code>host</code>.
     * </p>
     */
    public String getHost() {
        return host;
    }

    /**
     * <p>
     * Getter for the field <code>port</code>.
     * </p>
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof com.git.toolbox.thrift.client.pool.ThriftServerInfo)) {
            return false;
        }
        ThriftServer other = (ThriftServer) obj;
        if (host == null) {
            if (other.host != null) {
                return false;
            }
        } else if (!host.equals(other.host)) {
            return false;
        }
        return port == other.port;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ThriftServerInfo [host=" + host + ", port=" + port + "]";
    }

}
