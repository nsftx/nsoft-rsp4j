package com.nsoft.api.security.jwt.verifier.internal;

import com.nsoft.api.security.jwt.verifier.JWTClaimsSet;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class ImmutableJWTClaimsSet implements JWTClaimsSet {

    private final com.nimbusds.jwt.JWTClaimsSet claimsSet;

    public ImmutableJWTClaimsSet(final com.nimbusds.jwt.JWTClaimsSet claimsSet) {
        this.claimsSet = claimsSet;
    }

    @Override
    public String getIssuer() {
        return claimsSet.getIssuer();
    }

    @Override
    public String getSubject() {
        return claimsSet.getSubject();
    }

    @Override
    public List<String> getAudience() {
        return claimsSet.getAudience();
    }

    @Override
    public Date getExpirationTime() {
        return claimsSet.getExpirationTime();
    }

    @Override
    public Date getNotBeforeTime() {
        return claimsSet.getNotBeforeTime();
    }

    @Override
    public Date getIssueTime() {
        return claimsSet.getIssueTime();
    }

    public String getJWTId() {
        return claimsSet.getJWTID();
    }

    @Override
    public Object getClaim(String name) {
        return claimsSet.getClaim(name);
    }

    @Override
    public String getStringClaim(String name) throws ParseException {
        return claimsSet.getStringClaim(name);
    }

    @Override
    public String[] getStringArrayClaim(String name) throws ParseException {
        return claimsSet.getStringArrayClaim(name);
    }

    @Override
    public List<String> getStringListClaim(String name) throws ParseException {
        return claimsSet.getStringListClaim(name);
    }

    @Override
    public URI getURIClaim(String name) throws ParseException {
        return claimsSet.getURIClaim(name);
    }

    @Override
    public Boolean getBooleanClaim(String name) throws ParseException {
        return claimsSet.getBooleanClaim(name);
    }

    @Override
    public Integer getIntegerClaim(String name) throws ParseException {
        return claimsSet.getIntegerClaim(name);
    }

    @Override
    public Long getLongClaim(String name) throws ParseException {
        return claimsSet.getLongClaim(name);
    }

    @Override
    public Date getDateClaim(String name) throws ParseException {
        return claimsSet.getDateClaim(name);
    }

    @Override
    public Float getFloatClaim(String name) throws ParseException {
        return claimsSet.getFloatClaim(name);
    }

    @Override
    public Double getDoubleClaim(String name) throws ParseException {
        return claimsSet.getDoubleClaim(name);
    }

    @Override
    public Map<String, Object> getClaims() {
        return claimsSet.getClaims();
    }

}
