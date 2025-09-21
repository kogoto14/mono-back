package jp.co.monocrea.user.service;

import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserSummariesDto;

public interface UserService {
    public UserSummariesDto getPagenatedUserSummaries(Integer id, String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit);
}
