package redmine.api.interfaces;

import java.util.Map;

public interface Response extends io.restassured.response.Response {
    int getStatusCode();

    Map<String, String> getHeaders();

    Object getBody();

    <T> T getBody(Class<T> clazz);
}
