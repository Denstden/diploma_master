package ua.kiev.unicyb.diploma.domain.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
public class Role {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_gen")
    @SequenceGenerator(name = "user_role_gen", sequenceName = "USER_ROLE_SEQ", allocationSize = 1)
    private Long roleId;

    @Column
    private String name;
}
