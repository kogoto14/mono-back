package jp.co.monocrea.user.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.resource.request.UserCreateRequest;
import jp.co.monocrea.user.resource.request.UserUpdateRequest;
import jp.co.monocrea.user.resource.response.PagedUserSummariesResponse;
import jp.co.monocrea.user.resource.response.UserDetailResponse;
import jp.co.monocrea.user.service.UserService;

@Path("/users")
public class UserResource {
    
    @Inject
    UserService userService;

    @GET
    public Response getPagenatedUsers(
        @QueryParam("id") @Positive Long id,
        @QueryParam("_name_like") String nameLike,
        @QueryParam("_sort") @Pattern(regexp = "id|name") String sortKey,
        @QueryParam("_order") @DefaultValue("ASC") OrderEnum order,
        @QueryParam("_page")  @Positive @DefaultValue("1") Integer page,
        @QueryParam("_limit") @Positive @DefaultValue("5") @Min(1) @Max(100) Integer limit
    ) {        
        PagedUserSummariesResponse userSummaries = userService.getPagenatedUserSummaries(
            id,
            nameLike,
            sortKey,
            order,
            page,
            limit
        );
        return Response
                .ok(userSummaries.getUsers().getUsers())
                .header("X-Total-Count", userSummaries.getTotalCount())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") @Positive Long id) {
        UserDetailResponse userDetail = userService.getUserDetailById(id);
        return Response.ok(userDetail).build();
    }

    @POST
    public Response createUser(@Valid UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest.convertToUserCreateCommand());
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") @Positive Long id, @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest.convertToUserUpdateCommand(id));
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") @Positive Long id) {
        userService.deleteUser(id);
        return Response.ok().build();
    }
}
