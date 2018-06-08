package ua.kiev.unicyb.diploma.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_TBL")
@Getter
@Setter
public class User {
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
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns
            = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();
}