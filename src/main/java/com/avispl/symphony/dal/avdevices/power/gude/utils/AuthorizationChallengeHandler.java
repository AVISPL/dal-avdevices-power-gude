/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.avispl.symphony.dal.util.StringUtils;

/**
 * BasicScheme
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/25/2022
 * @since 1.0.0
 */
public class AuthorizationChallengeHandler {

	private final String username;
	private final String password;
	private final AtomicReference<String> authorizationPipeliningType = new AtomicReference<>();

	/**
	 * Creates an {@link AuthorizationChallengeHandler} using the {@code username} and {@code password} to respond to
	 * authentication challenges.
	 *
	 * @param username Username used to response to authorization challenges.
	 * @param password Password used to respond to authorization challenges.
	 * @throws NullPointerException If {@code username} or {@code password} are {@code null}.
	 */
	public AuthorizationChallengeHandler(String username, String password) {
		this.username = Objects.requireNonNull(username, "'username' cannot be null.");
		this.password = Objects.requireNonNull(password, "'password' cannot be null.");
	}

	/**
	 * Handles Basic authentication challenges.
	 *
	 * @return Authorization header for Basic authentication challenges.
	 */
	public final String handleBasic() {
		authorizationPipeliningType.set(DeviceConstant.BASIC);
		String token = username + ":" + password;
		return DeviceConstant.BASIC + Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Parses the {@code Authorization} or {@code Authentication} header into its key-value pairs.
	 * <p>
	 * This will remove quotes on quoted string values.
	 *
	 * @param header Authorization or Authentication header.
	 * @return The Authorization or Authentication header split into its key-value pairs.
	 */
	public Map<String, String> parseAuthenticationOrAuthorizationHeader(String header) {
		if (StringUtils.isNullOrEmpty(header)) {
			return Collections.emptyMap();
		}

		if (header.startsWith(DeviceConstant.BASIC) || header.startsWith(DeviceConstant.DIGEST)) {
			header = header.split(" ", 2)[1];
		}

		return Stream.of(header.split(","))
				.map(String::trim)
				.map(kvp -> kvp.split("=", 2))
				.collect(Collectors.toMap(kvpPieces -> kvpPieces[0].toLowerCase(Locale.ROOT),
						kvpPieces -> kvpPieces[1].replace("\"", "")));
	}

}

