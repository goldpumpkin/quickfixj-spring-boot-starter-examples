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
package io.allune.quickfixj.spring.boot.starter.examples.client;

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;

import java.util.ArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class MsgSender {

	private final Initiator initiator;
	public final QuickFixJTemplate quickFixJTemplate;

	public void send(Message message) throws SessionNotFound {
		final ArrayList<SessionID> sessions = initiator.getSessions();
		final SessionID sessionId = sessions.get(0);
		final Session session = Session.lookupSession(sessionId);

		quickFixJTemplate.setDoValidation(false);
		boolean send = quickFixJTemplate.send(message, sessionId);
		log.info("[MsgSender] sender session:{}, message:{}, raw:{}", session, message, message.toRawString());
		log.info("[MsgSender] send:{}", send);
	}


}
