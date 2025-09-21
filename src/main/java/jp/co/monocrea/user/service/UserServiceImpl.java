package jp.co.monocrea.user.service;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserDetailDto;
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
    public UserSummariesDto getPagenatedUserSummaries(Long id, String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit) {
        int pageNum = Optional.ofNullable(page).orElse(1);
        int limitNum = Optional.ofNullable(limit).orElse(10);
        
        PagedResult<UserSummaryDto> result = Optional.ofNullable(id)
            .map(userRepository::findUserSummaryById)
            .orElseGet(() -> userRepository.findPagedUserSummariesWithSort(nameLike, sortKey, order, pageNum, limitNum));
        
        return new UserSummariesDto(result.list, result.totalCount);
    }

    @Override
    @Transactional
    public UserDetailDto getUserDetailById(Long id) {
        return userRepository.findUserDetailById(id);
    }

    @Override
    @Transactional
    public void createUser(UserDetailDto userDetail) {
        userRepository.createUser(userDetail);
    }

    @Override
    @Transactional
    public void updateUser(UserDetailDto userDetail) {
        userRepository.updateUser(userDetail);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
