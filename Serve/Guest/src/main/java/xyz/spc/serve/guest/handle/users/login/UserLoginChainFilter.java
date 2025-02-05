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

package xyz.spc.serve.guest.handle.users.login;


import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.serve.auxiliary.config.design.chain.AbstractChainHandler;
import xyz.spc.serve.guest.common.enums.UsersChainMarkEnum;

/**
 * 用户登录责任链过滤器
 */
public interface UserLoginChainFilter<T extends User, Y extends UserDTO> extends AbstractChainHandler<T, Y> {

    @Override
    default String mark() {
        return UsersChainMarkEnum.USER_LOGIN_FILTER.name();
    }
}
