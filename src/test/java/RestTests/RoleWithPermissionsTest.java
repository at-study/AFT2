package RestTests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.Dto.RoleDto;
import redmine.model.role.Role;
import redmine.model.role.RolePermission;
import redmine.model.role.RolePermissions;
import redmine.model.user.User;

import java.util.stream.Collectors;

import static redmine.model.role.RolePermission.*;

public class RoleWithPermissionsTest {
    User user;
    Role role;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
        RolePermissions permissions = new RolePermissions(CLOSE_PROJECT, ADD_SUBPROJECTS, ADD_DOCUMENTS);
        role = new Role().setPermissions(permissions).generate();
    }

    @Test(testName = "Тест получения роли  с доступами по ид ")
    public void getRoleWithPermissionsByIdTest() {
        ApiClient apiClient = new RestApiClient(user);
        String uri = String.format("roles/%d.json", role.getId());
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(), 200);
        RoleDto roleDto = response.getBody(RoleDto.class);
        Assert.assertEquals(roleDto.getRole().getId(), role.getId());
        Assert.assertEquals(roleDto.getRole().getName(), role.getName());
        Assert.assertEquals(roleDto.getRole().getAssignable(), role.getAssignable());
        Assert.assertEquals(roleDto.getRole().getIssues_visibility(), role.getIssuesVisibility().toString());
        Assert.assertEquals(roleDto.getRole().getUsers_visibility(), role.getUsersVisibility().toString());
        Assert.assertEquals(roleDto.getRole().getPermissions().size(), 3);
        Assert.assertEquals(roleDto.getRole().getPermissions(),
                role.getPermissions().stream().map(RolePermission::toString).collect(Collectors.toList()));
    }
}
