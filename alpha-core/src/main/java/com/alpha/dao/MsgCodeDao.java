package com.alpha.dao;

import com.alpha.domain.MsgCodeDO;
import java.util.List;

import java.util.Date;

/**
 * Created by taoxiang on 2017/6/11.
 */
public interface MsgCodeDao {

    boolean insert(MsgCodeDO msgCodeDO);

    MsgCodeDO queryRecentOneByPhone(String phoneNumber);

    List<MsgCodeDO> queryByPhone(String phoneNumber, Date startDate);
}
