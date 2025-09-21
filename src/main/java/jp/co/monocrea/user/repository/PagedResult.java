package jp.co.monocrea.user.repository;

import java.util.List;

public class PagedResult<T> {
    public final List<T> list;
    public final Long totalCount;

    public PagedResult(List<T> list, Long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }
}
