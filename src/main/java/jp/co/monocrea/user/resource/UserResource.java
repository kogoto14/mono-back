package jp.co.monocrea.user.resource;

import jakarta.inject.Inject;
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
import jp.co.monocrea.user.dto.UserDetailDto;
import jp.co.monocrea.user.dto.UserSummariesDto;
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
        @QueryParam("_limit") @Positive @DefaultValue("10") @Min(1) @Max(100) Integer limit
    ) {        
        UserSummariesDto userSummaries = userService.getPagenatedUserSummaries(id, nameLike, sortKey, order, page, limit);
        return Response
                .ok(userSummaries.getUsers())
                .header("X-Total-Count", userSummaries.getTotalCount())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") @Positive Long id) {
        UserDetailDto userDetail = userService.getUserDetailById(id);
        return Response.ok(userDetail).build();
    }

    @POST
    public Response createUser(UserDetailDto userDetail) {
        userService.createUser(userDetail);
        return Response.ok(userDetail).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") @Positive Long id, UserDetailDto userDetail) {
        userDetail.id = id;
        userService.updateUser(userDetail);
        return Response.ok(userDetail).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") @Positive Long id) {
        userService.deleteUser(id);
        return Response.ok().build();
    }
}
