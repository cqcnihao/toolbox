/**
 * 
 */
package com.git.toolbox.thrift.client.impl;

import com.git.toolbox.thrift.client.ThriftClient;
import com.git.toolbox.thrift.client.pool.ThriftConnectionPoolProvider;
import com.git.toolbox.thrift.client.pool.ThriftServerInfo;
import com.git.toolbox.thrift.client.pool.impl.DefaultThriftConnectionPoolImpl;
import com.git.toolbox.thrift.client.utils.FailoverCheckingStrategy;
import com.git.toolbox.thrift.client.utils.ThriftClientUtils;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * FailoverThriftClientImpl class.
 * </p>
 */
public class FailoverThriftClientImpl implements ThriftClient {

    private final ThriftClient thriftClient;

    /**
     * <p>
     * Constructor for FailoverThriftClientImpl.
     * </p>
     *
     * @param serverInfoProvider a {@link Supplier}
     *        object.
     */
    public FailoverThriftClientImpl(Supplier<List<ThriftServerInfo>> serverInfoProvider) {
        this(new FailoverCheckingStrategy<>(), serverInfoProvider, DefaultThriftConnectionPoolImpl
                .getInstance());
    }

    /**
     * <p>
     * Constructor for FailoverThriftClientImpl.
     * </p>
     *
     */
    public FailoverThriftClientImpl(
            FailoverCheckingStrategy<ThriftServerInfo> failoverCheckingStrategy,
            Supplier<List<ThriftServerInfo>> serverInfoProvider,
            ThriftConnectionPoolProvider poolProvider) {
        FailoverStategy failoverStategy = new FailoverStategy(serverInfoProvider, poolProvider,
                failoverCheckingStrategy);
        this.thriftClient = new ThriftClientImpl(failoverStategy, failoverStategy);
    }

    /** {@inheritDoc} */
    @Override
    public <X extends TServiceClient> X iface(Class<X> ifaceClass) {
        return thriftClient.iface(ifaceClass);
    }


    /** {@inheritDoc} */
    @Override
    public <X extends TServiceClient> X iface(Class<X> ifaceClass, int hash) {
        return thriftClient.iface(ifaceClass, hash);
    }


    /** {@inheritDoc} */
    @Override
    public <X extends TServiceClient> X iface(Class<X> ifaceClass,
            Function<TTransport, TProtocol> protocolProvider, int hash) {
        return thriftClient.iface(ifaceClass, protocolProvider, hash);
    }

    /** {@inheritDoc} */
    @Override
    public <X extends TServiceClient> X mpiface(Class<X> ifaceClass, String serviceName) {
        return thriftClient.mpiface(ifaceClass, serviceName, ThriftClientUtils.randomNextInt());
    }

    /** {@inheritDoc} */
    @Override
    public <X extends TServiceClient> X mpiface(Class<X> ifaceClass, String serviceName, int hash) {
        return thriftClient.mpiface(ifaceClass, serviceName, TBinaryProtocol::new, hash);
    }

    /** {@inheritDoc} */
    @Override
    public <X extends TServiceClient> X mpiface(Class<X> ifaceClass, String serviceName,
        Function<TTransport, TProtocol> protocolProvider, int hash) {
        return thriftClient.mpiface(ifaceClass, serviceName, protocolProvider, hash);
    }


    private class FailoverStategy implements
                                 Supplier<List<ThriftServerInfo>>,
                                 ThriftConnectionPoolProvider {

        private final Supplier<List<ThriftServerInfo>> originalServerInfoProvider;

        private final ThriftConnectionPoolProvider connectionPoolProvider;

        private final FailoverCheckingStrategy<ThriftServerInfo> failoverCheckingStrategy;

        private FailoverStategy(Supplier<List<ThriftServerInfo>> originalServerInfoProvider,
                ThriftConnectionPoolProvider connectionPoolProvider,
                FailoverCheckingStrategy<ThriftServerInfo> failoverCheckingStrategy) {
            this.originalServerInfoProvider = originalServerInfoProvider;
            this.connectionPoolProvider = connectionPoolProvider;
            this.failoverCheckingStrategy = failoverCheckingStrategy;
        }

        /* (non-Javadoc)
         * @see java.util.function.Supplier#get()
         */
        @Override
        public List<ThriftServerInfo> get() {
            Set<ThriftServerInfo> failedServers = failoverCheckingStrategy.getFailed();
            return originalServerInfoProvider.get().stream()
                    .filter(i -> !failedServers.contains(i)).collect(toList());
        }

        @Override
        public TTransport getConnection(ThriftServerInfo thriftServerInfo) {
            return connectionPoolProvider.getConnection(thriftServerInfo);
        }

        @Override
        public void returnConnection(ThriftServerInfo thriftServerInfo, TTransport transport) {
            connectionPoolProvider.returnConnection(thriftServerInfo, transport);
        }

        @Override
        public void returnBrokenConnection(ThriftServerInfo thriftServerInfo, TTransport transport) {
            failoverCheckingStrategy.fail(thriftServerInfo);
            connectionPoolProvider.returnBrokenConnection(thriftServerInfo, transport);
        }
    }

}
