package redmine.api.implementations;

import io.restassured.http.Header;
import lombok.Getter;
import redmine.api.interfaces.Response;
import redmine.utils.gson.GsonHelper;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class RestResponse implements Response {
    private final int statusCode;
    private final Map<String, String> headers;
    private final Object body;

    public RestResponse(int statusCode, Map<String, String> headers, Object body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public RestResponse(io.restassured.response.Response restAssuredResponse) {
        this.statusCode = restAssuredResponse.getStatusCode();
        this.headers = restAssuredResponse.getHeaders().asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
        this.body = restAssuredResponse.getBody().asString();
    }

    @Override
    public <T> T getBody(Class<T> clazz) {
        return GsonHelper.getGson().fromJson(body.toString(), clazz);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(statusCode).append(System.lineSeparator());
        headers.forEach((key, value) -> sb.append(key).append("=").append(value).append(System.lineSeparator()));
        sb.append(System.lineSeparator());
        if (body != null) {
            sb.append(body.toString());
        }
        return sb.toString();
    }
}
