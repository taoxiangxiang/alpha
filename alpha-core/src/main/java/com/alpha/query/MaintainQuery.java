package com.alpha.query;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class MaintainQuery extends PageQuery {

    private static final long serialVersionUID = -7524851970819492882L;

    private Date startDate;

    private Date endDate;

    private Integer id;

    private List<String> statusList;
}
