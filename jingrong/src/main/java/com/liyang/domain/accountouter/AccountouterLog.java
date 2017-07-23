package com.liyang.domain.accountouter;

import com.liyang.domain.base.AbstractAuditorLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountouter_log")
public class AccountouterLog extends AbstractAuditorLog<Accountouter,AccountouterAct> {

}
