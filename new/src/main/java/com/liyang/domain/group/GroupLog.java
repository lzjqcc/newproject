package com.liyang.domain.group;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;
@Entity
@Table(name = "group_log")
public class GroupLog extends AbstractAuditorLog<Group,GroupAct> {

}
