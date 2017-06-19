package com.rain.app.service.riot.api.request.limit;
import com.rain.app.service.riot.api.RiotApiException;
public class RateLimitException extends RiotApiException {

	private static final long serialVersionUID = 8523329282717166886L;

	private final int retryAfter;
	private final String rateLimitType;

	/**
	 * Constructs a {@code RateLimitException} with the specified attributes.
	 * 
	 * @param retryAfter
	 *            The time in seconds to wait before the api key limits get refreshed
	 * @param rateLimitType
	 *            The type of rate limit that has been exceeded
	 */
	public RateLimitException(final int retryAfter, final String rateLimitType) {
		super(RATE_LIMITED, getMessage(RATE_LIMITED) + " (Retry After: " + retryAfter + ")");
		this.retryAfter = retryAfter;
		this.rateLimitType = rateLimitType;
	}

	/**
	 * Constructs a {@code RateLimitException} with the specified attributes.
	 * 
	 * @param message
	 *            Error message
	 * @param retryAfter
	 *            The time in seconds to wait before the api key limits get refreshed
	 * @param rateLimitType
	 *            The type of rate limit that has been exceeded
	 */
	protected RateLimitException(final String message, final int retryAfter, final String rateLimitType) {
		super(RATE_LIMITED, message);
		this.retryAfter = retryAfter;
		this.rateLimitType = rateLimitType;
	}

	/**
	 * Returns the type of rate limit that has been exceeded. This value can either be {@code user}, if the user's api key has reached its
	 * limits, or {@code service}, if Riot has reached some internal limits for that api.
	 * 
	 * @return The type of rate limit that has been exceeded
	 */
	public String getRateLimitType() {
		return rateLimitType;
	}

	/**
	 * Returns the time in seconds to wait before the api key limits get refreshed.
	 * 
	 * @return The time in seconds to wait before the api key limits get refreshed
	 */
	public int getRetryAfter() {
		return retryAfter;
	}
}
