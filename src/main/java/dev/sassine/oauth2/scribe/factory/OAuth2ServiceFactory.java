package dev.sassine.oauth2.scribe.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import dev.sassine.oauth2.scribe.dto.OAuth2ApiDTO;
import dev.sassine.oauth2.scribe.properties.OAuth2Properties;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2ServiceFactory {
	private static final String USER_AGENT = "SassineDev";
	private static Map<String, OAuth20Service> services = new HashMap<>();
	private final OAuth2Properties oAuth2Properties;

	public OAuth20Service getService(String serviceId) {
		if (!services.containsKey(serviceId))
			this.buildOAuthService(serviceId);

		return services.get(serviceId);

	}

	private void buildOAuthService(String serviceId) {
		OAuth2Properties.Registration registration = oAuth2Properties.getRegistration().get(serviceId);
		OAuth2Properties.Provider provider = oAuth2Properties.getProvider().get(serviceId);
		
		OAuth20Service oAuth20Service = new ServiceBuilder(registration.getClientId())
				.apiSecret(registration.getClientSecret()).callback(registration.getRedirectUri())
				.defaultScope(registration.getScope()).responseType(registration.getAuthorizationGrantType())
				.userAgent(USER_AGENT).build(new OAuth2ApiDTO(provider));
		services.put(serviceId, oAuth20Service);
	}

}
