package ua.kiev.unicyb.diploma.domain.entity.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ua.kiev.unicyb.diploma.domain.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "CONFIGURATION")
public class ConfigurationEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="configuration_gen")
    @SequenceGenerator(name="configuration_gen", sequenceName="CONFIGURATION_SEQ", allocationSize=1)
    private Long configurationId;

    @Column
    @JsonIgnore
    private String pathToFolder;

    @Column
    @JsonIgnore
    private String folderName;

    @Column
    @JsonIgnore
    private String configFileName;

    @Column
    private String originalFileName;

    @Column
    @JsonIgnore
    private Boolean isLoaded = false;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column
    private Date createDate;

    @ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="USER_ID")
    @JsonIgnore
    private User user;
}
