package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.queryhelper;

import java.util.ArrayList;
import java.util.List;

import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.model.Player;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.util.SearchOperation;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public PlayerSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final PlayerSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final PlayerSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Player> build() {
        if (params.size() == 0)
            return null;

        Specification<Player> result = new PlayerSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new PlayerSpecification(params.get(i)))
                    : Specification.where(result).and(new PlayerSpecification(params.get(i)));
        }

        return result;
    }

    public final PlayerSpecificationsBuilder with(PlayerSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final PlayerSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
