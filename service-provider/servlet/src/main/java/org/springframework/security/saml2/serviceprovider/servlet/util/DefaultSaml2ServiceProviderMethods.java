/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.springframework.security.saml2.serviceprovider.servlet.util;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.saml2.Saml2ProviderNotFoundException;
import org.springframework.security.saml2.Saml2Transformer;
import org.springframework.security.saml2.model.Saml2Object;
import org.springframework.security.saml2.model.metadata.Saml2BindingType;
import org.springframework.security.saml2.model.metadata.Saml2Endpoint;
import org.springframework.security.saml2.provider.Saml2ServiceProviderInstance;
import org.springframework.security.saml2.provider.validation.Saml2ServiceProviderValidator;
import org.springframework.security.saml2.serviceprovider.registration.Saml2ServiceProviderResolver;

public class DefaultSaml2ServiceProviderMethods implements Saml2ServiceProviderMethods {

	private final Saml2Transformer transformer;
	private final Saml2ServiceProviderResolver resolver;
	private final Saml2ServiceProviderValidator validator;

	public DefaultSaml2ServiceProviderMethods(Saml2Transformer transformer,
											  Saml2ServiceProviderResolver resolver,
											  Saml2ServiceProviderValidator validator) {
		this.transformer = transformer;
		this.resolver = resolver;
		this.validator = validator;
	}

	@Override
	public Saml2Object getSamlRequest(HttpServletRequest request) {
		return parseSamlObject(request, getServiceProvider(request), "SAMLRequest");
	}

	@Override
	public Saml2Object getSamlResponse(HttpServletRequest request) {
		return parseSamlObject(request, getServiceProvider(request), "SAMLResponse");
	}

	@Override
	public Saml2ServiceProviderInstance getServiceProvider(HttpServletRequest request) {
		Saml2ServiceProviderInstance serviceProvider = getResolver().getServiceProvider(request);
		if (serviceProvider == null) {
			throw new Saml2ProviderNotFoundException("hosted");
		}
		return serviceProvider;
	}

	@Override
	public Saml2Transformer getTransformer() {
		return transformer;
	}

	@Override
	public Saml2ServiceProviderValidator getValidator() {
		return validator;
	}

	@Override
	public Saml2ServiceProviderResolver getResolver() {
		return resolver;
	}

	@Override
	public Saml2Endpoint getPreferredEndpoint(List<Saml2Endpoint> endpoints,
											  Saml2BindingType preferredBinding,
											  int preferredIndex) {
		return Saml2ServiceProviderUtils.getPreferredEndpoint(endpoints, preferredBinding, preferredIndex);
	}

	private Saml2Object parseSamlObject(HttpServletRequest request,
										Saml2ServiceProviderInstance provider,
										String parameterName) {
		return Saml2ServiceProviderUtils.parseSaml2Object(
			request.getParameter(parameterName),
			HttpMethod.GET.matches(request.getMethod()),
			provider,
			getTransformer(),
			getValidator()
		);
	}

}
