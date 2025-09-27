package jp.co.monocrea.user.service.impl;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.co.monocrea.user.common.QueryConstants;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.repository.dto.PagedResultDto;
import jp.co.monocrea.user.repository.dto.UserDetailDto;
import jp.co.monocrea.user.repository.UserRepository;
import jp.co.monocrea.user.resource.response.PagedUserSummariesResponse;
import jp.co.monocrea.user.resource.response.UserDetailResponse;
import jp.co.monocrea.user.service.UserService;
import jp.co.monocrea.user.service.command.UserCreateCommand;
import jp.co.monocrea.user.service.command.UserUpdateCommand;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public PagedUserSummariesResponse getPaginatedUserSummaries(Long id, String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit) {
        int pageNum = Optional.ofNullable(page).orElse(Integer.valueOf(QueryConstants.DEFAULT_PAGE));
        int limitNum = Optional.ofNullable(limit).orElse(Integer.valueOf(QueryConstants.DEFAULT_LIMIT));
        
        PagedResultDto result = Optional.ofNullable(id)
            .map(userRepository::findUserSummaryById)
            .orElseGet(() -> userRepository.findPagedUserSummariesWithSort(nameLike, sortKey, order, pageNum, limitNum));
        
        return PagedUserSummariesResponse.from(result);
    }

    @Override
    @Transactional
    public UserDetailResponse getUserDetailById(Long id) {
        UserDetailDto userDetail = userRepository.findUserDetailById(id);
        return UserDetailResponse.from(userDetail);
    }

    @Override
    @Transactional
    public void createUser(UserCreateCommand userCreateCommand) {
        Long id = userRepository.createUser(userCreateCommand.convertToUserTable());
        if (id == null) throw new jakarta.ws.rs.InternalServerErrorException();
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateCommand userUpdateCommand) {
        boolean ok = userRepository.updateUser(userUpdateCommand.convertToUserTable());
        if (!ok) throw new jakarta.ws.rs.InternalServerErrorException();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.deleteUser(id)) throw new jakarta.ws.rs.InternalServerErrorException();
    }
}
