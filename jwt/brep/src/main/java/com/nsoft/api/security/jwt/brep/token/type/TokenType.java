package com.nsoft.api.security.jwt.brep.token.type;

import com.nsoft.api.security.jwt.brep.token.TokenIdentifier;

/**
 * @author Mislav Milicevic
 * @since 2020-01-10
 */
public enum TokenType implements TokenIdentifier {
    /**
     * Represents an Identity token type
     */
    IDENTITY,
    /**
     * Represents an Access token type
     */
    ACCESS;

    @Override
    public boolean matches(final TokenIdentifier tokenIdentifier) {
        if (!(tokenIdentifier instanceof TokenType)) {
            return false;
        }

        TokenType type = (TokenType) tokenIdentifier;
        return equals(type);
    }
}
