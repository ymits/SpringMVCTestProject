/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package my.cool.app.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import my.cool.app.service.Trade.TradeAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class TradeService implements ApplicationListener<BrokerAvailabilityEvent> {

	private static Log logger = LogFactory.getLog(TradeService.class);

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	private final TradeGenerator tradeGenerator = new TradeGenerator();

	private AtomicBoolean brokerAvailable = new AtomicBoolean();


	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		this.brokerAvailable.set(event.isBrokerAvailable());
	}

	@Scheduled(fixedDelay=2000)
	public void sendQuotes() {
		for (Trade trade : this.tradeGenerator.generateTrades()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Sending trade " + trade);
			}
			if (this.brokerAvailable.get()) {
				this.messagingTemplate.convertAndSendToUser(trade.getUsername(), "/queue/trade", trade);
			}
		}
	}


	private static class TradeGenerator {

		private static final MathContext mathContext = new MathContext(2);

		private final Random random = new Random();

		private final Map<String, String> trades = new ConcurrentHashMap<>();


		public TradeGenerator() {
			this.trades.put("user", "24.30");
			this.trades.put("admin", "13.03");
		}

		public Set<Trade> generateTrades() {
			Set<Trade> trades = new HashSet<>();
			for (String userName : this.trades.keySet()) {
				trades.add(getTrade(userName));
			}
			return trades;
		}

		private Trade getTrade(String userName) {
			BigDecimal seedPrice = new BigDecimal(this.trades.get(userName), mathContext);
			double range = seedPrice.multiply(new BigDecimal(0.02)).doubleValue();
			BigDecimal priceChange = new BigDecimal(String.valueOf(this.random.nextDouble() * range), mathContext);
			BigDecimal price = seedPrice.add(priceChange);
			
			Trade trade = new Trade();
			trade.setUsername(userName);
			trade.setTicker(price.toPlainString());
			trade.setShares(100);
			trade.setAction(TradeAction.Buy);
			return trade;
		}

	}

}
