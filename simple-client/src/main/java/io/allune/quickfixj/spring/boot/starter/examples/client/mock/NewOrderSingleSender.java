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
package io.allune.quickfixj.spring.boot.starter.examples.client.mock;

import io.allune.quickfixj.spring.boot.starter.examples.client.MsgSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix42.Message;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class NewOrderSingleSender {

	private final MsgSender msgSender;

	public void send() throws SessionNotFound {
		final String clOrdId = "gold001";

		final Message message = new Message();
		message.setField(new ClOrdID(clOrdId));
		message.setField(new OrdType(OrdType.LIMIT));
		message.setField(new SecurityType(SecurityType.COMMON_STOCK));
		message.setField(new Side(Side.BUY));
		message.setField(new Price(BigDecimal.valueOf(0.001).doubleValue()));
		message.setField(new OrderQty(BigDecimal.valueOf(100.12).doubleValue()));

		msgSender.send(message);
	}


}
