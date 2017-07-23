package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.group.Group;
import com.liyang.domain.group.GroupAct;
import com.liyang.domain.group.GroupLog;
import com.liyang.domain.group.GroupState;
@Component
public class GroupRestEventListener extends AuditorRestEventListener<Group,GroupState,GroupAct,GroupLog> {

}
