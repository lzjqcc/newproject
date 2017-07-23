package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractAuditorLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise_log")
public class EnterpriseLog extends AbstractAuditorLog<Enterprise,EnterpriseAct> {

}
