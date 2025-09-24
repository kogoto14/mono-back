package jp.co.monocrea.user.service;

import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.resource.response.PagedUserSummariesResponse;
import jp.co.monocrea.user.resource.response.UserDetailResponse;
import jp.co.monocrea.user.service.command.UserCreateCommand;
import jp.co.monocrea.user.service.command.UserUpdateCommand;

public interface UserService {
    public PagedUserSummariesResponse getPagenatedUserSummaries(Long id, String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit);
    public UserDetailResponse getUserDetailById(Long id);
    public void createUser(UserCreateCommand userCreateCommand);
    public void updateUser(UserUpdateCommand userUpdateCommand);
    public void deleteUser(Long id);
}
