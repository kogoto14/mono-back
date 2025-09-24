package jp.co.monocrea.user.repository;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.repository.dto.PagedResultDto;
import jp.co.monocrea.user.repository.dto.UserDetailDto;
import jp.co.monocrea.user.repository.dto.UserSummaryDto;
import jp.co.monocrea.user.repository.table.UserTable;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserTable> {
    
    public PagedResultDto findUserSummaryById(Long id) {
        List<UserSummaryDto> users = find("id", id)
            .project(UserSummaryDto.class)
            .stream()
            .toList();
        return new PagedResultDto(users, (long) users.size());
    }

    public PagedResultDto findPagedUserSummariesWithSort(String nameLike, String sortKey, OrderEnum order, Integer page, Integer limit) {
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
            .project(UserSummaryDto.class)
            .stream()
            .toList();

        return new PagedResultDto(pageList, totalCount);
    }

    public UserDetailDto findUserDetailById(Long id) {
        UserTable user = findById(id);
        if (user == null) return null;
        return UserDetailDto.from(user);
    }

    public Long createUser(UserTable userTable) {
        persist(userTable);
        return userTable.getId();
    }

    public boolean updateUser(UserTable changes) {
        var user = findById(changes.getId());
        if (user == null) return false;
        user.setName(changes.getName());
        user.setEmail(changes.getEmail());
        user.setPhone(changes.getPhone());
        user.setAddress(changes.getAddress());
        user.setUpdatedAt(changes.getUpdatedAt());
        return true;
    }

    public boolean deleteUser(Long id) {
        return deleteById(id);
    }

    private Sort createSort(String sortKey, OrderEnum order) {
        if (sortKey != null && !sortKey.isEmpty()) {
            Sort.Direction direction = (order == OrderEnum.DESC) ? Sort.Direction.Descending : Sort.Direction.Ascending;
            return Sort.by(sortKey, direction);
        }
        return Sort.by("id");
    }
}
