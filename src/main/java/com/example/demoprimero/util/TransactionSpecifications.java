package com.example.demoprimero.util;

import com.example.demoprimero.model.Transaction;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.Date;

public class TransactionSpecifications {

    public static Specification<Transaction> hasAccountId(Long accountId) {
        return (root, query, criteriaBuilder) ->
                accountId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("account"), accountId);
    }

    public static Specification<Transaction> hasCustomerId(Long customerId) {
        return (root, query, criteriaBuilder) ->
                customerId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("customer"), customerId);
    }

    public static Specification<Transaction> isWithinDateRange(Date startDate, Date endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            } else if (startDate == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), endDate);
            } else if (endDate == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), startDate);
            } else {
                return criteriaBuilder.between(root.get("fecha"), startDate, endDate);
            }
        };
    }
}

