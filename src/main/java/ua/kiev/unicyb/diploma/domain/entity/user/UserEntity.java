package ua.kiev.unicyb.diploma.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER_TBL")
@Getter
@Setter
public class UserEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_gen")
    @SequenceGenerator(name="user_id_gen", sequenceName="USER_ID_SEQ", allocationSize=1)
    private Long userId;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private Boolean isActive;

    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns
            = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy="users")
    @JsonIgnore
    private List<TestEntity> tests = new ArrayList<>();
}