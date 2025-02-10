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

package xyz.spc.serve.auxiliary.config.design;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.spc.serve.auxiliary.config.boot.ApplicationBaseAutoConfiguration;
import xyz.spc.serve.auxiliary.config.design.chain.AbstractChainContext;

/**
 * 设计模式自动装配
 */
@Slf4j
@Component
@ImportAutoConfiguration(ApplicationBaseAutoConfiguration.class)
public class DesignPatternAutoConfiguration<T, Y> {


    /**
     * 责任链上下文
     */
    @Bean
    public AbstractChainContext<T, Y> abstractChainContext() {
        log.debug("责任链上下文装载成功");
        return new AbstractChainContext<>();
    }
}
