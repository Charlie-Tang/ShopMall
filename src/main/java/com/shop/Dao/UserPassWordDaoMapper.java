package com.shop.Dao;

import com.shop.DaoObject.UserPassWordDao;

public interface UserPassWordDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri Feb 15 11:54:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri Feb 15 11:54:15 CST 2019
     */
    int insert(UserPassWordDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri Feb 15 11:54:15 CST 2019
     */
    int insertSelective(UserPassWordDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri Feb 15 11:54:15 CST 2019
     */
    UserPassWordDao selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri Feb 15 11:54:15 CST 2019
     */
    int updateByPrimaryKeySelective(UserPassWordDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri Feb 15 11:54:15 CST 2019
     */
    int updateByPrimaryKey(UserPassWordDao record);

	UserPassWordDao selectByUserId(Integer userid);
}