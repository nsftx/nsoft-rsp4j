package com.nsoft.api.security.jwt.verifier;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JWTClaimsSet {

    String getIssuer();

    String getSubject();

    List<String> getAudience();

    Date getExpirationTime();

    Date getNotBeforeTime();

    Date getIssueTime();

    String getJWTId();

    Map<String, Object> getClaims();

    Object getClaim(final String name);

    String getStringClaim(final String name) throws ParseException;

    String[] getStringArrayClaim(final String name) throws ParseException;

    List<String> getStringListClaim(final String name) throws ParseException;

    Boolean getBooleanClaim(final String name) throws ParseException;

    Integer getIntegerClaim(final String name) throws ParseException;

    Long getLongClaim(final String name) throws ParseException;

    Float getFloatClaim(final String name) throws ParseException;

    Double getDoubleClaim(final String name) throws ParseException;

    Date getDateClaim(final String name) throws ParseException;

    URI getURIClaim(final String name) throws ParseException;
}
