package com.liyang.domain.accountinner;

import com.liyang.domain.base.AbstractAuditorLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountinners_log")
public class AccouninnerLog extends AbstractAuditorLog<Accouninner,AccouninnerAct> {

}
