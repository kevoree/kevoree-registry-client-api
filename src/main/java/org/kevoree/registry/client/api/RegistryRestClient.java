package org.kevoree.registry.client.api;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RegistryRestClient {
	private final String serverPath;

	public RegistryRestClient(final String serverPath, final String accessToken) {
		super();
		this.serverPath = serverPath;
		this.accessToken = accessToken;
	}

	private final String accessToken;

	public HttpResponse<JsonNode> submitDU(final String namespace, final String tdefName, final String tdefVersion,
			final String platform, final String model, final String duName, final String duVersion)
			throws UnirestException {
		final HttpResponse<JsonNode> res = Unirest
				.post(serverPath + "/api/namespaces/{namespace}/tdefs/{tdefName}/{tdefVersion}/dus")
				.routeParam("namespace", namespace).routeParam("tdefName", tdefName)
				.routeParam("tdefVersion", tdefVersion).header("Content-Type", "application/json;charset=UTF-8")
				.header("Accept", "application/json")

				.header("Authorization", "Bearer " + accessToken).body(new JSONObject().put("model", model)
						.put("name", duName).put("platform", platform).put("version", duVersion))
				.asJson();
		return res;
	}

	public void postNamespace(final String namespace) throws UnirestException {
		Unirest.post(serverPath + "/api/namespaces").header("Content-Type", "application/json;charset=UTF-8")
				.header("Accept", "application/json").header("Authorization", "Bearer " + accessToken)
				.body(new JSONObject().put("name", namespace)).asJson();
	}

	public void postTypeDef(final String namespace, final String model, final String typeDefName,
			final String typeDefVersion) throws UnirestException {
		Unirest.post(serverPath + "/api/namespaces/{namespace}/tdefs").routeParam("namespace", namespace)
				.header("Content-Type", "application/json;charset=UTF-8").header("Accept", "application/json")
				.header("Authorization", "Bearer " + accessToken)
				.body(new JSONObject().put("name", typeDefName).put("version", typeDefVersion).put("model", model))
				.asJson();
	}
}
