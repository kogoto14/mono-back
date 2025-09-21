package jp.co.monocrea.user.service;

import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserDetailDto;
import jp.co.monocrea.user.dto.UserSummariesDto;

public interface UserService {
    public UserSummariesDto getPagenatedUserSummaries(Long id, String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit);
    public UserDetailDto getUserDetailById(Long id);
    public void createUser(UserDetailDto userDetail);
    public void updateUser(UserDetailDto userDetail);
    public void deleteUser(Long id);
}
