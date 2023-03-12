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

import com.google.common.collect.Maps;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.StringField;
import quickfix.field.MsgType;

import java.util.Map;

public class FromAppMsgHolder {

	private static final Map<String, FromAppMessageHandler> HOLDER = Maps.newHashMap();

	public void set(MsgType msgType, FromAppMessageHandler handler) {
		final String type = msgType.getValue();
		HOLDER.put(type, handler);
	}

	public void handle(Message message, SessionID sessionId) throws FieldNotFound {
		final StringField msgType = message.getField(new MsgType());
		final FromAppMessageHandler fromAppMessageHandler = HOLDER.get(msgType.getValue());
		fromAppMessageHandler.handle(message, sessionId);
	}

}
