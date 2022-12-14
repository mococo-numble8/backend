package com.mococo.core.account.domain.entity;

import com.mococo.core.account.contstants.Role;
import com.mococo.core.account.dto.AccountSaveCommand;
import com.mococo.core.account.vo.EmailAddress;
import java.util.Objects;
import javax.persistence.*;

import com.mococo.core.common.Base.BaseTimeEntity;
import com.mococo.core.post.dto.PostGetsCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private EmailAddress emailAddress;

    @Column
    private String address;

    @Column
    private String password;

    @Column
    private String nickName;

    @Column
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    public static Account create(AccountSaveCommand command) {
        final Account user = new Account();
        user.emailAddress = command.getEmailAddress();
        user.password = command.getPassword();
        user.nickName = command.getNickName();
        user.role = command.getRole();
        return user;
    }

    public Account user(PostGetsCommand command) {
        final Account user = new Account();
        user.emailAddress = command.getUser().getEmailAddress();
        user.nickName = command.getUser().getNickName();
        return user;
    }

    public String getEmailAddressValue() {
        return Objects.isNull(this.emailAddress) ? null : this.emailAddress.getEmail();
    }

    public void setCredentials(String credentials) {
        this.password = credentials;
    }

}
