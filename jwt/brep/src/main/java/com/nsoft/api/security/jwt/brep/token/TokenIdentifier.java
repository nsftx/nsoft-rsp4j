package com.nsoft.api.security.jwt.brep.token;

/**
 * Provides a mean of identifying different types of incoming tokens. The default implementation for
 * the {@link TokenIdentifier} interface provided by bRep is {@link com.nsoft.api.security.jwt.brep.token.type.TokenType}
 *
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public interface TokenIdentifier {

    boolean matches(final TokenIdentifier tokenIdentifier);
}
