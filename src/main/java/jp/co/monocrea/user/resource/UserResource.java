package jp.co.monocrea.user.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserSummariesDto;
import jp.co.monocrea.user.service.UserService;

@Path("/users")
public class UserResource {
    @Inject
    UserService userService;

    @GET
    public Response getPagenatedUsers(
        @QueryParam("id") Integer id,
        @QueryParam("_name_like") String nameLike,
        @QueryParam("_sort") String sortKey,
        @QueryParam("_order") @DefaultValue("ASC") OrderEnum order,
        @QueryParam("_page") Integer page,
        @QueryParam("_limit") Integer limit
    ) {        
        UserSummariesDto userSummaries = userService.getPagenatedUserSummaries(id, nameLike, sortKey, order, page, limit);
        return Response
                .ok(userSummaries.getUsers())
                .header("X-Total-Count", userSummaries.getTotalCount())
                .build();
    }
}
