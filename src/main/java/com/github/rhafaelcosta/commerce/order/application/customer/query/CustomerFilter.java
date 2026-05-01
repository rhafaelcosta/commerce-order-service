package com.github.rhafaelcosta.commerce.order.application.customer.query;

import com.github.rhafaelcosta.commerce.order.application.utility.SortablePageFilter;
import lombok.*;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerFilter extends SortablePageFilter<CustomerFilter.SortType> {

    private String email;
    private String firstName;

    public CustomerFilter(int size, int page) {
        super(size, page);
    }

    @Override
    public SortType getSortByPropertyOrDefault() {
        return getSortByProperty() == null ? SortType.REGISTERED_AT : getSortByProperty();
    }

    @Override
    public Sort.Direction getSortDirectionOrDefault() {
        return getSortDirection() == null ? Sort.Direction.ASC : getSortDirection();
    }

    @Getter
    @RequiredArgsConstructor
    public enum SortType {
        FIRST_NAME      ("firstName"),
        REGISTERED_AT   ("registeredAt"),
        ;

        private final String propertyName;
    }

}