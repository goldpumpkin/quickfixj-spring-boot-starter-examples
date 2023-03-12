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
package io.allune.quickfixj.spring.boot.starter.examples.server.mock;

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix42.ExecutionReport;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class NewHandler implements FromAppMessageHandler {

	public final QuickFixJTemplate quickFixJTemplate;

	public static final MsgType HANDLE_MSG_TYPE = new MsgType(MsgType.ORDER_SINGLE);

	@Override
	public void handle(Message message, SessionID sessionId) throws FieldNotFound {
		log.info("[NewHandle] handle message:{}", message);
		final Message.Header header = message.getHeader();
		final StringField msgTypeStr = header.getField(new MsgType());
		if (!StringUtils.equals(msgTypeStr.getValue(), HANDLE_MSG_TYPE.getValue())) {
			return;
		}

		final StringField clOrdId = message.getField(new ClOrdID());
		final CharField ordType = message.getField(new OrdType());
		final StringField securityType = message.getField(new SecurityType());
		final CharField side = message.getField(new Side());
		final DoubleField price = message.getField(new Price());
		final DoubleField stopPx = message.getField(new StopPx());
		final DoubleField orderQty = message.getField(new OrderQty());
		final StringField symbol = message.getField(new Symbol());

		final quickfix.fix42.Message execution = new ExecutionReport();
		execution.setField(new ExecID(UUID.randomUUID().toString()));
		execution.setField(new ExecTransType(ExecTransType.NEW));
		execution.setField(new ExecType(ExecType.NEW));
		execution.setField(new OrdStatus(OrdStatus.NEW));
		execution.setField(symbol);
		execution.setField(clOrdId);
		execution.setField(new OrderID(UUID.randomUUID().toString()));
		execution.setField(ordType);
		execution.setField(securityType);
		execution.setField(side);
		execution.setField(price);
		execution.setField(stopPx);
		execution.setField(orderQty);
		execution.setField(new LeavesQty(BigDecimal.valueOf(100).doubleValue()));
		execution.setField(new CumQty(BigDecimal.valueOf(100).doubleValue()));
		execution.setField(new AvgPx(BigDecimal.valueOf(0).doubleValue()));

		quickFixJTemplate.setDoValidation(false);
		quickFixJTemplate.send(execution, sessionId);
	}

	@Override
	public MsgType handleType(Message message) {
		return HANDLE_MSG_TYPE;
	}
}
