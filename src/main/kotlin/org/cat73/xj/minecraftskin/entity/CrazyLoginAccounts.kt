package org.cat73.xj.minecraftskin.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * 用户表
 */
@Entity
@Table(name="CrazyLogin_accounts")
data class CrazyLoginAccounts(
    /**
     * 用户名
     */
    @Id
    val name: String = "",
    /**
     * 密码(明文)
     */
    @Column
    val password: String = ""
)
