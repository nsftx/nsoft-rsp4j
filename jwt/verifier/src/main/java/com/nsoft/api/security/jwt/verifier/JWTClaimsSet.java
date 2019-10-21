package com.nsoft.api.security.jwt.verifier;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A JWT Claims Set abstraction used to access stored claims
 *
 * @author Mislav Milicevic
 * @since 2019-10-21
 */
public interface JWTClaimsSet {

    /**
     * Retrieves the issuer claim
     *
     * @return issuer claim
     */
    String getIssuer();

    /**
     * Retrieves the subject claim
     *
     * @return subject claim
     */
    String getSubject();

    /**
     * Retrieves the audience claim
     *
     * @return audience claim
     */
    List<String> getAudience();

    /**
     * Retrieves the expiration time claim
     *
     * @return expiration time claim
     */
    Date getExpirationTime();

    /**
     * Retrieves the not before time claim
     *
     * @return not before time claim
     */
    Date getNotBeforeTime();

    /**
     * Retrieves the issue time claim
     *
     * @return issue time claim
     */
    Date getIssueTime();

    /**
     * Retrieves the JWT id claim
     *
     * @return JWT id claim
     */
    String getJWTId();

    /**
     * Retrieves an immutable map of all present claims
     *
     * @return claim map
     */
    Map<String, Object> getClaims();

    /**
     * Retrieves a claim based on the specified name. If a claim with the specified name doesn't
     * exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     */
    Object getClaim(final String name);

    /**
     * Retrieves a claim as a {@link String} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link String}
     */
    String getStringClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link String[]} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link String[]}
     */
    String[] getStringArrayClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link List<String>} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link List<String>}
     */
    List<String> getStringListClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link Boolean} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link Boolean}
     */
    Boolean getBooleanClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link Integer} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link Integer}
     */
    Integer getIntegerClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link Long} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link Long}
     */
    Long getLongClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link Float} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link Float}
     */
    Float getFloatClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link Double} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link Double}
     */
    Double getDoubleClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link Date} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link Date}
     */
    Date getDateClaim(final String name) throws ParseException;

    /**
     * Retrieves a claim as a {@link URI} based on the specified name. If a claim with the
     * specified name doesn't exist, {@code null} will be returned
     *
     * @param name claim name
     * @return claim based on specified name, {@code null} if the claim doesn't exist
     * @throws ParseException if the claim is not a {@link URI}
     */
    URI getURIClaim(final String name) throws ParseException;
}
