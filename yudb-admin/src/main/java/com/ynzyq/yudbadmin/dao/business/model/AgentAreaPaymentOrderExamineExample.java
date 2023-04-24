package com.ynzyq.yudbadmin.dao.business.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AgentAreaPaymentOrderExamineExample {
    @Id
    @KeySql(useGeneratedKeys = true)
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AgentAreaPaymentOrderExamineExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdIsNull() {
            addCriterion("PAYMENT_ORDER_MASTER_ID is null");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdIsNotNull() {
            addCriterion("PAYMENT_ORDER_MASTER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdEqualTo(Integer value) {
            addCriterion("PAYMENT_ORDER_MASTER_ID =", value, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdNotEqualTo(Integer value) {
            addCriterion("PAYMENT_ORDER_MASTER_ID <>", value, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdGreaterThan(Integer value) {
            addCriterion("PAYMENT_ORDER_MASTER_ID >", value, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PAYMENT_ORDER_MASTER_ID >=", value, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdLessThan(Integer value) {
            addCriterion("PAYMENT_ORDER_MASTER_ID <", value, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdLessThanOrEqualTo(Integer value) {
            addCriterion("PAYMENT_ORDER_MASTER_ID <=", value, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdIn(List<Integer> values) {
            addCriterion("PAYMENT_ORDER_MASTER_ID in", values, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdNotIn(List<Integer> values) {
            addCriterion("PAYMENT_ORDER_MASTER_ID not in", values, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdBetween(Integer value1, Integer value2) {
            addCriterion("PAYMENT_ORDER_MASTER_ID between", value1, value2, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentOrderMasterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PAYMENT_ORDER_MASTER_ID not between", value1, value2, "paymentOrderMasterId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdIsNull() {
            addCriterion("PAYMENT_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdIsNotNull() {
            addCriterion("PAYMENT_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdEqualTo(Integer value) {
            addCriterion("PAYMENT_TYPE_ID =", value, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdNotEqualTo(Integer value) {
            addCriterion("PAYMENT_TYPE_ID <>", value, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdGreaterThan(Integer value) {
            addCriterion("PAYMENT_TYPE_ID >", value, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PAYMENT_TYPE_ID >=", value, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdLessThan(Integer value) {
            addCriterion("PAYMENT_TYPE_ID <", value, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("PAYMENT_TYPE_ID <=", value, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdIn(List<Integer> values) {
            addCriterion("PAYMENT_TYPE_ID in", values, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdNotIn(List<Integer> values) {
            addCriterion("PAYMENT_TYPE_ID not in", values, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("PAYMENT_TYPE_ID between", value1, value2, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PAYMENT_TYPE_ID not between", value1, value2, "paymentTypeId");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameIsNull() {
            addCriterion("PAYMENT_TYPE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameIsNotNull() {
            addCriterion("PAYMENT_TYPE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameEqualTo(String value) {
            addCriterion("PAYMENT_TYPE_NAME =", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameNotEqualTo(String value) {
            addCriterion("PAYMENT_TYPE_NAME <>", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameGreaterThan(String value) {
            addCriterion("PAYMENT_TYPE_NAME >", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_TYPE_NAME >=", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameLessThan(String value) {
            addCriterion("PAYMENT_TYPE_NAME <", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_TYPE_NAME <=", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameLike(String value) {
            addCriterion("PAYMENT_TYPE_NAME like", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameNotLike(String value) {
            addCriterion("PAYMENT_TYPE_NAME not like", value, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameIn(List<String> values) {
            addCriterion("PAYMENT_TYPE_NAME in", values, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameNotIn(List<String> values) {
            addCriterion("PAYMENT_TYPE_NAME not in", values, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameBetween(String value1, String value2) {
            addCriterion("PAYMENT_TYPE_NAME between", value1, value2, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNameNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_TYPE_NAME not between", value1, value2, "paymentTypeName");
            return (Criteria) this;
        }

        public Criteria andExamineTypeIsNull() {
            addCriterion("EXAMINE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andExamineTypeIsNotNull() {
            addCriterion("EXAMINE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andExamineTypeEqualTo(Integer value) {
            addCriterion("EXAMINE_TYPE =", value, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeNotEqualTo(Integer value) {
            addCriterion("EXAMINE_TYPE <>", value, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeGreaterThan(Integer value) {
            addCriterion("EXAMINE_TYPE >", value, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAMINE_TYPE >=", value, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeLessThan(Integer value) {
            addCriterion("EXAMINE_TYPE <", value, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeLessThanOrEqualTo(Integer value) {
            addCriterion("EXAMINE_TYPE <=", value, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeIn(List<Integer> values) {
            addCriterion("EXAMINE_TYPE in", values, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeNotIn(List<Integer> values) {
            addCriterion("EXAMINE_TYPE not in", values, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeBetween(Integer value1, Integer value2) {
            addCriterion("EXAMINE_TYPE between", value1, value2, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAMINE_TYPE not between", value1, value2, "examineType");
            return (Criteria) this;
        }

        public Criteria andExamineIsNull() {
            addCriterion("EXAMINE is null");
            return (Criteria) this;
        }

        public Criteria andExamineIsNotNull() {
            addCriterion("EXAMINE is not null");
            return (Criteria) this;
        }

        public Criteria andExamineEqualTo(Integer value) {
            addCriterion("EXAMINE =", value, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineNotEqualTo(Integer value) {
            addCriterion("EXAMINE <>", value, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineGreaterThan(Integer value) {
            addCriterion("EXAMINE >", value, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAMINE >=", value, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineLessThan(Integer value) {
            addCriterion("EXAMINE <", value, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineLessThanOrEqualTo(Integer value) {
            addCriterion("EXAMINE <=", value, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineIn(List<Integer> values) {
            addCriterion("EXAMINE in", values, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineNotIn(List<Integer> values) {
            addCriterion("EXAMINE not in", values, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineBetween(Integer value1, Integer value2) {
            addCriterion("EXAMINE between", value1, value2, "examine");
            return (Criteria) this;
        }

        public Criteria andExamineNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAMINE not between", value1, value2, "examine");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andOldMoneyIsNull() {
            addCriterion("OLD_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andOldMoneyIsNotNull() {
            addCriterion("OLD_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andOldMoneyEqualTo(BigDecimal value) {
            addCriterion("OLD_MONEY =", value, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyNotEqualTo(BigDecimal value) {
            addCriterion("OLD_MONEY <>", value, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyGreaterThan(BigDecimal value) {
            addCriterion("OLD_MONEY >", value, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_MONEY >=", value, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyLessThan(BigDecimal value) {
            addCriterion("OLD_MONEY <", value, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_MONEY <=", value, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyIn(List<BigDecimal> values) {
            addCriterion("OLD_MONEY in", values, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyNotIn(List<BigDecimal> values) {
            addCriterion("OLD_MONEY not in", values, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_MONEY between", value1, value2, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andOldMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_MONEY not between", value1, value2, "oldMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyIsNull() {
            addCriterion("NEW_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andNewMoneyIsNotNull() {
            addCriterion("NEW_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andNewMoneyEqualTo(BigDecimal value) {
            addCriterion("NEW_MONEY =", value, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyNotEqualTo(BigDecimal value) {
            addCriterion("NEW_MONEY <>", value, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyGreaterThan(BigDecimal value) {
            addCriterion("NEW_MONEY >", value, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NEW_MONEY >=", value, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyLessThan(BigDecimal value) {
            addCriterion("NEW_MONEY <", value, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NEW_MONEY <=", value, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyIn(List<BigDecimal> values) {
            addCriterion("NEW_MONEY in", values, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyNotIn(List<BigDecimal> values) {
            addCriterion("NEW_MONEY not in", values, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NEW_MONEY between", value1, value2, "newMoney");
            return (Criteria) this;
        }

        public Criteria andNewMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NEW_MONEY not between", value1, value2, "newMoney");
            return (Criteria) this;
        }

        public Criteria andOldTimeIsNull() {
            addCriterion("OLD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOldTimeIsNotNull() {
            addCriterion("OLD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOldTimeEqualTo(Date value) {
            addCriterionForJDBCDate("OLD_TIME =", value, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("OLD_TIME <>", value, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("OLD_TIME >", value, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("OLD_TIME >=", value, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeLessThan(Date value) {
            addCriterionForJDBCDate("OLD_TIME <", value, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("OLD_TIME <=", value, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeIn(List<Date> values) {
            addCriterionForJDBCDate("OLD_TIME in", values, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("OLD_TIME not in", values, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("OLD_TIME between", value1, value2, "oldTime");
            return (Criteria) this;
        }

        public Criteria andOldTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("OLD_TIME not between", value1, value2, "oldTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeIsNull() {
            addCriterion("NEW_TIME is null");
            return (Criteria) this;
        }

        public Criteria andNewTimeIsNotNull() {
            addCriterion("NEW_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andNewTimeEqualTo(Date value) {
            addCriterionForJDBCDate("NEW_TIME =", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("NEW_TIME <>", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("NEW_TIME >", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("NEW_TIME >=", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeLessThan(Date value) {
            addCriterionForJDBCDate("NEW_TIME <", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("NEW_TIME <=", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeIn(List<Date> values) {
            addCriterionForJDBCDate("NEW_TIME in", values, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("NEW_TIME not in", values, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("NEW_TIME between", value1, value2, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("NEW_TIME not between", value1, value2, "newTime");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("MSG is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("MSG is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("MSG =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("MSG <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("MSG >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("MSG >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("MSG <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("MSG <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("MSG like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("MSG not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("MSG in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("MSG not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("MSG between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("MSG not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRefuseIsNull() {
            addCriterion("REFUSE is null");
            return (Criteria) this;
        }

        public Criteria andRefuseIsNotNull() {
            addCriterion("REFUSE is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseEqualTo(String value) {
            addCriterion("REFUSE =", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotEqualTo(String value) {
            addCriterion("REFUSE <>", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseGreaterThan(String value) {
            addCriterion("REFUSE >", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseGreaterThanOrEqualTo(String value) {
            addCriterion("REFUSE >=", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseLessThan(String value) {
            addCriterion("REFUSE <", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseLessThanOrEqualTo(String value) {
            addCriterion("REFUSE <=", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseLike(String value) {
            addCriterion("REFUSE like", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotLike(String value) {
            addCriterion("REFUSE not like", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseIn(List<String> values) {
            addCriterion("REFUSE in", values, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotIn(List<String> values) {
            addCriterion("REFUSE not in", values, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseBetween(String value1, String value2) {
            addCriterion("REFUSE between", value1, value2, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotBetween(String value1, String value2) {
            addCriterion("REFUSE not between", value1, value2, "refuse");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNull() {
            addCriterion("APPLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("APPLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Integer value) {
            addCriterion("APPLY_ID =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Integer value) {
            addCriterion("APPLY_ID <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Integer value) {
            addCriterion("APPLY_ID >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("APPLY_ID >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Integer value) {
            addCriterion("APPLY_ID <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("APPLY_ID <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Integer> values) {
            addCriterion("APPLY_ID in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Integer> values) {
            addCriterion("APPLY_ID not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("APPLY_ID between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("APPLY_ID not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyNameIsNull() {
            addCriterion("APPLY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andApplyNameIsNotNull() {
            addCriterion("APPLY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andApplyNameEqualTo(String value) {
            addCriterion("APPLY_NAME =", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameNotEqualTo(String value) {
            addCriterion("APPLY_NAME <>", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameGreaterThan(String value) {
            addCriterion("APPLY_NAME >", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_NAME >=", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameLessThan(String value) {
            addCriterion("APPLY_NAME <", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameLessThanOrEqualTo(String value) {
            addCriterion("APPLY_NAME <=", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameLike(String value) {
            addCriterion("APPLY_NAME like", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameNotLike(String value) {
            addCriterion("APPLY_NAME not like", value, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameIn(List<String> values) {
            addCriterion("APPLY_NAME in", values, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameNotIn(List<String> values) {
            addCriterion("APPLY_NAME not in", values, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameBetween(String value1, String value2) {
            addCriterion("APPLY_NAME between", value1, value2, "applyName");
            return (Criteria) this;
        }

        public Criteria andApplyNameNotBetween(String value1, String value2) {
            addCriterion("APPLY_NAME not between", value1, value2, "applyName");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNull() {
            addCriterion("COMPLETE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNotNull() {
            addCriterion("COMPLETE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeEqualTo(Date value) {
            addCriterion("COMPLETE_TIME =", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotEqualTo(Date value) {
            addCriterion("COMPLETE_TIME <>", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThan(Date value) {
            addCriterion("COMPLETE_TIME >", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("COMPLETE_TIME >=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThan(Date value) {
            addCriterion("COMPLETE_TIME <", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("COMPLETE_TIME <=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIn(List<Date> values) {
            addCriterion("COMPLETE_TIME in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotIn(List<Date> values) {
            addCriterion("COMPLETE_TIME not in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeBetween(Date value1, Date value2) {
            addCriterion("COMPLETE_TIME between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("COMPLETE_TIME not between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("DELETED is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("DELETED is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Byte value) {
            addCriterion("DELETED =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Byte value) {
            addCriterion("DELETED <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Byte value) {
            addCriterion("DELETED >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Byte value) {
            addCriterion("DELETED >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Byte value) {
            addCriterion("DELETED <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Byte value) {
            addCriterion("DELETED <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Byte> values) {
            addCriterion("DELETED in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Byte> values) {
            addCriterion("DELETED not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Byte value1, Byte value2) {
            addCriterion("DELETED between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Byte value1, Byte value2) {
            addCriterion("DELETED not between", value1, value2, "deleted");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}