package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserWorkflow;
import com.liyang.domain.wdds.Wdds;
import com.liyang.domain.wdds.WddsAct;
import com.liyang.domain.wdds.WddsLog;
import com.liyang.domain.wdds.WddsState;
import com.liyang.domain.wdds.WddsWorkflow;

@Component
public class WddsRestEventListener extends WorkflowRestEventListener<Wdds, WddsWorkflow, WddsAct, WddsState, WddsLog> {

}
