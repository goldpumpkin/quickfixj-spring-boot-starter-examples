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

import io.allune.quickfixj.spring.boot.starter.examples.client.mock.NewOrderSingleSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/fix")
@RestController
@AllArgsConstructor
public class TestController {

	private final NewOrderSingleSender newOrderSingleSender;

	@GetMapping("/test")
	public String test() throws Exception {
		log.info("test");
		return "success";
	}

	@GetMapping("/new-order-single")
	public String newOrderSingle() throws Exception {
		newOrderSingleSender.send();
		return "new order single";
	}


}
