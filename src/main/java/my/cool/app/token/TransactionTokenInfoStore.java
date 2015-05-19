/*
 * Copyright (C) 2013-2015 terasoluna.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package my.cool.app.token;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * トランザクショントークン設定情報の格納クラス
 * 
 * @author mitsui0273
 *
 */
@Component
public class TransactionTokenInfoStore {

	/**
	 * TransactionTokenInfo のキャッシュ
	 */
	private ConcurrentMap<Method, TransactionTokenInfo> tokenInfoCache = new ConcurrentHashMap<Method, TransactionTokenInfo>();

	/**
	 * コンストラクタ
	 */
	public TransactionTokenInfoStore() {
	}

	/**
	 * 渡されたhandlerMethodに紐づくTransactionTokenInfoインスタンスを返します。
	 * <p>
	 * もしTransactionTokenInfoインスタンスが存在しなければ、新しいインスタンスを生成してキャッシュにのせます。
	 * </p>
	 * 
	 * @param handlerMethod
	 * @return TransactionTokenInfo
	 */
	public TransactionTokenInfo getTransactionTokenInfo(
			final HandlerMethod handlerMethod) {
		Method method = handlerMethod.getMethod();
		TransactionTokenInfo info = tokenInfoCache.get(method);
		if (info == null) {
			synchronized (tokenInfoCache) {
				info = tokenInfoCache.get(method);
				if (info == null) {
					info = createTransactionTokenInfo(handlerMethod);
					tokenInfoCache.put(method, info);
				}
			}
		}
		return info;
	}

	/**
	 * 渡したhandlerMethodのアノテーション情報に基づいてTransactionTokenInfoインスタンスを生成して返します。
	 * 
	 * @param handlerMethod
	 * @return TransactionTokenInfo
	 */
	TransactionTokenInfo createTransactionTokenInfo(
			final HandlerMethod handlerMethod) {

		TransactionTokenCheck methodAnnotation = handlerMethod
				.getMethodAnnotation(TransactionTokenCheck.class);

		if (methodAnnotation == null) {
			return new TransactionTokenInfo(null, TransactionTokenType.NONE);
		}

		return new TransactionTokenInfo(methodAnnotation.value(),
				methodAnnotation.type());
	}

}
