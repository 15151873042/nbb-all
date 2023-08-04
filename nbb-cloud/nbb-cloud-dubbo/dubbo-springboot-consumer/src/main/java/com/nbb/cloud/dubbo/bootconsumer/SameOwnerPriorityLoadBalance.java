package com.nbb.cloud.dubbo.bootconsumer;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.context.Lifecycle;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * owner标识相同，或含有owner标识优先调用
 */

public class SameOwnerPriorityLoadBalance extends AbstractLoadBalance implements Lifecycle{

    private String owner;


    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        /*
            此处不能通过url.getParameter("dubbo.application.owner")获取当前消费者的owner信息
            因为consumer会将provider的parameter合并，所以consumer不配置owner也可能会取到owner，详细信息见：
            org.apache.dubbo.registry.integration.RegistryDirectory.mergeUrl方法
         */

        Invoker<T> randomInvoker = invokers.get(new Random().nextInt(invokers.size()));

        if (StringUtils.isBlank(owner)) {
            // 消费者无owner标识，优先获取含有owner标识的提供者
            // 场景：注册中心无本地服务时，调用本地服务
            List<Invoker<T>> hasOwnerInvokers = invokers.stream()
                    .filter(invoker -> StringUtils.isNotBlank(invoker.getUrl().getParameter("owner")))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(hasOwnerInvokers)) {
                return randomInvoker;
            }
            return hasOwnerInvokers.get(new Random().nextInt(hasOwnerInvokers.size()));
        } else {
            // 消费者有owner标识，优先获取具有相同owner标识的提供者
            // 场景：本地两个服务优先相互调用
            return invokers.stream()
                    .filter(invoker -> owner.equals(invoker.getUrl().getParameter("owner")))
                    .findFirst()
                    .orElse(randomInvoker);
        }
    }

    @Override
    public void initialize() throws IllegalStateException {
        this.owner = SpringUtil.getProperty("dubbo.application.owner");
    }

    @Override
    public void start() throws IllegalStateException {

    }

    @Override
    public void destroy() throws IllegalStateException {

    }
}
