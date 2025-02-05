/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.spc.serve.auxiliary.config.design.chain;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;
import xyz.spc.common.funcpack.commu.exception.ServiceException;
import xyz.spc.serve.auxiliary.config.boot.ApplicationContextHolder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象责任链上下文
 */
@Slf4j
public final class AbstractChainContext<T, Y> implements CommandLineRunner {

    /**
     * 责任链组件容器
     */
    private final Map<String, List<AbstractChainHandler<T, Y>>> abstractChainHandlerContainer = Maps.newHashMap();

    /**
     * 责任链组件执行
     *
     * @param mark 责任链组件标识
     * @param o1   参数1
     * @param o2   参数2
     */
    public void handler(String mark, T o1, Y o2) {
        List<AbstractChainHandler<T, Y>> abstractChainHandlers = abstractChainHandlerContainer.get(mark);
        if (CollectionUtils.isEmpty(abstractChainHandlers)) {
            throw new ServiceException(String.format("[%s] Chain of Responsibility ID is undefined.", mark));
        }
        abstractChainHandlers.forEach(each -> each.handler(o1, o2));
        log.debug("责任链组件执行完毕");
    }

    /**
     * 责任链运行
     */
    @Override
    public void run(String... args) {
        Map<String, AbstractChainHandler> chainFilterMap = ApplicationContextHolder
                .getBeansOfType(AbstractChainHandler.class);
        chainFilterMap.forEach((beanName, bean) -> {
            List<AbstractChainHandler<T, Y>> abstractChainHandlers = abstractChainHandlerContainer.get(bean.mark());
            if (CollectionUtils.isEmpty(abstractChainHandlers)) {
                abstractChainHandlers = new ArrayList<>();
            }
            abstractChainHandlers.add(bean);
            List<AbstractChainHandler<T, Y>> actualAbstractChainHandlers = abstractChainHandlers.stream()
                    .sorted(Comparator.comparing(Ordered::getOrder))
                    .collect(Collectors.toList());
            abstractChainHandlerContainer.put(bean.mark(), actualAbstractChainHandlers);
        });
    }
}
