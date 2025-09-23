package jp.co.monocrea.user.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserDetailDto;
import jp.co.monocrea.user.dto.UserSummaryDto;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserTable> {

    public void createUser(UserDetailDto userDetail) {
        UserTable user = new UserTable();
        user.name = userDetail.name;
        user.email = userDetail.email;
        user.phone = userDetail.phone;
        user.address = userDetail.address;
        user.createdAt = userDetail.createdAt;
        user.updatedAt = userDetail.updatedAt;
        persist(user);
    }
    
    public PagedResult<UserSummaryDto> findUserSummaryById(Long id) {
        List<UserSummaryDto> users = find("id", id)
            .project(UserSummaryProjection.class)
            .stream()
            .map(UserSummaryProjection::convertToDto)
            .toList();
        return new PagedResult<>(users, (long) users.size());
    }   

    public PagedResult<UserSummaryDto> findPagedUserSummariesWithSort(String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit) {
        Sort sort = createSort(sortKey, order);
        PanacheQuery<UserTable> query;

        if (nameLike != null && !nameLike.isBlank()) {
            query = find("name like ?1", sort, "%" + nameLike + "%");
        } else {
            query = findAll(sort);
        }

        long totalCount = query.count();

        List<UserSummaryDto> pageList = query
            .page(Page.of(page - 1, limit))
            .project(UserSummaryProjection.class)
            .stream()
            .map(UserSummaryProjection::convertToDto)
            .toList();

        return new PagedResult<>(pageList, totalCount);
    }

    public UserDetailDto findUserDetailById(Long id) {
        UserTable user = findById(id);
        return new UserDetailDto(id, user.name, user.email, user.phone, user.address, user.createdAt, user.updatedAt);
    }

    public void updateUser(UserDetailDto userDetail) {
        UserTable user = findById(userDetail.id);
        user.name = userDetail.name;
        user.email = userDetail.email;
        user.phone = userDetail.phone;
        user.address = userDetail.address;
        user.updatedAt = userDetail.updatedAt;
        persist(user);
    }

    public void deleteUser(Long id) {
        deleteById(id);
    }

    private Sort createSort(String sortKey, OrderEnum order) {
        if (sortKey != null && !sortKey.isEmpty()) {
            Sort.Direction direction = (order == OrderEnum.DESC) ? Sort.Direction.Descending : Sort.Direction.Ascending;
            return Sort.by(sortKey, direction);
        }
        return Sort.by("id");
    }
}
