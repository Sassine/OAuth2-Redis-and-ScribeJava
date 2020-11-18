package dev.sassine.oauth2.scribe.dto;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;

import dev.sassine.oauth2.scribe.properties.OAuth2Properties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2ApiDTO extends DefaultApi20 {

	private final OAuth2Properties.Provider provider;

	@Override
	public String getAccessTokenEndpoint() {
		return provider.getTokenUri();
	}

	@Override
	public String getAuthorizationBaseUrl() {
		return provider.getAuthorizationUri();
	}

	@Override
	public String getRevokeTokenEndpoint() {
		return provider.getRevokeTokenUri();
	}

	@Override
	public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
		if (provider.getName().equalsIgnoreCase("github")) 
			return OAuth2AccessTokenExtractor.instance();
		else
			return OAuth2AccessTokenJsonExtractor.instance();
	}

	public String getUserInfoEndpoint() {
		return provider.getUserInfoUri();
	}

	public String getUserNameAttribute() {
		return provider.getUserNameAttribute();
	}

	public String getRevokePermissionEndpoint() {
		return provider.getRevokePermissionUri();
	}
}