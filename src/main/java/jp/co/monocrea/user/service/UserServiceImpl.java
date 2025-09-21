package jp.co.monocrea.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserSummariesDto;
import jp.co.monocrea.user.dto.UserSummaryDto;
import jp.co.monocrea.user.repository.PagedResult;
import jp.co.monocrea.user.repository.UserRepository;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public UserSummariesDto getPagenatedUserSummaries(Integer id, String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit) {
        int pageNum = (page != null && page > 0) ? page : 1;
        int limitNum = (limit != null && limit > 0) ? limit : 10;
        
        PagedResult<UserSummaryDto> result;

        if (id != null) {
            result = userRepository.findUserSummaryById(id);
        } else {
            result = userRepository.findPagedUserSummariesWithSort(nameLike, sortKey, order, pageNum, limitNum);
        }
        
        return new UserSummariesDto(result.list, result.totalCount);
    }
}

