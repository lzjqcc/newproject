package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserWorkflow;

@Component
public class UserRestEventListener extends WorkflowRestEventListener<User, UserWorkflow, UserAct, UserState, UserLog> {

}
