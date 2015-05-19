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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

/**
 * TransactionTokenCheckで使用されるtokenを生成するクラス
 * 
 * @author mitsui0273
 *
 */
@Component
public class TokenStringGenerator {

	public static final String DEFAULT_ALGORITHM = "MD5";

	private final AtomicLong counter = new AtomicLong();

	private final String internalSeed;

	private final String algorithm;

	/**
	 * Constructor.
	 * <p>
	 * MD5がデフォルトで使用されます。
	 * </p>
	 */
	public TokenStringGenerator() {
		this(DEFAULT_ALGORITHM);
	}

	/**
	 * Constructor.
	 * 
	 * @param 使用したいアルゴリズム
	 * @throws IllegalArgumentException
	 *             アルゴリズムがnullまたは不正な場合
	 */
	public TokenStringGenerator(final String algorithm) {
		if (algorithm == null) {
			throw new IllegalArgumentException("algorithm must not be null");
		}
		try {
			MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(
					"The given algorithm is invalid. algorithm=" + algorithm, e);
		}
		this.algorithm = algorithm;
		this.internalSeed = Long.toHexString(new SecureRandom().nextLong());
	}

	/**
	 * ランダムなトークン文字列を生成して返します。
	 * 
	 * @return トークン文字列
	 */
	public String generate() {
		long time = System.currentTimeMillis();

		StringBuilder sb = new StringBuilder(1000);
		sb.append(internalSeed).append(time).append(counter.getAndIncrement());

		MessageDigest md = createMessageDigest();
		md.update(sb.toString().getBytes());
		byte[] bytes = md.digest();
		return toHexString(bytes);
	}

	private MessageDigest createMessageDigest() {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private String toHexString(final byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			String s = Integer.toHexString(b & 0xff);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s);
		}
		return sb.toString();
	}
}
