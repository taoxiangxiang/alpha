package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/5/26.
 */
@Data
public class TeamQuery extends PageQuery {

    private static final long serialVersionUID = 3916331369012695613L;

    public String team;

    public String status;
}
