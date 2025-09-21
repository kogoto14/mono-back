package jp.co.monocrea.user.resource;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
        @QueryParam("id") @Positive Integer id,
        @QueryParam("_name_like") String nameLike,
        @QueryParam("_sort") @Pattern(regexp = "id|name|createdAt|updatedAt") String sortKey,
        @QueryParam("_order") @DefaultValue("ASC") OrderEnum order,
        @QueryParam("_page")  @DefaultValue("1") Integer page,
        @QueryParam("_limit") @DefaultValue("10") @Min(1) @Max(100) Integer limit
    ) {        
        UserSummariesDto userSummaries = userService.getPagenatedUserSummaries(id, nameLike, sortKey, order, page, limit);
        return Response
                .ok(userSummaries.getUsers())
                .header("X-Total-Count", userSummaries.getTotalCount())
                .build();
    }
}
