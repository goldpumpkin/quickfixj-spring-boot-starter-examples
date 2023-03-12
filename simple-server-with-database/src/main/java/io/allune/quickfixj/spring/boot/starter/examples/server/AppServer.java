/*
 * Copyright 2017-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.allune.quickfixj.spring.boot.starter.examples.server;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJServer;
import io.allune.quickfixj.spring.boot.starter.examples.server.mock.FromAppMsgHolder;
import io.allune.quickfixj.spring.boot.starter.examples.server.mock.NewHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.*;
import quickfix.field.MsgType;

import javax.sql.DataSource;

@Slf4j
@EnableQuickFixJServer
@SpringBootApplication
public class AppServer {

	public static void main(String[] args) {
		SpringApplication.run(AppServer.class, args);
	}

	@Bean
	public Application serverApplication(FromAppMsgHolder fromAppMsgHolder) {
		final ServerApplicationAdapter serverApplicationAdapter = new ServerApplicationAdapter();
		serverApplicationAdapter.setFromAppMsgHolder(fromAppMsgHolder);
		return serverApplicationAdapter;
	}

	@Bean
	public Acceptor serverAcceptor(quickfix.Application serverApplication, MessageStoreFactory serverMessageStoreFactory,
								   SessionSettings serverSessionSettings, LogFactory serverLogFactory,
								   MessageFactory serverMessageFactory) throws ConfigError {

		return new ThreadedSocketAcceptor(serverApplication, serverMessageStoreFactory, serverSessionSettings,
			serverLogFactory, serverMessageFactory);
	}

	@Bean
	public MessageStoreFactory serverMessageStoreFactory(SessionSettings serverSessionSettings, DataSource dataSource) {
		JdbcStoreFactory jdbcStoreFactory = new JdbcStoreFactory(serverSessionSettings);
		jdbcStoreFactory.setDataSource(dataSource);
		return jdbcStoreFactory;
	}

	@Bean
	public LogFactory serverLogFactory(SessionSettings serverSessionSettings, DataSource dataSource) {
		JdbcLogFactory jdbcLogFactory = new JdbcLogFactory(serverSessionSettings);
		jdbcLogFactory.setDataSource(dataSource);
		return jdbcLogFactory;
	}

	@Bean
	public FromAppMsgHolder fromAppMsgHolder(NewHandler newHandler) {
		final FromAppMsgHolder holder = new FromAppMsgHolder();
		holder.set(new MsgType(MsgType.ORDER_SINGLE), newHandler);
		return holder;
	}
}
