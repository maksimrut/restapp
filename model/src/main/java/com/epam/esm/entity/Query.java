package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private String tag;
    private String partName;
    private String sortByName;
    private String sortByDate;

    private static final String SELECT_FROM = "SELECT gc.*, t.id AS tagId, t.name AS tagName " +
            "FROM gift_certificates gc " +
            "JOIN tags_certificates tc ON gc.id=tc.gift_certificate_id " +
            "JOIN tags t ON t.id=tc.tag_id ";
    private static final String SELECT_BY_TAG = "WHERE t.name=?";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String SELECT_BY_PART_NAME =
            "gc.name LIKE CONCAT('%', ?, '%') " +
            "OR gc.description LIKE CONCAT('%', ?, '%')";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String ORDER_BY_NAME = "gc.name ";
    private static final String DESC = "DESC";
    private static final String COMMA_SIGN = ",";
    private static final String ORDER_BY_DATE = "gc.createDate ";

    private final List<String> params = new ArrayList<>();
    private final StringBuilder sqlQuery = new StringBuilder(SELECT_FROM);

    public Query() {
    }

    public Query(String tag, String partName, String sortByName, String sortByDate) {
        this.tag = tag;
        this.partName = partName;
        this.sortByName = sortByName;
        this.sortByDate = sortByDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortByDate() {
        return sortByDate;
    }

    public void setSortByDate(String sortByDate) {
        this.sortByDate = sortByDate;
    }

    public Object[] getQueryParams() {
        return params.toArray();
    }

    public String buildSqlQuery() {
        buildSelectByTag();
        buildSelectByPartName();
        buildOrderByName();
        buildOrderByDate();
        return sqlQuery.toString();
    }

    private void buildSelectByTag() {
        if (tag != null) {
            sqlQuery.append(SELECT_BY_TAG);
            params.add(tag);
        }
    }

    private void buildSelectByPartName() {
        String queryPart = (tag != null) ? AND : WHERE;
        if (partName != null) {
            sqlQuery.append(queryPart).append(SELECT_BY_PART_NAME);
            params.add(partName);
            params.add(partName);
        }
    }

    private void buildOrderByName() {
        if (sortByName != null) {
            sqlQuery.append(ORDER_BY);
            sqlQuery.append(ORDER_BY_NAME);
            if (sortByName.equals(DESC)) {
                sqlQuery.append(DESC);
            }
        }
    }

    private void buildOrderByDate() {
        if (sortByDate != null) {
            if (sortByName != null) {
                sqlQuery.append(COMMA_SIGN);
            } else {
                sqlQuery.append(ORDER_BY);
            }
            sqlQuery.append(ORDER_BY_DATE);
            if (sortByDate.equals(DESC)) {
                sqlQuery.append(DESC);
            }
        }
    }
}
