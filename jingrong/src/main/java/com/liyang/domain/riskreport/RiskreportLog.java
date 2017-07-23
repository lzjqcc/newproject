package com.liyang.domain.riskreport;

import com.liyang.domain.base.AbstractAuditorLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "riskreport_log")
public class RiskreportLog extends AbstractAuditorLog<Riskreport,RiskreportAct> {

}
